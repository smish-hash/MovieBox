package com.example.moviebox.ui.screen.MovieSynopsis

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviebox.R
import com.example.moviebox.data.model.moviedetail.MovieDetailModel
import com.example.moviebox.data.model.moviedetail.SpokenLanguage
import com.example.moviebox.ui.state.CastAndCrewState
import com.example.moviebox.ui.state.MovieDetailState
import com.example.moviebox.ui.viewmodel.MovieDetailViewModel
import com.example.moviebox.util.Constants
import com.example.moviebox.util.toHoursMinutes
import com.example.moviebox.util.toShortenedString
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import java.math.RoundingMode
import java.text.DecimalFormat

@Composable
fun MovieDetail(movieId: Int, movieDetailViewModel: MovieDetailViewModel = hiltViewModel()) {
    val state = movieDetailViewModel.movieDetailState.collectAsState().value
    LaunchedEffect(movieId) {
        movieDetailViewModel.fetchMovieDetail(movieId = movieId)
    }

    when (state) {
        is MovieDetailState.Empty -> {
            Text(
                text = "No data available",
                modifier = Modifier.padding(16.dp)
            )
        }
        is MovieDetailState.Loading ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        is MovieDetailState.Error -> {
            Text(
                text = "error found - ${state.message}",
                modifier = Modifier.padding(16.dp)
            )
        }
        is MovieDetailState.Success -> {

            DataLoaded(state.data)
        }

    }

}

@Composable
fun DataLoaded(data: MovieDetailModel) {


    val availability = data.status//from API
    var message = ""
    message = if (availability== "Released") {
        "Available in Cinemas"
    } else {
        "Coming Soon In Cinemas"
    }

    val adult = if(data.adult == true){
        "A"
    } else {
        "UA"
    }

    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
    ) {

        Text(
            text = data.title.toString(),
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium
        )

        Card(
            modifier = Modifier.then(
                Modifier
                    .fillMaxWidth()
                    .height(165.dp)
            ),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {
            Box(modifier = Modifier) {
                val imageUrl = Constants.BASE_IMAGE_URL+data.backdropPath

                CoilImage(
                    imageModel = { imageUrl },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop
                    ),
                    previewPlaceholder = R.drawable.spider
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .alpha(0.7f)
                            .fillMaxWidth()
                            .background(Color.Black),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }
        Row(
            Modifier
                .padding(start = 6.dp, top = 8.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.padding(end = 6.dp)
            )
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.DOWN
            val voteAvg = df.format(data.voteAverage)
            Text(text = "${voteAvg.toString()}/10", style = MaterialTheme.typography.labelLarge)
            Text(
                text = "${data.voteCount?.toShortenedString()} Votes >",
                modifier = Modifier.padding(start = 8.dp), fontSize = 11.sp
            )
        }

        Column(
            Modifier
                .padding(start = 6.dp, top = 8.dp)
                .fillMaxWidth()
        ) {

            Surface(
                color = Color.LightGray,
                modifier = Modifier
                    .clip(RoundedCornerShape(3.dp))
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp),
                    text = "2D, 3D, ICE, 4DX, MX4D, IMAX 2D",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Surface(
                color = Color.LightGray,
                modifier = Modifier
                    .clip(RoundedCornerShape(3.dp))
            ) {

                val string = data.spokenLanguages?.joinToString(separator = ",") {it ->
                    "${it?.englishName}"
                }
                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp),
                    text = string?:"N/A",
                    style = MaterialTheme.typography.labelSmall
                )
            }

        }
        val genre = data.genres?.subList(0,3)?.joinToString(separator = ",") {it ->
            "${it?.name}"
        }
        Text(
            text = "${data.runtime?.toHoursMinutes()}  •  $genre  •  $adult  •  ${data.releaseDate}",
            maxLines = 2,
            modifier = Modifier
                .padding(top = 2.dp, start = 6.dp, end = 6.dp)
                .fillMaxWidth(),
            fontSize = 11.sp
        )

        Text(
            text = data.overview.toString(),
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
            fontSize = 11.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun previewMovieDetailScreen() {
    DataLoaded(
        MovieDetailModel(
            title = "Spiderman",
            voteAverage = 3.54,
            voteCount = 6947930,
            runtime = 170
        )
    )
}