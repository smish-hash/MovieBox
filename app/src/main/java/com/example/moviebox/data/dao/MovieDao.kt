package com.example.moviebox.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviebox.data.model.movielist.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Result)

    @Query("DELETE from movielist")
    suspend fun deleteMovies()

    @Query("SELECT * from movielist")
    fun getAllItems(): Flow<List<Result>>
}