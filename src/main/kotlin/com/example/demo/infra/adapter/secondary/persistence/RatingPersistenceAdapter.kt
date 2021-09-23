package com.example.demo.infra.adapter.secondary.persistence

import arrow.core.Either
import arrow.core.Either.Companion.catch
import arrow.core.computations.either
import arrow.core.right
import com.example.demo.domain.errors.RatingError
import com.example.demo.domain.model.Rating
import com.example.demo.domain.port.secondary.RatingPersistencePort
import org.springframework.stereotype.Service

private val ratings = mutableListOf<Rating>()

@Service
class RatingPersistence: RatingPersistencePort {
    override fun addRating(rating: Rating): Either<RatingError, Rating> {
        ratings.add(rating)
        return rating.right()
    }

    override fun getRatingsByTitle(title: String): Either<RatingError, List<Rating>> =
        catch {
            ratings.filter { it.title == title }
        }.mapLeft { RatingError.RatingPersistenceError(e = it) }
}