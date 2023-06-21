package com.example.moviebox.ui.screen.MovieList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import com.example.moviebox.R
import com.example.moviebox.data.model.movielist.Result
import com.example.moviebox.util.Constants
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun MovieCard(
    movie: Result
) {
    Surface(modifier = Modifier.padding(16.dp)) {
        Column {
            Image(
                painterResource(id = R.drawable.nature),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 250.dp, height = 300.dp)
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            /*CoilImage(
                imageModel = { Constants.BASE_URL+movie.posterPath },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            )*/

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