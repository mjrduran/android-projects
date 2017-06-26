package com.training.popularmovies.data.api

import com.training.popularmovies.data.MovieList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by moacir on 26/02/17.
 */
interface MovieDbApi {

    @GET("movie/popular")
    fun getPopular(): Observable<MovieList>

    @GET("movie/top_rated")
    fun getTopRated(): Observable<MovieList>

}