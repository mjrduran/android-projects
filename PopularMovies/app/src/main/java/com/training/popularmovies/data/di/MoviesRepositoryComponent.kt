package com.training.popularmovies.data.di

import com.training.popularmovies.data.repository.MovieRepository
import dagger.Component
import javax.inject.Singleton

/**
 * Created by moacir on 01/03/17.
 */
@Singleton
@Component(modules = arrayOf(MoviesRepositoryModule::class))
interface MoviesRepositoryComponent{

    fun moviesRepository(): MovieRepository
}