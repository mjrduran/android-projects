package com.training.popularmovies.data.repository

import com.training.popularmovies.data.MovieList
import com.training.popularmovies.data.MoviesSortBy
import io.reactivex.Observable

/**
 * Created by moacir on 27/02/17.
 */
interface MovieRepository {

    fun getMovies(sortBy: MoviesSortBy): Observable<MovieList>

}
