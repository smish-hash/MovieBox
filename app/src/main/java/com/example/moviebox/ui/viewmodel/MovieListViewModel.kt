package com.example.moviebox.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.data.repository.networkrepository.MovieListRepository
import com.example.moviebox.ui.state.ScreenState
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

    private val _popularMoviesState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val popularMoviesState: StateFlow<ScreenState>
    get() = _popularMoviesState

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        _popularMoviesState.value = ScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = movieListRepository.getPopularMovies(1)
                _popularMoviesState.value = ScreenState.Success(movies)
            } catch (e: Exception) {
                // Handle error
                onErrorOccurred(e.localizedMessage as String)
            }
        }
    }

    private fun onErrorOccurred(error: String) {
        _popularMoviesState.value = ScreenState.Error(error)
    }
}