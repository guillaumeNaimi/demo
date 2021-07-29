package com.example.demo.domain.port.secondary

import arrow.core.Either
import com.example.demo.domain.errors.ShowError
import com.example.demo.domain.model.Show
import java.lang.Exception

interface ShowPersistencePort {
    fun findShows(titleFilter: String?): Either<ShowError.ShowPersistenceError, List<Show>>
}