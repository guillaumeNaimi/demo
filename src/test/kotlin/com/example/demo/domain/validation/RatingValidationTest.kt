package com.example.demo.domain.validation

import arrow.core.left
import arrow.core.right
import com.example.demo.domain.ShowsFilter.FilterByTitle
import com.example.demo.domain.errors.ShowError
import com.example.demo.domain.errors.validation.ValidationError.*
import com.example.demo.domain.model.Rating
import com.example.demo.domain.model.Show
import com.example.demo.domain.port.secondary.ShowPersistencePort
import io.kotest.assertions.arrow.core.shouldContainAll
import io.kotest.assertions.arrow.core.shouldHaveSize
import io.kotest.assertions.arrow.validation.shouldBeInvalid
import io.kotest.assertions.arrow.validation.shouldBeValid
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class RatingValidationTest {

    private val showPersistencePort = mockk<ShowPersistencePort>()

    private val ratingValidation = RatingValidation(showPersistencePort)

    @Nested
    inner class StarsValidator {
        @Test
        fun `stars in range should be valid`() {
            // GIVEN
            val stars = 4

            // WHEN
            val result = ratingValidation.validateStars(stars)

            // THEN
            result shouldBeValid stars
        }

        @Test
        fun `stars not in range should be invalid`() {
            // GIVEN
            val stars = 6

            // WHEN
            val result = ratingValidation.validateStars(stars)

            // THEN
            result shouldBeInvalid NotInRange("stars")
        }
    }

    @Nested
    inner class TitleValidation {
        @Test
        fun `title existing should be valid`() {
            // GIVEN
            val show = Show("pwet", 2017)
            every { showPersistencePort.findShows(FilterByTitle(show.title)) } returns listOf(show).right()

            // WHEN
            val result = ratingValidation.validateTitle(show.title)

            // THEN
            verify {
                showPersistencePort.findShows(FilterByTitle(show.title))
            }
            result shouldBeValid show.title
        }

        @Test
        fun `title not existing should not be valid`() {
            // GIVEN
            val show = Show("pwet", 2017)
            every { showPersistencePort.findShows(FilterByTitle("plop")) } returns listOf(show).right()

            // WHEN
            val result = ratingValidation.validateTitle("plop")

            // THEN
            verify {
                showPersistencePort.findShows(FilterByTitle("plop"))
            }
            result shouldBeInvalid NotExist("title")
        }

        @Test
        fun `database error should not be valid`() {
            // GIVEN
            every { showPersistencePort.findShows(FilterByTitle("plop")) } returns
                    ShowError.ShowPersistenceError("la bdd est cassée").left()

            // WHEN
            val result = ratingValidation.validateTitle("plop")

            // THEN
            verify {
                showPersistencePort.findShows(FilterByTitle("plop"))
            }
            result shouldBeInvalid UnexpectedError("title")
        }
    }

    @Nested
    inner class RatingValidator {
        @Test
        fun `valid rating should return the rating`() {
            // GIVEN
            val stars = 4
            val show = Show("pwet", 2017)
            every { showPersistencePort.findShows(FilterByTitle(show.title)) } returns listOf(show).right()


            // WHEN
            val result = ratingValidation.validateRating(stars, show.title)

            // THEN
            result shouldBeValid Rating(stars, show.title)
        }

        @Test
        fun `invalid rating should return accumulated errors`() {
            // GIVEN
            val stars = 6
            val show = Show("pwet", 2017)
            every { showPersistencePort.findShows(FilterByTitle("plop")) } returns listOf(show).right()

            // WHEN
            val result = ratingValidation.validateRating(stars, "plop")

            // THEN
            result shouldBeInvalid {
                it.value shouldHaveSize 2
                it.value shouldContainAll listOf(NotInRange("stars"), NotExist("title"))
            }
        }
    }
}