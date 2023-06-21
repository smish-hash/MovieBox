package com.example.moviebox.di

import com.example.moviebox.data.api.MovieDetailApi
import com.example.moviebox.data.api.MovieListApi
import com.example.moviebox.data.api.MovieReviewApi
import com.example.moviebox.data.repository.MovieDetailRepository
import com.example.moviebox.data.repository.MovieListRepository
import com.example.moviebox.data.repository.MovieReviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieListRepository(
        movieListApi: MovieListApi
    ): MovieListRepository {
        return MovieListRepository(movieListApi)
    }

    @Singleton
    @Provides
    fun provideMovieDetailRepository(
        movieDetailApi: MovieDetailApi
    ): MovieDetailRepository {
        return MovieDetailRepository(movieDetailApi)
    }

    @Singleton
    @Provides
    fun provideMovieReviewRepository(
        movieReviewApi: MovieReviewApi
    ): MovieReviewRepository {
        return MovieReviewRepository(movieReviewApi)
    }
}