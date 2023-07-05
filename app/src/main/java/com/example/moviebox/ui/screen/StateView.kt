package com.example.moviebox.ui.screen

import androidx.compose.runtime.Composable
import com.example.moviebox.ui.state.ScreenState

@Composable
fun StateView(
    uiState: ScreenState,
    loadingComposable: @Composable () -> Unit = { Loading() },
    errorComposable: @Composable (String) -> Unit,
    successComposable: @Composable (Any) -> Unit,
) {
    when (uiState) {
        ScreenState.Empty -> {
            loadingComposable()
        }

        is ScreenState.Error -> errorComposable(uiState.message)
        ScreenState.Loading -> loadingComposable()
        is ScreenState.Success -> {
            successComposable(uiState.data)
        }
    }
}