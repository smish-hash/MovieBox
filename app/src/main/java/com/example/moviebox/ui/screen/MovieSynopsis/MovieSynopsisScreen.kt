package com.example.moviebox.ui.screen.MovieSynopsis

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.moviebox.R

@Composable
fun MovieSynopsisScreen(movieId: Int, onBookTicketClicked: () -> Unit, modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        MovieDetail(movieId)
        CastAndCrew(movieId)
        MovieReview(movieId)
    }
}