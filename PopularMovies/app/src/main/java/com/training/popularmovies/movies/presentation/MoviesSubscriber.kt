package com.training.popularmovies.movies.presentation

import android.util.Log
import com.training.popularmovies.data.MovieList
import com.training.popularmovies.movies.MoviesContract
import io.reactivex.observers.DisposableObserver

/**
 * Created by moacir on 27/02/17.
 */
 class MoviesSubscriber(val view: MoviesContract.View) : DisposableObserver<MovieList>() {

    val TAG: String = MoviesSubscriber::class.java.simpleName

    override fun onError(t: Throwable?) {
        Log.e(TAG, "error", t)
        view.displayError()
    }

    override fun onNext(movieList: MovieList?) {
        view.addMovies(movieList?.results)
    }

    override fun onComplete() {

    }

}