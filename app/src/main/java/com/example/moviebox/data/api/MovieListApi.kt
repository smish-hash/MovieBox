package com.example.moviebox.data.api

import com.example.moviebox.data.model.movielist.MovieListModel
import com.example.moviebox.util.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieListApi {
    @Headers("Authorization: Bearer ${Constants.AUTH_KEY}")
    @GET("popular")
    suspend fun getMovies(
        @Query("page")
        page: Int,
        @Query("language")
        language: String = "en-US"
    ): MovieListModel
}