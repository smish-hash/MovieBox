package com.example.moviebox.ui.screen.MovieList

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.moviebox.data.model.movielist.Result
import com.example.moviebox.util.Constants
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(
    onMovieClick: (Int?) -> Unit,
    movie: Result
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(size = 12.dp),
        onClick = {onMovieClick(movie.id)}
    ) {

        Column {
            val imageUrl = Constants.BASE_IMAGE_URL+movie.posterPath

            CoilImage(
                imageModel = { imageUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            )

            Row(modifier = Modifier
                .width(250.dp)
                .padding(bottom = 8.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.LightGray)
                .padding(top = 3.dp, bottom = 3.dp)
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.padding(start = 5.dp)
                )
                Text(
                    text = "rating"
                )
            }
            movie.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .paddingFromBaseline(top = 16.dp))
            }
        }
    }
}