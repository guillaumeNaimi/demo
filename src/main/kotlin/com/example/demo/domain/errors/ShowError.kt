package com.example.demo.domain.errors

sealed class ShowError(override val message : String = "An error occured when retrieving show"): Exception(message) {
    data class ShowPersistenceError(override val message : String = "An error occured in persistence when retrieving show"): ShowError(message)
}
