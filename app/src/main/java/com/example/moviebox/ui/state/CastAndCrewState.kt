package com.example.moviebox.ui.state

import com.example.moviebox.data.model.castcrew.CastAndCrewModel

sealed class CastAndCrewState {
    object Empty : CastAndCrewState()
    object Loading : CastAndCrewState()
    class Success(val data: CastAndCrewModel) : CastAndCrewState()
    class Error(val message: String) : CastAndCrewState()
}
