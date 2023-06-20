package com.example.moviebox.data.repository

import com.example.moviebox.data.api.MovieListApi
import com.example.moviebox.data.model.movielist.MovieListModel
import javax.inject.Inject

class MovieListRepository @Inject constructor(
    private val movieListApi: MovieListApi
) {
    // will be used by view model
    suspend fun getPopularMovies(page: Int): MovieListModel {
        return movieListApi.getMovies(page)
    }
}