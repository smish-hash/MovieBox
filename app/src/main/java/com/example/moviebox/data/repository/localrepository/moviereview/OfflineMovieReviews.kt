package com.example.moviebox.data.repository.localrepository.moviereview

import com.example.moviebox.data.model.movieReview.MovieReviewModel
import kotlinx.coroutines.flow.Flow

interface OfflineMovieReviews {
    fun getMovieReviews(movieId: Int): Flow<MovieReviewModel>

    suspend fun insertMovieReviews(movieReview: MovieReviewModel)
}