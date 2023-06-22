package com.example.moviebox.ui.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.data.repository.CastAndCrewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.moviebox.ui.state.CastAndCrewState


@HiltViewModel
class CastAndCrewViewModel @Inject constructor(
    private val castAndCrewRepository: CastAndCrewRepository
): ViewModel() {
    private val _castAndCrewState = MutableStateFlow<CastAndCrewState>(CastAndCrewState.Empty)
    val castAndCrewState: StateFlow<CastAndCrewState> = _castAndCrewState

    fun fetchCastAndCrew(movieId: Int) {
        _castAndCrewState.value = CastAndCrewState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val castAndCrew = castAndCrewRepository.getCastAndCrew(movieId)
                _castAndCrewState.value = CastAndCrewState.Success(castAndCrew)
            } catch (e: Exception) {
                // Handle error
                onErrorOccurred(e.localizedMessage)
            }
        }
    }
    private fun onErrorOccurred(error: String) {
        _castAndCrewState.value = CastAndCrewState.Error(error)
    }
}