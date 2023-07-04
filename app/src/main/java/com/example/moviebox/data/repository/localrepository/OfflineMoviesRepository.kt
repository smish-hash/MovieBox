package com.example.moviebox.data.repository.localrepository

import com.example.moviebox.data.dao.MovieDao
import com.example.moviebox.data.model.movielist.Result
import kotlinx.coroutines.flow.Flow

class OfflineMoviesRepository(
    private val movieDao: MovieDao
): MovieRepository {
    override fun getAllMovies(): Flow<List<Result>> {
       return movieDao.getAllItems()
    }

    override suspend fun insertMovie(movie: Result) {
        movieDao.insertMovie(movie = movie)
    }

    override suspend fun deleteAllMovies() {
        movieDao.deleteMovies()
    }
}

