package com.example.moviebox.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.data.model.movielist.MovieListModel
import com.example.moviebox.data.repository.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
): ViewModel() {

    sealed class MovieListState {
        object Empty : MovieListState()
        object Loading : MovieListState()
        class Success(val data: MovieListModel) : MovieListState()
        class Error(val message: String) : MovieListState()
    }

    private val _popularMoviesState = MutableStateFlow<MovieListState>(MovieListState.Empty)
    val popularMoviesState: StateFlow<MovieListState> = _popularMoviesState

    /*init { tbd = later
        fetchPopularMovies()
    }*/

    fun fetchPopularMovies() {
        _popularMoviesState.value = MovieListState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = movieListRepository.getPopularMovies(1)
                _popularMoviesState.value = MovieListState.Success(movies)
            } catch (e: Exception) {
                // Handle error
                onErrorOccurred(e.localizedMessage)
            }
        }
    }

    private fun onErrorOccurred(error: String) {
        _popularMoviesState.value = MovieListState.Error(error)
    }
}