package com.example.moviebox.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.data.model.movielist.MovieListModel
import com.example.moviebox.data.repository.MovieDetailRepository
import com.example.moviebox.data.repository.MovieListRepository
import com.example.moviebox.ui.state.MovieDetailState
import com.example.moviebox.ui.state.MovieListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
): ViewModel() {

    private val _movieDetailState = MutableStateFlow<MovieDetailState>(MovieDetailState.Empty)
    val movieDetailState: StateFlow<MovieDetailState> = _movieDetailState

    fun fetchMovieDetail(movieId: Int) {
        _movieDetailState.value = MovieDetailState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movieDetail = movieDetailRepository.getMovieDetail(movieId)
                _movieDetailState.value = MovieDetailState.Success(movieDetail)
            } catch (e: Exception) {
                // Handle error
                onErrorOccurred(e.localizedMessage)
            }
        }
    }

    private fun onErrorOccurred(error: String) {
        _movieDetailState.value = MovieDetailState.Error(error)
    }
}