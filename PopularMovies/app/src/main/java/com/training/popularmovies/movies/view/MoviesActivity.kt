package com.training.popularmovies.movies.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.training.popularmovies.PopMoviesApplication
import com.training.popularmovies.R
import com.training.popularmovies.data.Movie
import com.training.popularmovies.data.MoviesSortBy
import com.training.popularmovies.moviedetails.presentation.MovieDetailsActivity
import com.training.popularmovies.movies.MoviesContract
import com.training.popularmovies.movies.di.DaggerMoviesComponent
import com.training.popularmovies.movies.di.MoviesPresenterModule
import com.training.popularmovies.movies.presentation.MoviesGridAdapter
import kotlinx.android.synthetic.main.activity_movies.*
import javax.inject.Inject

class MoviesActivity : MoviesContract.View, AppCompatActivity() {

    var moviesPresenter: MoviesContract.Presenter? = null
    var moviesAdapter: MoviesGridAdapter? = null
    var sortBy: MoviesSortBy = MoviesSortBy.POPULAR

    val movieClickListener: MoviesContract.MovieClickListener = object: MoviesContract.MovieClickListener {
        override fun onClick(movie: Movie) {
            moviesPresenter?.onClick(movie)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        initMoviesList()

        DaggerMoviesComponent.builder()
                .moviesPresenterModule(MoviesPresenterModule(this))
                .moviesRepositoryComponent((application as PopMoviesApplication).repositoryComponent)
                .build()
                .inject(this)
    }

    private fun initMoviesList() {
        moviesAdapter = MoviesGridAdapter(clickListener = movieClickListener)
        val gridLayoutManager = GridLayoutManager(this, 2)

        movies_list_view.layoutManager = gridLayoutManager
        movies_list_view.adapter = moviesAdapter
    }

    @Inject
    override fun setPresenter(presenter: MoviesContract.Presenter) {
        moviesPresenter = presenter

    }

    override fun addMovies(movies: List<Movie>?) {
        moviesAdapter?.movies?.clear()

        movies?.forEach {
            moviesAdapter?.addMovie(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        when (id) {
            R.id.action_sort_by_popularity -> sortBy = MoviesSortBy.POPULAR
            R.id.action_sort_by_highest_rating -> sortBy = MoviesSortBy.TOP_RATED
        }
        moviesPresenter?.loadMovies()

        return super.onOptionsItemSelected(item)
    }

    override fun displayError() {
        Toast.makeText(this, R.string.load_movies_error_message, Toast.LENGTH_LONG)
    }

    override fun onPause() {
        super.onPause()
        moviesPresenter?.unsubscribe()
    }

    override fun onResume() {
        super.onResume()
        moviesPresenter?.subscribe()
    }

    override fun getSortOrder(): MoviesSortBy {
        return sortBy
    }

    override fun navigateToDetails(movie: Movie) {
        val intent = MovieDetailsActivity.createIntent(this, movie)
        startActivity(intent)
    }
}



