package com.example.moviebox.data.repository

import com.example.moviebox.data.api.MovieDetailApi
import com.example.moviebox.data.api.MovieListApi
import com.example.moviebox.data.model.moviedetail.MovieDetailModel
import com.example.moviebox.data.model.movielist.MovieListModel
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(
    private val movieDetailApi: MovieDetailApi
) {
    // will be used by view model
    suspend fun getMovieDetail(movieId: Int): MovieDetailModel {
        return movieDetailApi.getMovieDetail(movieId)
    }
}