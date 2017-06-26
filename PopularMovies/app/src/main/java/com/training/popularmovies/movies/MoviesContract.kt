package com.training.popularmovies.movies

import com.training.popularmovies.BasePresenter
import com.training.popularmovies.BaseView
import com.training.popularmovies.data.Movie
import com.training.popularmovies.data.MoviesSortBy

/**
 * Created by moacir on 27/02/17.
 */
interface MoviesContract {

    interface View : BaseView<Presenter> {

        fun addMovies(movies: List<Movie>?)

        fun displayError()

        fun getSortOrder(): MoviesSortBy

        fun navigateToDetails(movie: Movie)
    }

    interface Presenter : BasePresenter {

        fun loadMovies()

        fun onClick(movie: Movie)

    }

    interface MovieClickListener {

        fun onClick(movie: Movie)

    }
}