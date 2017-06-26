package com.training.popularmovies.data.di

import com.training.popularmovies.data.api.MovieDbApi
import com.training.popularmovies.data.api.MovieDbApiBuilder
import com.training.popularmovies.data.repository.MovieRepository
import com.training.popularmovies.data.repository.RemoteMovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by moacir on 28/02/17.
 */
@Module
class MoviesRepositoryModule{

    @Singleton
    @Provides
    fun providesMoviesRepository(movieDbApi: MovieDbApi): MovieRepository =
            RemoteMovieRepository(movieDbApi)

    @Singleton
    @Provides
    fun providesMovieDbApi(): MovieDbApi = MovieDbApiBuilder().build()
}