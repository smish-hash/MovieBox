package com.example.moviebox.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.moviebox.data.dao.MovieDao
import com.example.moviebox.data.db.MovieListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: MovieListDatabase.Callback): MovieListDatabase{
        return Room.databaseBuilder(application, MovieListDatabase::class.java, "movie_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideMovieDao(db: MovieListDatabase): MovieDao {
        return db.getMovieDao()
    }
}