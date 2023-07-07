package com.example.moviebox.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviebox.data.model.movieReview.MovieReviewModel
import com.example.moviebox.data.model.movieReview.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieReviews(moviereviews: MovieReviewModel)

    @Query("SELECT * from MovieReview where id = :movieId")
    fun getMovieReviews(movieId: Int): Flow<MovieReviewModel>
}