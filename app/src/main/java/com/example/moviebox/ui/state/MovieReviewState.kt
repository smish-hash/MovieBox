package com.example.moviebox.ui.state

import com.example.moviebox.data.model.movieReview.MovieReviewModel

sealed class MovieReviewState {
    object Empty : MovieReviewState()
    object Loading : MovieReviewState()
    class Success(val data: MovieReviewModel) : MovieReviewState()
    class Error(val message: String) : MovieReviewState()
}
