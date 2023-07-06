package com.example.moviebox.ui.screen.MovieSynopsis

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviebox.data.model.MovieSynopsisModel
import com.example.moviebox.ui.theme.PinkPrimary

@Composable
fun MovieSynopsisScreen(movieSynopsis: MovieSynopsisModel, onBookTicketClicked: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 64.dp)
        ) {
            movieSynopsis.movieDetail?.let { MovieDetail(it) }
            movieSynopsis.movieCastCrew?.let { CastAndCrew(it) }
            movieSynopsis.movieReview?.let { MovieReview(it) }
        }

        Surface(
            modifier = Modifier.align(Alignment.BottomCenter),
            shadowElevation = 3.dp
        ) {
            Button(
                onClick = {
                    onBookTicketClicked()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinkPrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .fillMaxWidth()

            ) {
                Text(text = "Book tickets",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview (showSystemUi = true, showBackground = true)
@Composable
fun SynopsisPreview() {

    Box(modifier = Modifier.fillMaxSize()) {

        Surface(
            modifier = Modifier.align(Alignment.BottomCenter),
            shadowElevation = 3.dp
        ) {
            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .fillMaxWidth(),
                elevation = ButtonDefaults.buttonElevation()

            ) {
                Text(text = "Book tickets",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp)
                )
            }
        }
    }
}