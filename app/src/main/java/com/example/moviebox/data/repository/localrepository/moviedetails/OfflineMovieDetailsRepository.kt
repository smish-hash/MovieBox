package com.example.moviebox.data.repository.localrepository.moviedetails

import com.example.moviebox.data.dao.MovieDetailsDao
import com.example.moviebox.data.model.moviedetail.MovieDetailModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineMovieDetailsRepository @Inject constructor(
    private val movieDetailsDao: MovieDetailsDao
): OfflineMovieDetails {
    override fun getMovieDetails(id:Int): Flow<MovieDetailModel> {
        return movieDetailsDao.getMovieDetail(id)
    }

    override suspend fun insertMovieDetail(movieDetails: MovieDetailModel){
        movieDetailsDao.insertMovieDetail(movieDetail = movieDetails)
    }
}
