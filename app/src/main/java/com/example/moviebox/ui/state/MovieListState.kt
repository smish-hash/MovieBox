package com.example.moviebox.ui.state

import com.example.moviebox.data.model.movielist.MovieListModel

sealed class MovieListState {
    object Empty : MovieListState()
    object Loading : MovieListState()
    class Success(val data: MovieListModel) : MovieListState()
    class Error(val message: String) : MovieListState()
}
