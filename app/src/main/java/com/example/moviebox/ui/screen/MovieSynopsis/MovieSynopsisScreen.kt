package com.example.moviebox.ui.screen.MovieSynopsis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviebox.R
import com.example.moviebox.ui.state.CastAndCrewState
import com.example.moviebox.ui.viewmodel.CastAndCrewViewModel

@Composable
fun MovieSynopsisScreen(castAndCrewViewModel: CastAndCrewViewModel = hiltViewModel(), movieId: Int, onBookTicketClicked: () -> Unit, modifier: Modifier) {
    Column {
        Text(text = "Welcome to synopsis page - $movieId", modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium )))

        when (val state = castAndCrewViewModel.castAndCrewState.collectAsState().value) {
            is CastAndCrewState.Empty -> {
                Text(
                    text = "No data available",
                    modifier = Modifier.padding(16.dp)
                )
                castAndCrewViewModel.fetchCastAndCrew(movieId)

            }
            is CastAndCrewState.Loading ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            is CastAndCrewState.Error -> {
                Text(
                    text = "error found - ${state.message}",
                    modifier = Modifier.padding(16.dp)
                )
            }
            is CastAndCrewState.Success -> {
                Text(
                    text = "fetched cast and crew",
                    modifier = Modifier.padding(16.dp)
                )
                CastAndCrew(state.data)
            }

        }
    }
}