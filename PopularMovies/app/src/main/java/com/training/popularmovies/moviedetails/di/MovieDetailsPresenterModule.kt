package com.training.popularmovies.moviedetails.di

import com.training.popularmovies.moviedetails.MovieDetailsContract
import com.training.popularmovies.moviedetails.presentation.MovieDetailsPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by moacir on 04/03/17.
 */
@Module
class MovieDetailsPresenterModule (val view: MovieDetailsContract.View){

    @Provides
    fun providesMovieDetailsView() = view

    @Provides
    fun providesMovieDetailsPresenter(): MovieDetailsContract.Presenter =
            MovieDetailsPresenter(view)

}