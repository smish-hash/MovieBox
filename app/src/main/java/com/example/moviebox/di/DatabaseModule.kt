package com.example.moviebox.di

import android.app.Application
import androidx.room.Room
import com.example.moviebox.data.dao.MovieDao
import com.example.moviebox.data.dao.MovieDetailsDao
import com.example.moviebox.data.dao.MovieReviewDao
import com.example.moviebox.data.db.MovieBoxDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: MovieBoxDatabase.Callback): MovieBoxDatabase{
        return Room.databaseBuilder(application, MovieBoxDatabase::class.java, "movie_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideMovieDao(db: MovieBoxDatabase): MovieDao {
        return db.getMovieDao()
    }

    @Provides
    fun provideMovieDetailsDao(db: MovieBoxDatabase): MovieDetailsDao {
        return db.getMovieDetailDao()
    }

    @Provides
    fun provideMovieReviewsDao(db: MovieBoxDatabase): MovieReviewDao {
        return db.getMovieReviewDao()
    }
}