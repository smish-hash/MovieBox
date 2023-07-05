package com.example.moviebox.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.moviebox.ui.theme.*
import com.example.moviebox.ui.viewmodel.MovieListViewModel
import kotlinx.coroutines.launch


sealed class Screen(var title: String, val route: String, val color: Color, val textColor: Color) {
    object Home: Screen("It All Starts Here", "home", color = DarkBlue, textColor = Color.White)
    object Synopsis: Screen("Synopsis", "detail/{id}", color = Color.White, textColor = Color.Black)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieBoxAppBar(
    currentScreen: State<Screen>,
    canNavigateBack: Boolean,
    showSubtitle: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = currentScreen.value.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = currentScreen.value.textColor
                )
                if (showSubtitle)
                    Text(
                        text = "Bengaluru >",
                        style = MaterialTheme.typography.labelMedium,
                        color = currentScreen.value.textColor,
                        fontFamily = FontFamily.SansSerif
                    )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = currentScreen.value.color,
            titleContentColor = MaterialTheme.colorScheme.onSurface
//            MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.navigate_up),
//                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun MovieBoxApp(
    movieListViewModel: MovieListViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route?.let { route ->
        when (route) {
            Screen.Home.route -> Screen.Home
            Screen.Synopsis.route -> Screen.Synopsis
            else -> Screen.Home
        }
    } ?: Screen.Home

    val currentScreenState = remember { mutableStateOf(currentScreen) }
    currentScreenState.value = currentScreen
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MovieBoxAppBar(
                currentScreen = currentScreenState,
                canNavigateBack = navController.previousBackStackEntry != null,
                showSubtitle = currentScreenState.value == Screen.Home,
                navigateUp = { navController.navigateUp() }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        val movieListState by movieListViewModel.popularMoviesState.collectAsState()


        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = Screen.Home.route
            ) {
                MovieListScreen(
                    movieListState = movieListState,
                    onMovieClicked = {movieId ->
                        movieId?.let {
                            navController.navigate("detail/${it.id}")
                            Screen.Synopsis.title = it.title.toString()
                        }
                    }
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
                    movieId = movieId,
                    onBookTicketClicked = {
                        coroutineScope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Your tickets have been booked :)",
                                actionLabel = "Awesome!",
                                duration = SnackbarDuration.Short
                            )

                            when (result) {
                                SnackbarResult.Dismissed -> {

                                }

                                SnackbarResult.ActionPerformed -> {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}