package com.example.moviebox.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.data.model.MovieSynopsisModel
import com.example.moviebox.data.model.castcrew.CastAndCrewModel
import com.example.moviebox.data.model.movieReview.MovieReviewModel
import com.example.moviebox.data.model.moviedetail.MovieDetailModel
import com.example.moviebox.data.repository.localrepository.moviedetails.OfflineMovieDetailsRepository
import com.example.moviebox.data.repository.networkrepository.CastAndCrewRepository
import com.example.moviebox.data.repository.networkrepository.MovieDetailRepository
import com.example.moviebox.data.repository.networkrepository.MovieReviewRepository
import com.example.moviebox.ui.state.ScreenState
import com.example.moviebox.util.NetworkUtil.Companion.hasInternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieSynopsisViewModel @Inject constructor(
    private val offlineMovieDetailsRepository: OfflineMovieDetailsRepository,
    private val movieDetailRepository: MovieDetailRepository,
    private val movieCastAndCrewRepository: CastAndCrewRepository,
    private val movieReviewRepository: MovieReviewRepository,
    @ApplicationContext private val context: Context
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
                if (hasInternetConnection(context)) {
                    val result = performSynopsisCall()
                    _movieDetailState.value = ScreenState.Success(result)
                    result.movieDetail?.let { saveMovieDetail(it) }
                } else {
                    // check db
                    _movieId.value?.let {
                        getMovieDetailFromDB(it)
                    }
                }
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
        val movieDetail = withContext(Dispatchers.IO) {
            _movieId.value?.let { movieDetailRepository.getMovieDetail(it) }
        }
        return movieDetail ?: MovieDetailModel()
    }

    private suspend fun fetchCastAndCrew(): CastAndCrewModel {
        val movieCastAndCrew = withContext(Dispatchers.IO){
            _movieId.value?.let {movieCastAndCrewRepository.getCastAndCrew(it)}
        }
        return movieCastAndCrew ?: CastAndCrewModel()
    }

    private suspend fun fetchMovieReviews(): MovieReviewModel {
        val movieReview = withContext(Dispatchers.IO) {
            _movieId.value?.let {movieReviewRepository.getMovieReview(it)}
        }
        return movieReview ?: MovieReviewModel()
    }


    private fun onErrorOccurred(error: String?) {
        _movieDetailState.value = ScreenState.Error(error ?: "Unknown Error Occurred")
    }

    private suspend fun saveMovieDetail(movieDetails: MovieDetailModel) {
        offlineMovieDetailsRepository.insertMovieDetail(movieDetails)
    }

    private suspend fun getMovieDetailFromDB(movieId: Int) {
        val movie = offlineMovieDetailsRepository.getMovieDetails(movieId).firstOrNull()
        // movie review
        if (movie != null) {
            _movieDetailState.value = ScreenState.Success(MovieSynopsisModel(movieDetail = movie))
        } else {
            _movieDetailState.value = ScreenState.Error("No internet connection")
        }
    }
}