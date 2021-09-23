package com.example.demo.domain.validation

import arrow.core.*
import arrow.typeclasses.Semigroup
import com.example.demo.domain.ShowsFilter
import com.example.demo.domain.errors.validation.ValidationError
import com.example.demo.domain.model.Rating
import com.example.demo.domain.port.secondary.ShowPersistencePort

class RatingValidation(private val showPersistencePort: ShowPersistencePort) {

    fun validateRating(stars: Int, title: String): ValidatedNel<ValidationError, Rating> =
        validateStars(stars).toValidatedNel()
            .zip(Semigroup.nonEmptyList(),validateTitle(title).toValidatedNel())
            .map { (a, b) -> Rating(a, b)}

    fun validateStars(stars: Int): Validated<ValidationError, Int> =
        if ((1..5).contains(stars)) {
            stars.valid()
        } else {
            ValidationError.NotInRange(field = "stars").invalid()
        }

    fun validateTitle(title: String): Validated<ValidationError, String> =
        showPersistencePort.findShows(ShowsFilter.FilterByTitle(title)).fold(
            ifLeft = {
                ValidationError.UnexpectedError(field = "title").invalid()
            },
            ifRight = {
                if (it.any { show -> show.title == title }) {
                    title.valid()
                } else {
                    ValidationError.NotExist(field = "title").invalid()
                }
            }
        )

}