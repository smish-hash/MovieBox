package com.example.moviebox.data.repository.networkrepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.moviebox.data.api.MovieListApi
import com.example.moviebox.data.model.movielist.MovieListModel
import com.example.moviebox.data.repository.datasource.MovieListPagingSource
import javax.inject.Inject

class MovieListRepository @Inject constructor(
    private val movieListApi: MovieListApi
) {
    // will be used by view model
    suspend fun getPopularMovies(page: Int): MovieListModel {
        return movieListApi.getMovies(page)
    }

    fun getPaginatedPopularMovies() = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            MovieListPagingSource(movieListApi)
        }
    ).flow
}