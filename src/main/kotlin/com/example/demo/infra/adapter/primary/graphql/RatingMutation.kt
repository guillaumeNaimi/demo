package com.example.demo.infra.adapter.primary.graphql

import com.example.demo.domain.model.Rating
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import graphql.schema.DataFetchingEnvironment

@DgsComponent
class RatingMutation {
    @DgsData(parentType = "Mutation", field = "addRating")
    fun addRating(dataFetchingEnvironment: DataFetchingEnvironment): Rating {
        val stars = dataFetchingEnvironment.getArgument<Float>("stars")
        require(stars >= 1) { "Stars must be 1-5" }
        val title = dataFetchingEnvironment.getArgument<String>("title")
        println("Rated $title with $stars stars")
        return Rating(stars)
    }
}