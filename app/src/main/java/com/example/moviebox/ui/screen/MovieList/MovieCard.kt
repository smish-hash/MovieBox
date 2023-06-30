package com.example.moviebox.ui.screen.MovieList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviebox.R
import com.example.moviebox.data.model.movielist.Result
import com.example.moviebox.ui.screen.ImageLoading
import com.example.moviebox.ui.screen.ImageLoadingError
import com.example.moviebox.ui.theme.*
import com.example.moviebox.util.Constants
import com.example.moviebox.util.toShortenedString
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun MovieCard(
    onMovieClick: (Result?) -> Unit,
    movie: Result
) {

    Column(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(16.dp)
            .width(250.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                enabled = true,
                onClick = {onMovieClick(movie)}
            )
    ) {

        val imageUrl = Constants.BASE_IMAGE_URL + movie.posterPath

        CoilImage(
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            previewPlaceholder = R.drawable.nature,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .heightIn(min = 165.dp),
            loading = {
                ImageLoading()
            },
            failure = {
                ImageLoadingError(errorImage = R.drawable.baseline_error_outline_24)
            }

        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(color = LightBlue)
                .heightIn(28.dp)
                .padding(vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Rounded.Star,
                contentDescription = null,
                tint = PinkSecondary,
                modifier = Modifier
                    .padding(start = 6.dp, end = 3.dp)
                    .size(20.dp)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = movie.voteAverage.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = Modifier.padding(end = 6.dp),
                text = "${movie.voteCount?.toShortenedString()} votes",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

        movie.title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
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