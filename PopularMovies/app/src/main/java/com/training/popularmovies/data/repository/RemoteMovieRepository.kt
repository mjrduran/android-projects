package com.training.popularmovies.data.repository

import com.training.popularmovies.data.MovieList
import com.training.popularmovies.data.MoviesSortBy
import com.training.popularmovies.data.api.MovieDbApi
import io.reactivex.Observable

/**
 * Created by moacir on 27/02/17.
 */
class RemoteMovieRepository(val movieDbApi: MovieDbApi) : MovieRepository {

    override fun getMovies(sortBy: MoviesSortBy): Observable<MovieList> {
        when(sortBy){
            MoviesSortBy.POPULAR -> return movieDbApi.getPopular()
            MoviesSortBy.TOP_RATED -> return movieDbApi.getTopRated()
        }
    }

}