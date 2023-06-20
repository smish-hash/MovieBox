package com.example.moviebox

import android.graphics.drawable.Icon
import android.media.Image
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.moviebox.base.BaseActivity
import com.example.moviebox.ui.theme.MovieBoxTheme
import java.util.Locale

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieBoxTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListingScreen() {
                        MovieCardGrid()
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Surface(modifier = Modifier.padding(16.dp)) {
        Column() {
            Image(
                painterResource(id = drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 250.dp, height = 300.dp)
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Row(modifier = Modifier
                .width(250.dp)
                .padding(bottom = 8.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.LightGray)
                .padding(top = 3.dp, bottom = 3.dp)
            ) {
                Icon(Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.padding(start = 5.dp)
                )
                Text(
                    text = "rating"
                )
            }
            Text(
                stringResource(id = text),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .paddingFromBaseline(top = 16.dp))
        }
    }
}

@Composable
fun MovieCardGrid(
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(vertical = 16.dp),
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxHeight()
    ) {
        items(photosList) { item ->
            MovieCard(
                drawable = item.drawable,
                text = item.text
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column() {
        Text(
            text = "Movies List".uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            modifier = Modifier
                .background(color = Color.Red)
                .paddingFromBaseline(top = 40.dp, bottom = 20.dp)
                .padding(start = 16.dp)
                .fillMaxWidth()
        )
        content()
    }
}

private val photosList = List(8) {
    R.drawable.nature to R.string.app_name
}.map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    MovieBoxTheme {
        MovieCard(
            drawable = R.drawable.nature,
            text = R.string.app_name,
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCardGridPreview() {
    MovieBoxTheme {
        MovieCardGrid()
    }
}

@Preview(showBackground = true)
@Composable
fun ListingScreenPreview() {
    MovieBoxTheme {
        ListingScreen() {
            MovieCardGrid()
        }
    }
}