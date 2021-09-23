package com.example.demo.infra.adapter.secondary.persistence

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.demo.domain.ShowsFilter
import com.example.demo.domain.errors.ShowError
import com.example.demo.domain.model.Show
import com.example.demo.domain.port.secondary.ShowPersistencePort
import com.example.demo.infra.adapter.secondary.persistence.document.ShowDocument
import org.springframework.stereotype.Service
import java.lang.Exception

private val defaultShows = listOf(
    ShowDocument("Stranger Things", 2016),
    ShowDocument("Ozark", 2017),
    ShowDocument("The Crown", 2016),
    ShowDocument("Dead to Me", 2019),
    ShowDocument("Orange is the New Black", 2013)
)

const val ERROR_CASE = "persistenceError"



@Service
class ShowPersistenceAdapter(private val shows: List<ShowDocument> = defaultShows): ShowPersistencePort {

    override fun findShows(showsFilter: ShowsFilter): Either<ShowError.ShowPersistenceError, List<Show>> =
        when (showsFilter) {
            is ShowsFilter.FilterByTitle -> {
                if (showsFilter.title == ERROR_CASE) {
                    ShowError.ShowPersistenceError("An error occurred in persistence").left()
                } else {
                    shows.filter { it.title.contains(showsFilter.title) }.map { it.toShow() }.right()
                }

            }
            is ShowsFilter.NoFilter -> {
                shows.map { it.toShow() }.right()
            }
        }

}