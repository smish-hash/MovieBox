package com.example.moviebox.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviebox.data.model.moviedetail.MovieDetailModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetailModel)

    @Query("SELECT * from movieDetails where id = :movieId ")
    fun getMovieDetail(movieId: Int): Flow<MovieDetailModel>
}