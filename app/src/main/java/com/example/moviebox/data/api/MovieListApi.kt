package com.example.moviebox.data.api

import com.example.moviebox.BuildConfig
import com.example.moviebox.data.model.movielist.MovieListModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieListApi {
    @Headers("Authorization: Bearer ${BuildConfig.AUTH_KEY}")
    @GET("popular")
    suspend fun getMovies(
        @Query("page")
        page: Int,
        @Query("language")
        language: String = "en-US"
    ): MovieListModel
}