package com.example.moviebox.data.repository.networkrepository

import com.example.moviebox.data.api.MovieReviewApi
import com.example.moviebox.data.model.movieReview.MovieReviewModel
import javax.inject.Inject

class MovieReviewRepository @Inject constructor(
    private val movieReviewApi: MovieReviewApi
) {
    // will be used by view model
    suspend fun getMovieReview(movieId: Int): MovieReviewModel {
        return movieReviewApi.getMovieReviews(movieId)
    }
}