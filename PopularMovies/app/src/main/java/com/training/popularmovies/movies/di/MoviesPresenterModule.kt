package com.training.popularmovies.movies.di

import com.training.popularmovies.data.repository.MovieRepository
import com.training.popularmovies.movies.MoviesContract
import com.training.popularmovies.movies.presentation.MoviesPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by moacir on 28/02/17.
 */
@Module
class MoviesPresenterModule(val view: MoviesContract.View){

    @Provides
    fun providesMoviesContractView() = view

    @Provides
    fun providesMoviesPresenter(repository: MovieRepository): MoviesContract.Presenter =
            MoviesPresenter(view, repository)

}