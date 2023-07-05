package com.example.moviebox.ui.screen.MovieList

import android.os.Bundle
import android.view.View
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.moviebox.base.BaseFragmentBinding
import com.example.moviebox.data.model.movielist.MovieListModel
import com.example.moviebox.data.model.movielist.Result
import com.example.moviebox.databinding.FragmentMovieListBinding
import com.example.moviebox.ui.screen.ErrorOrEmpty
import com.example.moviebox.ui.screen.StateView
import com.example.moviebox.ui.state.ScreenState
import com.example.moviebox.ui.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment :
    BaseFragmentBinding<FragmentMovieListBinding>(FragmentMovieListBinding::inflate) {

    private val movieListViewModel: MovieListViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            movieListViewModel.popularMoviesState.collect { uiState ->
                setContent(uiState)
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    private fun setContent(uiState: ScreenState) {
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    val refreshing by movieListViewModel.isRefreshing.collectAsStateWithLifecycle()
                    val pullRefreshState = rememberPullRefreshState(
                        refreshing = refreshing,
                        onRefresh = { movieListViewModel.fetchPopularMovies() }
                    )
                    StateView(
                        uiState = uiState,
                        errorComposable = {
                            ErrorOrEmpty(
                                errorMessage = it,
                                onRefreshButtonClick = {movieListViewModel.fetchPopularMovies()}
                            )
                        }
                    ) {
                        val res = it as List<Result>
                        MovieCardGrid(
                            movieList = res,
                            isRefreshing = refreshing,
                            refreshState = pullRefreshState,
                            onMovieClicked = { movie ->
                                movie?.let {
                                    val action =
                                        MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                                            it.id ?: -1
                                        )
                                    findNavController().navigate(action)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}