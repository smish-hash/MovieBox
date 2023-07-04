package com.example.moviebox.data.repository.localrepository

import com.example.moviebox.data.model.movielist.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getAllMovies(): Flow<List<Result>>

    suspend fun insertMovie(movie: Result)

    suspend fun deleteAllMovies()
}