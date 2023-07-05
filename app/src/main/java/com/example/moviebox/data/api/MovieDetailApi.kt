package com.example.moviebox.data.api

import com.example.moviebox.BuildConfig
import com.example.moviebox.data.model.moviedetail.MovieDetailModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface MovieDetailApi {
    @Headers("Authorization: Bearer ${BuildConfig.AUTH_KEY}")
    @GET("{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId:Int
    ): MovieDetailModel
}