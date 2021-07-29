package com.example.demo.infra.adapter.secondary.persistence

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.demo.domain.errors.ShowError
import com.example.demo.domain.model.Show
import com.example.demo.domain.port.secondary.ShowPersistencePort
import com.example.demo.infra.adapter.secondary.persistence.document.ShowDocument
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class ShowPersistenceAdapter: ShowPersistencePort {

    private val shows = listOf(
        ShowDocument("Stranger Things", 2016),
        ShowDocument("Ozark", 2017),
        ShowDocument("The Crown", 2016),
        ShowDocument("Dead to Me", 2019),
        ShowDocument("Orange is the New Black", 2013)
    )

    override fun findShows(titleFilter: String?): Either<ShowError.ShowPersistenceError, List<Show>> =
        when {
            titleFilter != null -> {
                shows.filter { it.title.contains(titleFilter) }.map { it.toShow() }.right()
            }
            titleFilter == "persistenceError" -> {
                ShowError.ShowPersistenceError("An error occurred in persistence").left()
            }
            else -> {
                shows.map { it.toShow() }.right()
            }
        }

}