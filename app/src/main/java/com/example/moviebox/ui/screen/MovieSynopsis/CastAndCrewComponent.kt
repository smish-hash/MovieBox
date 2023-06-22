package com.example.moviebox.ui.screen.MovieSynopsis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviebox.R
import com.example.moviebox.data.model.castcrew.Cast
import com.example.moviebox.data.model.castcrew.CastAndCrewModel
import com.example.moviebox.data.model.castcrew.Crew
import com.example.moviebox.ui.state.CastAndCrewState
import com.example.moviebox.ui.viewmodel.CastAndCrewViewModel
import com.example.moviebox.util.Constants
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CastAndCrew(
    movieId: Int,
    castAndCrewViewModel: CastAndCrewViewModel = hiltViewModel()
) {
    val state = castAndCrewViewModel.castAndCrewState.collectAsState().value
    
    LaunchedEffect(movieId) {
        castAndCrewViewModel.fetchCastAndCrew(movieId)
    }

    when (state) {
        is CastAndCrewState.Empty -> {
            Text(
                text = "No data available",
                modifier = Modifier.padding(16.dp)
            )
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
            DataLoaded(state.data)
        }

    }
}

@Composable
fun DataLoaded(data: CastAndCrewModel) {
    Column {
        Divider()
        data.cast?.let { CastComponent(modifier = Modifier, "Cast", it) }
        Divider()
        data.crew?.let { CrewComponent(modifier = Modifier, "Crew", it) }
    }
}

@Composable
fun CastComponent(modifier: Modifier, text: String, members: List<Cast>) {

    Column(modifier = modifier.padding(top = 16.dp, start = 16.dp, bottom = 8.dp)) {
        Text(text = text)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)){
            items(members.subList(0, 10)) {
                CastCard(it, modifier)
            }
        }
    }
}


@Composable
fun CrewComponent(modifier: Modifier, text: String, members: List<Crew>) {

    Column(modifier = modifier.padding(top = 16.dp, start = 16.dp, bottom = 8.dp)) {
        Text(text = text)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)){
            items(members.subList(0, 10)) {
                CrewCard(it, modifier)
            }
        }
    }
}
@Composable
fun CastCard(cast: Cast, modifier: Modifier){
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))) {
        val imageUrl = Constants.BASE_IMAGE_URL+cast.profilePath

        CoilImage(
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        cast.name?.let { Text(text = it, modifier = Modifier.width(120.dp), textAlign = TextAlign.Center, style = MaterialTheme.typography.titleSmall, maxLines = 2) }
        cast.character?.let { Text(text = it, color = Color.Gray, style = MaterialTheme.typography.labelSmall, modifier = Modifier.width(120.dp), textAlign = TextAlign.Center, maxLines = 2) }
    }
}

@Composable
fun CrewCard(crew: Crew, modifier: Modifier){
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))) {
        val imageUrl = Constants.BASE_IMAGE_URL+crew.profilePath

        CoilImage(
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        crew.name?.let { Text(text = it) }
        crew.job?.let { Text(text = it, color = Color.Gray, fontSize = 10.sp) }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewCommonCastCrewComponent() {
    Column() {
        Text(text = "Test String")
        Divider()
        CastComponent(modifier = Modifier, "Cast", emptyList())

        Divider()
        CrewComponent(modifier = Modifier, "Crew", emptyList())

    }
}