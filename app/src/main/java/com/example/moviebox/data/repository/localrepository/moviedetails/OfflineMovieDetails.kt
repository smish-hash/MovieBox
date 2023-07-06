package com.example.moviebox.data.repository.localrepository.moviedetails

import com.example.moviebox.data.model.moviedetail.MovieDetailModel
import kotlinx.coroutines.flow.Flow

interface OfflineMovieDetails {
    fun getMovieDetails(id:Int): Flow<MovieDetailModel>

    suspend fun insertMovieDetail(movieDetails: MovieDetailModel)
}