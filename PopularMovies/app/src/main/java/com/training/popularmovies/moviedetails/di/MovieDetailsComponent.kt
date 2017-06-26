package com.training.popularmovies.moviedetails.di

import com.training.popularmovies.di.ActivityScope
import com.training.popularmovies.moviedetails.presentation.MovieDetailsActivity
import dagger.Component
import dagger.Module

/**
 * Created by moacir on 04/03/17.
 */
@ActivityScope
@Component(modules = arrayOf(MovieDetailsPresenterModule::class))
interface MovieDetailsComponent {

    fun inject(movieDetailsActivity: MovieDetailsActivity)

}