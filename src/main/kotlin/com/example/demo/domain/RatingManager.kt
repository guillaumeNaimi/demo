package com.example.demo.domain

import arrow.core.Either
import arrow.core.computations.either
import com.example.demo.domain.errors.RatingError
import com.example.demo.domain.model.Rating
import com.example.demo.domain.port.primary.RatingManagerPort
import com.example.demo.domain.port.secondary.RatingPersistencePort
import com.example.demo.domain.port.secondary.ShowPersistencePort
import com.example.demo.domain.validation.RatingValidation

class RatingManager(
    private val ratingPersistencePort: RatingPersistencePort,
    private val showPersistencePort: ShowPersistencePort,
    private val ratingValidation: RatingValidation = RatingValidation(showPersistencePort),

): RatingManagerPort {
    override fun addRating(stars: Int, title: String) : Either<RatingError, Rating> =
        either.eager {
            val rating = ratingValidation.validateRating(stars, title)
                .toEither()
                .mapLeft { RatingError.RatingValidationError(errors = it) }
                .bind()
            ratingPersistencePort.addRating(rating).bind()
        }

}