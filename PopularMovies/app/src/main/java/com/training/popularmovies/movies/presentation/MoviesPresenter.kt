package com.training.popularmovies.movies.presentation

import com.training.popularmovies.data.Movie
import com.training.popularmovies.data.MoviesSortBy
import com.training.popularmovies.data.repository.MovieRepository
import com.training.popularmovies.movies.MoviesContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by moacir on 27/02/17.
 */
class MoviesPresenter (val view: MoviesContract.View, val repository: MovieRepository) : MoviesContract.Presenter {

    val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun loadMovies() {

        val subscriber = MoviesSubscriber(view)

        compositeDisposable.add(repository.getMovies(view.getSortOrder())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber))
    }

    override fun subscribe() {
        loadMovies()
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    override fun onClick(movie: Movie) {
        view?.navigateToDetails(movie)
    }
}