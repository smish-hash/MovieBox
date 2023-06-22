package com.example.moviebox.ui.screen.MovieSynopsis

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.moviebox.R

@Composable
fun MovieSynopsisScreen(movieId: Int, onBookTicketClicked: () -> Unit, modifier: Modifier) {
    Column {
        Text(text = "Welcome to synopsis page - $movieId", modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium )))

        CastAndCrew(movieId)
    }
}