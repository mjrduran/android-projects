package com.training.popularmovies.data

import com.google.gson.annotations.SerializedName

/**
 * Created by moacir on 26/02/17.
 */

data class MovieList(
        @SerializedName("page") val page: Int?,
        @SerializedName("results") val results: List<Movie>?,
        @SerializedName("total_results") val totalResults: Int?,
        @SerializedName("total_pages") val totalPages: Int?)
