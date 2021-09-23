package com.example.demo.domain.port.primary

import arrow.core.Either
import com.example.demo.domain.errors.RatingError
import com.example.demo.domain.model.Rating

interface RatingManagerPort {
    fun addRating(stars: Int, title: String): Either<RatingError, Rating>
}