package com.training.popularmovies.moviedetails

import com.training.popularmovies.BasePresenter
import com.training.popularmovies.BaseView
import com.training.popularmovies.data.Movie

/**
 * Created by moacir on 04/03/17.
 */
interface MovieDetailsContract {

    interface View : BaseView<MovieDetailsContract.Presenter> {
        fun showMovie(movie: Movie)
    }

    interface Presenter : BasePresenter {
        fun onCreate(movie: Movie?)
    }
}