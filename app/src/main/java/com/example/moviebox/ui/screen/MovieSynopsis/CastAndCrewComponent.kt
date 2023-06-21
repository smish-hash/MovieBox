package com.example.moviebox.ui.screen.MovieSynopsis

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.moviebox.data.model.castcrew.CastAndCrewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CastAndCrew() {
    Column(modifier = Modifier) {
        Column() {
            Text(text = "Test String")
            Divider()
            CommonCastCrewComponent(modifier = Modifier, "Cast")

            Divider()
            CommonCastCrewComponent(modifier = Modifier, "Crew")

        }
    }
}

@Composable
fun CommonCastCrewComponent(modifier: Modifier, text: String) {
//    Column(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))) {
    Column(modifier = modifier.padding(top = 16.dp, start = 16.dp, bottom = 8.dp)) {
        Text(text = text)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)){
            items(10) {
                ItemCard(modifier)
            }
        }
    }
}

@Composable
//fun ItemCard(modifier: Modifier, member: CastAndCrewModel) {
fun ItemCard(modifier: Modifier){
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))) {
        Image(
            painter = painterResource(id = R.drawable.nature),
            contentDescription = "Artist Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
        )
        Text(text = "Prabhas")
        Text(text = "as Prabhas", color = Color.Gray, fontSize = 10.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCommonCastCrewComponent() {
    Column() {
        Text(text = "Test String")
        Divider()
        CommonCastCrewComponent(modifier = Modifier, "Cast")

        Divider()
        CommonCastCrewComponent(modifier = Modifier, "Crew")

    }
}