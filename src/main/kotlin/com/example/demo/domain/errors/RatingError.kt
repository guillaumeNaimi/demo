package com.example.demo.domain.errors

import com.example.demo.domain.errors.validation.ValidationError

sealed class RatingError(override val message : String = "An error occured when adding rating"): RuntimeException(message) {
    data class RatingPersistenceError(override val message : String = "An error occured in persistence when adding rating", val e: Throwable): RatingError(message)
    data class RatingValidationError(override val message : String = "The rating is invalid", val errors: List<ValidationError>): RatingError(message)
}
