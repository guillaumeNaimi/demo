package com.example.demo.domain.port.secondary

import arrow.core.Either
import com.example.demo.domain.ShowsFilter
import com.example.demo.domain.errors.ShowError
import com.example.demo.domain.model.Show
import java.lang.Exception

interface ShowPersistencePort {
    fun findShows(showsFilter: ShowsFilter): Either<ShowError.ShowPersistenceError, List<Show>>
}