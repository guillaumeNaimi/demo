package com.example.demo.domain

import arrow.core.left
import arrow.core.right
import com.example.demo.domain.ShowsFilter.NoFilter
import com.example.demo.domain.errors.ShowError
import com.example.demo.domain.model.Rating
import com.example.demo.domain.model.Show
import com.example.demo.domain.port.secondary.RatingPersistencePort
import com.example.demo.domain.port.secondary.ShowPersistencePort
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class ShowManagerTest {

    private val shows: List<Show> = listOf(
        Show(title = "toto", releaseYear = 2021, avgStars = 5f)
    )

    private val showPersistencePort = mockk<ShowPersistencePort>()
    private val ratingPersistencePort = mockk<RatingPersistencePort>()

    private val showManager = ShowManager(showPersistencePort, ratingPersistencePort)

    @Test
    fun `should return a list of shows when persistence return the list`() {
        // GIVEN
        every { showPersistencePort.findShows(any()) }
            .returns(shows.right())
        every { ratingPersistencePort.getRatingsByTitle(any()) }
            .returns(listOf(Rating(5, "toto")).right())

        // WHEN
        val showsResult = showManager.getShows(NoFilter)

        //THEN
        showsResult shouldBeRight shows
    }

    @Test
    fun `should return an error when persistence return an error`() {
        // GIVEN
        every { showPersistencePort.findShows(any()) }
            .returns(ShowError.ShowPersistenceError("la bdd est cassée").left())

        // WHEN
        val showsResult = showManager.getShows(NoFilter)

        //THEN
        showsResult shouldBeLeft {
            it.message shouldBe ShowError.ShowPersistenceError("la bdd est cassée").message
        }
    }
}