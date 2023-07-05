package com.example.moviebox.ui.state

sealed class ScreenState{
    object Empty : ScreenState()
    object Loading : ScreenState()
    class Error(val message: String) : ScreenState()
    data class Success(val data: Any): ScreenState()
}

