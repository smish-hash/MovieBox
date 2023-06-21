package com.example.moviebox.di

import com.example.moviebox.data.api.CastAndCrewApi
import com.example.moviebox.data.api.MovieDetailApi
import com.example.moviebox.data.api.MovieListApi
import com.example.moviebox.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieListService(retrofit: Retrofit): MovieListApi {
        return retrofit.create(MovieListApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieCastCrewService(retrofit: Retrofit): CastAndCrewApi {
        return retrofit.create(CastAndCrewApi::class.java)
    }
    fun provideMovieDetailService(retrofit: Retrofit): MovieDetailApi {
        return retrofit.create(MovieDetailApi::class.java)
    }
}