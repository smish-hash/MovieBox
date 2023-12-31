package com.example.moviebox.ui.screen.MovieList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.moviebox.R
import com.example.moviebox.data.model.movielist.MovieListModel
import com.example.moviebox.data.model.movielist.Result
import com.example.moviebox.ui.state.MovieListState

@Composable
fun MovieListScreen(movieListState: MovieListState, onMovieClicked: (Result?) -> Unit) {
    Column(verticalArrangement = Arrangement.Center) {
        when (movieListState) {
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
                    text = "error found - ${movieListState.message}",
                    modifier = Modifier.padding(16.dp)
                )
            }
            is MovieListState.Success -> {
                movieListState.data.results?.let {
                    MovieCardGrid(
                        onMovieClicked = {onMovieClicked(it)},
                        movies = it
                    )
                }
            }
        }
    }
}

@Composable
fun MovieCardGrid(
    movies: List<Result>,
    onMovieClicked: (Result?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        items(movies) { movie ->
            MovieCard(
                onMovieClick = {onMovieClicked(it)},
                movie
            )
        }
    }
}