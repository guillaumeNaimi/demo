package com.example.demo.domain.errors.validation

sealed class ValidationError(override val message : String): RuntimeException(message) {
    data class NotInRange(val field: String, override val message: String = "Not in range"): ValidationError(message)
    data class UnexpectedError(val field: String, override val message: String = "unexpected error"): ValidationError(message)
    data class NotExist(val field: String, override val message: String = "not exists"): ValidationError(message)
}
