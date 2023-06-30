package com.example.moviebox.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.data.model.MovieSynopsisModel
import com.example.moviebox.data.model.castcrew.CastAndCrewModel
import com.example.moviebox.data.model.movieReview.MovieReviewModel
import com.example.moviebox.data.model.moviedetail.MovieDetailModel
import com.example.moviebox.data.repository.CastAndCrewRepository
import com.example.moviebox.data.repository.MovieDetailRepository
import com.example.moviebox.data.repository.MovieReviewRepository
import com.example.moviebox.ui.state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSynopsisViewModel @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
    private val movieCastAndCrewRepository: CastAndCrewRepository,
    private val movieReviewRepository: MovieReviewRepository
): ViewModel() {

    private val _movieDetailState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val movieDetailState: StateFlow<ScreenState> = _movieDetailState

    private val _movieId = MutableLiveData<Int>()
    val movieId: LiveData<Int>
        get() = _movieId

    fun setMovieId(movieId: Int) {
        _movieId.value = movieId
    }

    fun fetchMovieSynopsis() {
        viewModelScope.launch {
            try {
                val result = performSynopsisCall()
                _movieDetailState.value = ScreenState.Success(result)
            } catch (e: Exception) {
                onErrorOccurred(e.localizedMessage)
            }
        }
    }

    private suspend fun performSynopsisCall(): MovieSynopsisModel = coroutineScope {

        val resDetails = async { fetchMovieDetails() }
        val resCastCrew = async { fetchCastAndCrew() }
        val resReviews = async { fetchMovieReviews() }

        try {
            val resultDetails = resDetails.await()
            val resultCastCrew = resCastCrew.await()
            val resultReviews = resReviews.await()
            return@coroutineScope MovieSynopsisModel(movieDetail = resultDetails, movieCastCrew = resultCastCrew, movieReview = resultReviews)
        } catch(e: Exception) {
            throw e
        }
    }

    private suspend fun fetchMovieDetails(): MovieDetailModel {
        val movieDetail= _movieId.value?.let { movieDetailRepository.getMovieDetail(it) }
        return movieDetail ?: MovieDetailModel()
    }

    private suspend fun fetchCastAndCrew(): CastAndCrewModel {
        val movieCastAndCrew = _movieId.value?.let {movieCastAndCrewRepository.getCastAndCrew(it)}
        return movieCastAndCrew ?: CastAndCrewModel()
    }

    private suspend fun fetchMovieReviews(): MovieReviewModel {
        val movieReview = _movieId.value?.let { movieReviewRepository.getMovieReview(it)}
        return movieReview ?: MovieReviewModel()
    }


    private fun onErrorOccurred(error: String?) {
        _movieDetailState.value = ScreenState.Error(error ?: "Unknown Error Occurred")
    }
}