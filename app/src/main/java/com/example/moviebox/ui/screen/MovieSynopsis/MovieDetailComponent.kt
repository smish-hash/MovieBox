package com.example.moviebox.ui.screen.MovieSynopsis

import android.os.Build
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviebox.R
import com.example.moviebox.data.model.moviedetail.MovieDetailModel
import com.example.moviebox.ui.screen.ImageLoading
import com.example.moviebox.ui.screen.ImageLoadingError
import com.example.moviebox.ui.state.MovieDetailState
import com.example.moviebox.ui.viewmodel.MovieDetailViewModel
import com.example.moviebox.util.Constants
import com.example.moviebox.util.convertToFormattedDate
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
    val message: String = if (availability== "Released") {
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

        Card (
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 160.dp, max = 200.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {
            Box {
                val imageUrl = Constants.BASE_IMAGE_URL+data.backdropPath

                CoilImage(
                    modifier = Modifier.fillMaxSize(),
                    imageModel = { imageUrl },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop
                    ),
                    previewPlaceholder = R.drawable.nature,
                    loading = {
                        ImageLoading()
                    },
                    failure = {
                        ImageLoadingError(errorImage = R.drawable.baseline_error_outline_24)
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.BottomStart),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Surface (
                        color = Color.Black,
                        modifier = Modifier.alpha(0.8f)
                    ) {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                        )
                    }
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
            Text(text = "$voteAvg/10", style = MaterialTheme.typography.labelLarge)
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
                val spokenLanguageList =
                    data.spokenLanguages?.size?.let {
                        if (it > 3) data.spokenLanguages.subList(0,3)
                        else data.spokenLanguages
                    }
                val string = spokenLanguageList?.joinToString(separator = ", ") {
                    "${it.englishName}"
                }
                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp),
                    text = string?:"N/A",
                    style = MaterialTheme.typography.labelSmall
                )
            }

        }

        val genreList = data.genres?.size?.let {
            if (it > 3) data.genres.subList(0, 3)
            else data.genres
        }
        val genre = genreList?.joinToString(separator = ", ") {
            "${it?.name}"
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Text(
                text = "${data.runtime?.toHoursMinutes()}  •  $genre  •  $adult  •  ${data.releaseDate?.convertToFormattedDate()}",
                maxLines = 2,
                modifier = Modifier
                    .padding(top = 2.dp, start = 6.dp, end = 6.dp)
                    .fillMaxWidth(),
                fontSize = 11.sp
            )
        } else {
            Text(
                text = "${data.runtime?.toHoursMinutes()}  •  $genre  •  $adult  •  ${data.releaseDate}",
                maxLines = 2,
                modifier = Modifier
                    .padding(top = 2.dp, start = 6.dp, end = 6.dp)
                    .fillMaxWidth(),
                fontSize = 11.sp
            )
        }

        ExpandableText(
            text = data.overview.toString()
        )
    }
}

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    text: String,
    collapsedMaxLine: Int = 3,
    showMoreText: String = "...More",
    showMoreStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.W500, color = Color.Red)
) {
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableStateOf(0) }
    Box(modifier = Modifier
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            enabled = clickable
        ) {
            isExpanded = !isExpanded
        }
        .then(modifier)
    ) {
        Text(
            modifier = textModifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(6.dp),
            text = buildAnnotatedString {
                if (clickable) {
                    if (isExpanded) {
                        append(text)
                    } else {
                        val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        withStyle(style = showMoreStyle) { append(showMoreText) }
                    }
                } else {
                    append(text)
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
            },
            fontSize = 11.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieDetailScreen() {
    DataLoaded(
        MovieDetailModel(
            title = "Spiderman",
            voteAverage = 3.54,
            voteCount = 6947930,
            runtime = 170,
            overview = "After reuniting with Gwen Stacy, Brooklyn’s full-time, friendly neighborhood Spider-Man is catapulted across the Multiverse, where he encounters the Spider Society, a team of Spider-People charged with protecting the Multiverse’s very existence. But when the heroes clash on how to handle a new threat, Miles finds himself pitted against the other Spiders and must set out on his own to save those he loves most."
        )
    )
}