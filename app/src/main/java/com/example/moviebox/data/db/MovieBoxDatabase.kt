package com.example.moviebox.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviebox.data.converter.Converters
import com.example.moviebox.data.dao.MovieDao
import com.example.moviebox.data.dao.MovieDetailsDao
import com.example.moviebox.data.dao.MovieReviewDao
import com.example.moviebox.data.model.movieReview.MovieReviewModel
import com.example.moviebox.data.model.moviedetail.MovieDetailModel
import com.example.moviebox.data.model.movielist.Result
import com.example.moviebox.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(
    version = 1,
    entities = [Result::class, MovieDetailModel::class, MovieReviewModel::class],
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieBoxDatabase: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

    abstract fun getMovieDetailDao(): MovieDetailsDao
    abstract fun getMovieReviewDao(): MovieReviewDao
    class Callback @Inject constructor(
        private val database: Provider<MovieBoxDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()


}