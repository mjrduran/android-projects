package com.training.popularmovies.moviedetails.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.training.popularmovies.R
import com.training.popularmovies.data.Movie
import com.training.popularmovies.moviedetails.MovieDetailsContract
import com.training.popularmovies.moviedetails.di.DaggerMovieDetailsComponent
import com.training.popularmovies.moviedetails.di.MovieDetailsPresenterModule
import kotlinx.android.synthetic.main.activity_movie_detail.*


import javax.inject.Inject

/**
 * Created by moacir on 04/03/17.
 */
class MovieDetailsActivity: MovieDetailsContract.View, AppCompatActivity(){

    var movieDetailsPresenter: MovieDetailsContract.Presenter? = null

    object Extras {
        val MOVIE = "MOVIE"
    }

    companion object {
        fun createIntent(context: Context, movie: Movie): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(Extras.MOVIE, movie)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        DaggerMovieDetailsComponent.builder()
                .movieDetailsPresenterModule(MovieDetailsPresenterModule(this))
                .build()
                .inject(this)
    }

    @Inject
    override fun setPresenter(presenter: MovieDetailsContract.Presenter) {
        movieDetailsPresenter = presenter

        val movie: Movie? = intent?.getParcelableExtra(Extras.MOVIE)
        movieDetailsPresenter?.onCreate(movie)
    }

    override fun showMovie(movie: Movie) {
        movie_name?.text = movie?.originalTitle
        val url = "http://image.tmdb.org/t/p/w342" + movie.posterPath
        Picasso.with(this).load(url).into(movie_poster)

        movie_release_year?.text = movie?.releaseDate
        movie_rating?.text = movie?.voteAverage.toString()
        movie_overview?.text = movie?.overview
        movie_duration?.text = "120min"
    }

}