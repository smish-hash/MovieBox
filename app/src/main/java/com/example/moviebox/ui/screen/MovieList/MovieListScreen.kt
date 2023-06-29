package com.example.moviebox.ui.screen.MovieList

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviebox.data.model.movielist.Result

@Composable
fun MovieListScreen(movieList: List<Result>?, onMovieClicked: (Result?) -> Unit) {
    movieList?.let { movieList ->
        MovieCardGrid(
            movies = movieList,
            onMovieClicked = {
                onMovieClicked(it)
            }
        )
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