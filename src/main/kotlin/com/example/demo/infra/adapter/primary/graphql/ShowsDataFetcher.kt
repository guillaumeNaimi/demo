package com.example.demo.infra.adapter.primary.graphql

import arrow.core.getOrHandle
import com.example.demo.domain.model.Show
import com.example.demo.domain.port.primary.ShowManagerPort
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import graphql.schema.DataFetchingEnvironment


@DgsComponent
class ShowsDataFetcher(private val showManagerPort: ShowManagerPort) {

    @DgsQuery
    fun shows(@InputArgument titleFilter : String?): List<Show> =
        showManagerPort.getShows(titleFilter).getOrHandle { throw it }
}


