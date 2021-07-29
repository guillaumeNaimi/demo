package com.example.demo.domain

import arrow.core.Either
import com.example.demo.domain.errors.ShowError
import com.example.demo.domain.model.Show
import com.example.demo.domain.port.primary.ShowManagerPort
import com.example.demo.domain.port.secondary.ShowPersistencePort
import java.lang.Exception

class ShowManager(private val showPersistencePort: ShowPersistencePort): ShowManagerPort {
    override fun getShows(titleFilter: String?): Either<ShowError, List<Show>> =
        showPersistencePort.findShows(titleFilter)
}