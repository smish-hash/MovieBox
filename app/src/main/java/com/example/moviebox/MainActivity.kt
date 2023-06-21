package com.example.moviebox

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviebox.base.BaseActivity
import com.example.moviebox.ui.state.MovieListState
import com.example.moviebox.ui.theme.MovieBoxTheme
import com.example.moviebox.ui.viewmodel.CastAndCrewViewModel
import com.example.moviebox.ui.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, listViewModel: MovieListViewModel = viewModel()) {

    Column(verticalArrangement = Arrangement.Center) {
        Text(
            text = "Hello $name!",
            modifier = modifier.padding()
        )
        Button(onClick = {
            listViewModel.fetchPopularMovies()
        }, modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Text(text = "Tap Me")
        }

        when (val state = listViewModel.popularMoviesState.collectAsState().value) {
            is MovieListState.Empty -> {
                Text(
                    text = "No data available",
                    modifier = Modifier.padding(16.dp)
                )
            }
            is MovieListState.Loading ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            is MovieListState.Error -> {
                Text(
                    text = "error found - ${state.message}",
                    modifier = Modifier.padding(16.dp)
                )
            }
            is MovieListState.Success -> {
                Text(
                    text = "Popular movies fetched - ${state.data.totalResults} movies found",
                    modifier = Modifier.padding(16.dp)
                )
            }

        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieBoxTheme {
        Greeting("Android")
    }
}