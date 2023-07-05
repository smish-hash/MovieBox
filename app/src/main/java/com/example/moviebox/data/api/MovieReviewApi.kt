package com.example.moviebox.data.api

import com.example.moviebox.BuildConfig
import com.example.moviebox.data.model.movieReview.MovieReviewModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieReviewApi {
    @Headers("Authorization: Bearer ${BuildConfig.AUTH_KEY}")
    @GET("{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id")
        movieId: Int,
        @Query("language")
        language: String = "en-US",
        @Query("page")
        page: Int = 1
    ): MovieReviewModel
}