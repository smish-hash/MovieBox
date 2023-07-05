package com.example.moviebox.ui.screen.MovieSynopsis

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviebox.R
import com.example.moviebox.data.model.movieReview.MovieReviewModel
import com.example.moviebox.data.model.movieReview.Result
import com.example.moviebox.ui.screen.ImageLoading
import com.example.moviebox.ui.screen.ImageLoadingError
import com.example.moviebox.ui.state.MovieReviewState
import com.example.moviebox.ui.theme.*
import com.example.moviebox.ui.viewmodel.MovieReviewViewModel
import com.example.moviebox.util.convertToFormattedTime
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun AddMovieReview() {
    Card(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Column() {
                Text("Add your rating and review", style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp))
                Text("Your ratings matter", style = TextStyle(fontSize = 10.sp))
            }
            Spacer(modifier = Modifier.padding(horizontal = 40.dp))
            OutlinedButton(
                onClick = { },
                border = BorderStroke(1.dp, Color.Red),
                shape = RoundedCornerShape(8.dp),
//                modifier = Modifier.width(90.dp)
            ) {
                Text(text = "Rate Now",
                    style = TextStyle(color = Color.Black, fontSize = 10.sp))
            }
        }
    }
}

@Composable
fun TopReviews(reviewCount: Int) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Top reviews",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "$reviewCount reviews",
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 13.sp)
                )
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .size(13.dp)
                )
            }
        }
    }

}

@Composable
fun ReviewCard(review: Result) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .padding(8.dp)
            .border(
                1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(10.dp)
            )

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
//                .height(200.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)

            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    var imageUrl = review.authorDetails?.avatarPath?.substring(1)

//                    if( imageUrl?.substring(0, 5) !== "https" ){
//                        imageUrl = BASE_AVATAR_URL + imageUrl
//                    }
                    CoilImage(
                        imageModel = { imageUrl },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center
                        ),
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape),
                        previewPlaceholder = R.drawable.nature,
                        loading = {
                            ImageLoading()
                        },
                        failure = {
                            ImageLoadingError(errorImage = R.drawable.baseline_person_24)
                        }
                    )
                    Text(text = review.author.toString(),
                        style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 15.sp),
                        modifier = Modifier.padding(start= 10.dp))
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Rounded.Star,
                        contentDescription = null,
                        tint = PinkSecondary
                    )
                    Text(text = "${review.authorDetails?.rating ?: 1}/10",
                        style = TextStyle(fontSize = 15.sp),
                        modifier = Modifier.padding(start= 3.dp)
                    )
                }
            }

            Text(
                text= review.content.toString(),
                style = TextStyle(fontSize = 12.sp),
                maxLines = 7,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(5.dp)
                    .height(150.dp)
            )

            val dateSt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                review.updatedAt?.convertToFormattedTime()
            } else {
                review.updatedAt.toString()
            }

            Text(
                text = dateSt ?: "",
                style = TextStyle(fontWeight = FontWeight.Light, fontSize = 11.sp),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun ReviewsRow(reviews: List<Result>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(reviews){
            ReviewCard(it)
        }
    }
}


@Composable
fun MovieReview(movieId: Int, movieReviewViewModel: MovieReviewViewModel = hiltViewModel()) {
    val state = movieReviewViewModel.movieReviewState.collectAsState().value
    LaunchedEffect(movieId) {
        movieReviewViewModel.fetchMovieReviews(movieId = movieId)
    }

    when (state) {
        is MovieReviewState.Empty -> {
            Text(
                text = "No data available",
                modifier = Modifier.padding(16.dp)
            )
        }
        is MovieReviewState.Loading ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        is MovieReviewState.Error -> {
            Text(
                text = "error found - ${state.message}",
                modifier = Modifier.padding(16.dp)
            )
        }
        is MovieReviewState.Success -> {

            DataLoaded(state.data)
        }

    }
}

@Composable
fun DataLoaded(data: MovieReviewModel) {
    Column(modifier = Modifier.padding(bottom =16.dp)) {
        AddMovieReview()
        Divider()
        TopReviews(data.totalResults ?: -1)
        data.results?.let { ReviewsRow(it) }
    }
}

@Preview(showBackground = true)
@Composable
fun previewMovieReviewScreen(){
    MovieReview(123)
}

@Preview(showBackground = true)
@Composable
fun previewReviewCardScreen(){
    ReviewCard(Result())
}


