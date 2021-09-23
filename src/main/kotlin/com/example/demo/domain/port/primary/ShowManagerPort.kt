package com.example.demo.domain.port.primary

import arrow.core.Either
import com.example.demo.domain.ShowsFilter
import com.example.demo.domain.model.Show
import kotlin.Exception


interface ShowManagerPort {
    fun getShows(showsFilter: ShowsFilter): Either<Exception, List<Show>>
}
