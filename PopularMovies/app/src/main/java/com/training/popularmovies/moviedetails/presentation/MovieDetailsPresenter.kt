package com.training.popularmovies.moviedetails.presentation

import com.training.popularmovies.data.Movie
import com.training.popularmovies.moviedetails.MovieDetailsContract

/**
 * Created by moacir on 04/03/17.
 */
class MovieDetailsPresenter(val view: MovieDetailsContract.View): MovieDetailsContract.Presenter{

    override fun onCreate(movie: Movie?) {
        movie?.let {
            view.showMovie(movie)
        }
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }

}