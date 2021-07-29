package com.example.demo.domain.port.primary

import arrow.core.Either
import com.example.demo.domain.model.Show
import java.lang.Exception


interface ShowManagerPort {
    fun getShows(titleFilter : String?): Either<Exception, List<Show>>
}
