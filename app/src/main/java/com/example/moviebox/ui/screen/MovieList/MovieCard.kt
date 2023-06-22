package com.example.moviebox.ui.screen.MovieList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviebox.R
import com.example.moviebox.data.model.movielist.Result
import com.example.moviebox.util.Constants
import com.example.moviebox.util.toShortenedString
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun MovieCard(
    onMovieClick: (Int?) -> Unit,
    movie: Result
) {

    Column(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(16.dp)
            .width(250.dp)
            .clickable { onMovieClick(movie.id) }
    ) {
        val imageUrl = Constants.BASE_IMAGE_URL + movie.posterPath

        CoilImage(
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            previewPlaceholder = R.drawable.nature,
            modifier = Modifier.clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(vertical = dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.padding(start = 6.dp, end = 6.dp)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = movie.voteAverage.toString()
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = Modifier.padding(end = 6.dp),
                text = "${movie.voteCount?.toShortenedString()} votes",
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

        movie.title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    MovieCard(
        onMovieClick = {},
        movie = Result(title = "Spiderman: Beyond the spider verse")
    )
}