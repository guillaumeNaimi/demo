package com.example.demo.infra.adapter.primary.graphql

import arrow.core.getOrHandle
import com.example.demo.domain.model.Rating
import com.example.demo.domain.port.primary.RatingManagerPort
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import graphql.schema.DataFetchingEnvironment

@DgsComponent
class RatingMutation(private val ratingManagerPort: RatingManagerPort) {
    @DgsData(parentType = "Mutation", field = "addRating")
    fun addRating(dataFetchingEnvironment: DataFetchingEnvironment): Rating {
        val stars = dataFetchingEnvironment.getArgument<Int>("stars")
        require(stars >= 1) { "Stars must be 1-5" }
        val title = dataFetchingEnvironment.getArgument<String>("title")
        println("Rated $title with $stars stars")
        return ratingManagerPort.addRating(stars, title).getOrHandle { throw it }
    }
}