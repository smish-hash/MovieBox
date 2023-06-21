package com.example.moviebox.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.data.repository.MovieReviewRepository
import com.example.moviebox.ui.state.MovieReviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieReviewViewModel @Inject constructor(
    private val movieReviewRepository: MovieReviewRepository
): ViewModel() {

    private val _movieReviewState = MutableStateFlow<MovieReviewState>(MovieReviewState.Empty)
    val movieReviewState: StateFlow<MovieReviewState> = _movieReviewState

    /*init {
        fetchMovieReviews()
    }*/

    fun fetchMovieReviews() {
        _movieReviewState.value = MovieReviewState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movieReview = movieReviewRepository.getMovieReview(1)
                _movieReviewState.value = MovieReviewState.Success(movieReview)
            } catch (e: Exception) {
                // Handle error
                onErrorOccurred(e.localizedMessage)
            }
        }
    }

    private fun onErrorOccurred(error: String) {
        _movieReviewState.value = MovieReviewState.Error(error)
    }
}