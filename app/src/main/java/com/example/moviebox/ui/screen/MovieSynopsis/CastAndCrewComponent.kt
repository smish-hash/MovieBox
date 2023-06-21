package com.example.moviebox.ui.screen.MovieSynopsis

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviebox.R
import com.example.moviebox.data.model.castcrew.Cast
import com.example.moviebox.data.model.castcrew.CastAndCrewModel
import com.example.moviebox.data.model.castcrew.Crew
import com.example.moviebox.util.Constants
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CastAndCrew(data: CastAndCrewModel) {
    Column(modifier = Modifier) {
        Column() {
            Divider()
            data.cast?.let { CastComponent(modifier = Modifier, "Cast", it) }

            Divider()
            data.crew?.let { CrewComponent(modifier = Modifier, "Crew", it) }

        }
    }
}

@Composable
fun CastComponent(modifier: Modifier, text: String, members: List<Cast>) {

    Column(modifier = modifier.padding(top = 16.dp, start = 16.dp, bottom = 8.dp)) {
        Text(text = text)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)){
            items(members) {
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
            items(members) {
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
            modifier = Modifier.size(64.dp)
                .clip(CircleShape)
        )
        cast.name?.let { Text(text = it) }
        cast.character?.let { Text(text = it, color = Color.Gray, fontSize = 10.sp) }
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
            modifier = Modifier.size(64.dp)
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