package com.example.moviebox.ui.state

import com.example.moviebox.data.model.moviedetail.MovieDetailModel

sealed class MovieDetailState {
    object Empty : MovieDetailState()
    object Loading : MovieDetailState()
    class Success(val data: MovieDetailModel) : MovieDetailState()
    class Error(val message: String) : MovieDetailState()
}
