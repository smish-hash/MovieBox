package com.example.moviebox.ui.screen.MovieSynopsis

import android.os.Bundle
import android.view.View
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.moviebox.base.BaseFragmentBinding
import com.example.moviebox.data.model.MovieSynopsisModel
import com.example.moviebox.databinding.FragmentMovieDetailBinding
import com.example.moviebox.ui.screen.ErrorOrEmpty
import com.example.moviebox.ui.screen.StateView
import com.example.moviebox.ui.state.ScreenState
import com.example.moviebox.ui.viewmodel.MovieSynopsisViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : BaseFragmentBinding<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

    private var movieId: Int? = null
    private val movieSynopsisViewModel: MovieSynopsisViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // we get the movieID here
        arguments?.let {
            movieId = it.getInt("movieId")
        }

        movieId?.let {
            movieSynopsisViewModel.setMovieId(it)
            debugLog("movie id set to $it")
        }

        movieSynopsisViewModel.movieId.observe(viewLifecycleOwner) {
            movieSynopsisViewModel.fetchMovieSynopsis()
        }

        lifecycleScope.launch {
            movieSynopsisViewModel.movieDetailState.collect{uiState ->
                setContent(uiState)
            }
        }

    }

    private fun setContent(uiState: ScreenState) {
        binding.detailCompose.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    StateView(
                        uiState = uiState,
                        errorComposable = {
                            ErrorOrEmpty(
                                errorMessage = it,
                                onRefreshButtonClick = {
                                    movieId?.let { id -> movieSynopsisViewModel.setMovieId(id) }
                                }
                            )
                        }
                    ) {
                        val result = it as MovieSynopsisModel
                        MovieSynopsisScreen(
                            movieSynopsis = result,
                            onBookTicketClicked = {
                                showSmallToast("Your tickets have been booked :)")
                            }
                        )
                    }
                }
            }
        }
    }
}