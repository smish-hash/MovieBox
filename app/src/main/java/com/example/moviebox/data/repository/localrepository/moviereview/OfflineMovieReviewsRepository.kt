package com.example.moviebox.data.repository.localrepository.moviereview

import com.example.moviebox.data.dao.MovieReviewDao
import com.example.moviebox.data.model.movieReview.MovieReviewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineMovieReviewsRepository @Inject constructor(
    private val movieReviewDao: MovieReviewDao
) : OfflineMovieReviews {
    override fun getMovieReviews(id:Int): Flow<MovieReviewModel> {
        return movieReviewDao.getMovieReviews(id)
    }

    override suspend fun insertMovieReviews(movieReviews: MovieReviewModel) {
        movieReviewDao.insertMovieReviews(moviereviews = movieReviews)
    }
}
