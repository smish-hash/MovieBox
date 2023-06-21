package com.example.moviebox.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviebox.R
import com.example.moviebox.ui.screen.MovieList.MovieListScreen
import com.example.moviebox.ui.screen.MovieSynopsis.MovieSynopsisScreen
import com.example.moviebox.ui.viewmodel.MovieListViewModel


sealed class Screen(val title: String, val route: String) {
    object Home: Screen("Movie Box", "home")
    object Synopsis: Screen("Synopsis", "detail/{id}")
}

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieBoxAppBar(
    currentScreen: MovieScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.navigate_up)
                    )
                }
            }
        }
    )
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieBoxApp(
    movieListViewModel: MovieListViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    /*val currentScreen = MovieScreen.valueOf(
        backStackEntry?.destination?.route ?: MovieScreen.MovieList.name
    )*/

    Scaffold(
        /*topBar = {
            MovieBoxAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }*/
    ) { innerPadding ->
        val movieListState by movieListViewModel.popularMoviesState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Home.route) {
                MovieListScreen(
                    movieListState = movieListState,
                    onFetchMovieClicked = { movieListViewModel.fetchPopularMovies() },
                    onMovieClicked = {movieId ->
                        movieId?.let {
                            navController.navigate("detail/$it")
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            composable(
                route = Screen.Synopsis.route,
                arguments = listOf(navArgument("id"){
                    type = NavType.IntType
                })
            ) {
                val movieId = it.arguments?.getInt("id") ?: -1
                MovieSynopsisScreen(
                    movieId,
                    onBookTicketClicked = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)))
            }
        }
    }
}
