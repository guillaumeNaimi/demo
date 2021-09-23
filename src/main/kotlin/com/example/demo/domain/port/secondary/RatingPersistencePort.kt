package com.example.demo.domain.port.secondary

import arrow.core.Either
import com.example.demo.domain.errors.RatingError
import com.example.demo.domain.model.Rating

interface RatingPersistencePort {
    fun addRating(rating: Rating): Either<RatingError, Rating>
    fun getRatingsByTitle(title: String): Either<RatingError, List<Rating>>
}