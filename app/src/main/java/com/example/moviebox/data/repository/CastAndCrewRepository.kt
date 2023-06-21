package com.example.moviebox.data.repository

import com.example.moviebox.data.api.CastAndCrewApi
import com.example.moviebox.data.model.castcrew.CastAndCrewModel
import javax.inject.Inject

class CastAndCrewRepository @Inject constructor(
    private val castAndCrewApi: CastAndCrewApi
){
    suspend fun getCastAndCrew(movieId: Int): CastAndCrewModel {
        return castAndCrewApi.getCastAndCrew(movieId = movieId)
    }
}