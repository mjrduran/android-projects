package com.training.popularmovies.movies.di

import com.training.popularmovies.data.di.MoviesRepositoryComponent
import com.training.popularmovies.di.ActivityScope
import com.training.popularmovies.movies.view.MoviesActivity
import dagger.Component

/**
 * Created by moacir on 28/02/17.
 */
@ActivityScope
@Component(dependencies = arrayOf(MoviesRepositoryComponent::class), modules = arrayOf(MoviesPresenterModule::class))
interface MoviesComponent {

    fun inject(moviesActivity: MoviesActivity)

}