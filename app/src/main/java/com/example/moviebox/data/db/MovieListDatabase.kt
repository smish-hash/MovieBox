package com.example.moviebox.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviebox.data.dao.MovieDao
import com.example.moviebox.data.model.movielist.Result

@Database(
    version = 1,
    entities = [Result::class],
    exportSchema = false
)
abstract class MovieListDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var Instance: MovieListDatabase? = null

        fun getDatabase(context: Context): MovieListDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MovieListDatabase::class.java, "movie_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}