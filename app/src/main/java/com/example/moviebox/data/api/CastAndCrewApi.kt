package com.example.moviebox.data.api

import com.example.moviebox.data.model.castcrew.CastAndCrewModel
import com.example.moviebox.util.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface CastAndCrewApi {

    @Headers("Authorization: Bearer ${Constants.AUTH_KEY}")
    @GET("{movie_id}/credits")
    suspend fun getCastAndCrew(
        @Path("movie_id") movieId: Int,
        @Query("language")
        language: String = "en-US"
    ): CastAndCrewModel
}