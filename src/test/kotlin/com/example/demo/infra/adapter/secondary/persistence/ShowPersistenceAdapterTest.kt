package com.example.demo.infra.adapter.secondary.persistence

import com.example.demo.domain.ShowsFilter
import com.example.demo.domain.ShowsFilter.FilterByTitle
import com.example.demo.domain.ShowsFilter.NoFilter
import com.example.demo.domain.errors.ShowError
import com.example.demo.domain.model.Show
import com.example.demo.infra.adapter.secondary.persistence.document.ShowDocument
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import org.junit.jupiter.api.Test

class ShowPersistenceAdapterTest {

    private val showsDocument = listOf(
        ShowDocument("Stranger Things", 2016),
        ShowDocument("Ozark", 2017)
    )

    private val shows = listOf(
        Show("Stranger Things", 2016),
        Show("Ozark", 2017)
    )

    private val showPersistenceAdapter = ShowPersistenceAdapter(showsDocument)

    @Test
    fun `should return all the shows when no filter temp`() {
        // GIVEN

        // WHEN
        val showsResult = showPersistenceAdapter.findShows(NoFilter)

        // THEN
        showsResult shouldBeRight shows
    }

    @Test
    fun `should return shows matching the predicate of the filter`() {
        // GIVEN

        // WHEN
        val showsResult = showPersistenceAdapter.findShows(FilterByTitle("Stranger"))

        // THEN
        showsResult shouldBeRight listOf(Show("Stranger Things", 2016))
    }

    @Test
    fun `should return an error when the filter match error case`() {
        // GIVEN

        // WHEN
        val showsResult = showPersistenceAdapter.findShows(FilterByTitle(ERROR_CASE))

        // THEN
        showsResult shouldBeLeft ShowError.ShowPersistenceError("An error occurred in persistence")
    }

}