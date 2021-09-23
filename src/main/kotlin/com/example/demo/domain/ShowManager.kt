package com.example.demo.domain

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.flatMap
import arrow.core.getOrHandle
import com.example.demo.domain.errors.ShowError
import com.example.demo.domain.model.Show
import com.example.demo.domain.port.primary.ShowManagerPort
import com.example.demo.domain.port.secondary.RatingPersistencePort
import com.example.demo.domain.port.secondary.ShowPersistencePort

class ShowManager(private val showPersistencePort: ShowPersistencePort, private val ratingPersistencePort: RatingPersistencePort): ShowManagerPort {

    override fun getShows(showsFilter: ShowsFilter): Either<Exception, List<Show>> =
        either.eager {
            val shows = showPersistencePort.findShows(showsFilter).bind()
            shows.map { show ->
                val ratings = ratingPersistencePort.getRatingsByTitle(show.title).bind()
                show.copy(avgStars = if (ratings.isEmpty()) 0f else ratings.map { it.stars }.average().toFloat())
            }
        }
}