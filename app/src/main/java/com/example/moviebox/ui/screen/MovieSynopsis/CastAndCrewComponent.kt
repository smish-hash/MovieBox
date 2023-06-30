package com.example.moviebox.ui.screen.MovieSynopsis

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviebox.R
import com.example.moviebox.data.model.castcrew.Cast
import com.example.moviebox.data.model.castcrew.CastAndCrewModel
import com.example.moviebox.data.model.castcrew.Crew
import com.example.moviebox.ui.screen.ImageLoading
import com.example.moviebox.ui.screen.ImageLoadingError
import com.example.moviebox.util.Constants
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CastAndCrew(data: CastAndCrewModel) {
    Column {
        Divider()
        data.cast?.let { CastComponent(modifier = Modifier, "Cast", it) }
        Divider()
        data.crew?.let { CrewComponent(modifier = Modifier, "Crew", it) }
    }
}

@Composable
fun CastComponent(modifier: Modifier, text: String, members: List<Cast>) {

    Column(modifier = modifier.padding(vertical = 16.dp)) {
        Text(
            text = text,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            modifier = modifier.padding(horizontal = 16.dp)
        )
        LazyRow {
            items(members.subList(0, 10)) {
                CastCard(it, modifier)
            }
        }
    }
}


@Composable
fun CrewComponent(modifier: Modifier, text: String, members: List<Crew>) {

    Column(
        modifier = modifier
            .padding(vertical = 16.dp)
    ) {
        Text(text = text,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            modifier = modifier.padding(horizontal = 16.dp))
        LazyRow{
            items(members.subList(0, 10)) {
                CrewCard(it, modifier)
            }
        }
    }
}

@Composable
fun CastCard(cast: Cast, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 14.dp, end = 6.dp, start = 6.dp)
    ) {
        val imageUrl = Constants.BASE_IMAGE_URL + cast.profilePath

        CoilImage(
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            previewPlaceholder = R.drawable.nature,
            modifier = Modifier
                .size(86.dp)
                .clip(CircleShape),
            loading = {
                ImageLoading()
            },
            failure = {
                ImageLoadingError(errorImage = R.drawable.baseline_person_24)
            }
        )
        Spacer(modifier = modifier.height(4.dp))
        cast.name?.let {
            Text(
                text = it,
                modifier = Modifier
                    .height(28.dp)
                    .width(60.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                lineHeight = 14.sp,
                maxLines = 2,
            )
        }
        cast.character?.let {
            Text(
                text = "as $it",
                color = Color.Gray,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .height(30.dp)
                    .width(80.dp),
                textAlign = TextAlign.Center,
                lineHeight = 12.sp,
                maxLines = 2
            )
        }
    }
}

@Composable
fun CrewCard(crew: Crew, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 14.dp, end = 6.dp, start = 6.dp)
    ) {
        val imageUrl = Constants.BASE_IMAGE_URL + crew.profilePath

        CoilImage(
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            previewPlaceholder = R.drawable.nature,
            modifier = Modifier
                .size(86.dp)
                .clip(CircleShape),
            loading = {
                ImageLoading()
            },
            failure = {
                ImageLoadingError(errorImage = R.drawable.baseline_person_24)
            }
        )
        Spacer(modifier = modifier.height(4.dp))
        crew.name?.let { Text(
            text = it,
            modifier = Modifier
                .height(28.dp)
                .width(60.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium,
            lineHeight = 14.sp,
            maxLines = 2,
        ) }
        crew.job?.let { Text(text = it,
            color = Color.Gray,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .height(30.dp)
                .width(80.dp),
            textAlign = TextAlign.Center,
            lineHeight = 12.sp,
            maxLines = 2) }
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