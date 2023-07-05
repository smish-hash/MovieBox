package com.example.moviebox.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.data.model.movielist.Result
import com.example.moviebox.data.repository.localrepository.OfflineMoviesRepository
import com.example.moviebox.data.repository.networkrepository.MovieListRepository
import com.example.moviebox.di.ApplicationScope
import com.example.moviebox.ui.state.ScreenState
import com.example.moviebox.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val offlineMoviesRepository: OfflineMoviesRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _popularMoviesState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val popularMoviesState: StateFlow<ScreenState>
    get() = _popularMoviesState

    init {
        observeOfflineMovies()
        fetchPopularMovies()
    }

    fun fetchPopularMovies() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _popularMoviesState.value = ScreenState.Loading
                if (NetworkUtil.hasInternetConnection(context = context)) {
                    val movies = movieListRepository.getPopularMovies(1)
                    saveMovie(movies.results)
                } else {
                    // check db
                    val offlineMovies = offlineMoviesRepository.getAllMovies().firstOrNull()
                    if( !offlineMovies.isNullOrEmpty() ){
                        // when data available in local db
                        _popularMoviesState.value = ScreenState.Success(offlineMovies)
                        _isRefreshing.emit(false)
                    } else {
//                         Local db is empty
                        onErrorOccurred("No Internet Connection")
                    }
                }
            } catch (e: Exception) {
                // Handle error
                onErrorOccurred(e.localizedMessage as String)
            }
        }
    }

    private suspend fun onErrorOccurred(error: String) {
        _popularMoviesState.value = ScreenState.Error(error)
        _isRefreshing.emit(false)
    }

    private suspend fun saveMovie(movies: List<Result>?) {
        if (movies != null) {
            offlineMoviesRepository.insertMovie(movies)
        }
    }
    private fun observeOfflineMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            offlineMoviesRepository.getAllMovies().collect{
                _popularMoviesState.value = ScreenState.Success(it)
            }
        }
    }
}