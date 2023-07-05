package com.example.moviebox.data.repository.localrepository

import com.example.moviebox.data.dao.MovieDao
import com.example.moviebox.data.model.movielist.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineMoviesRepository @Inject constructor(
    private val movieDao: MovieDao
): MovieRepository {
    override fun getAllMovies(): Flow<List<Result>> {
       return movieDao.getAllItems()
    }

    override suspend fun insertMovie(movies: List<Result>) {
        movies.forEach {
            movieDao.insertMovie(movie = it)
        }

    }

    override suspend fun deleteAllMovies() {
        movieDao.deleteMovies()
    }
}

