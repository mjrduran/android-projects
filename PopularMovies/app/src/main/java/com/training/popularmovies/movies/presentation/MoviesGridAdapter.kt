package com.training.popularmovies.movies.presentation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.squareup.picasso.Picasso
import com.training.popularmovies.R
import com.training.popularmovies.data.Movie
import com.training.popularmovies.movies.MoviesContract

/**
 * Created by moacir on 26/02/17.
 */
class MoviesGridAdapter(val movies: MutableList<Movie> = mutableListOf(), val clickListener: MoviesContract.MovieClickListener) :
        RecyclerView.Adapter<MoviesGridAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position])

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_movie, parent, false)
        val viewHolder = ViewHolder(view, clickListener)
        return viewHolder
    }

    class ViewHolder(val view: View?, val clickListener: MoviesContract.MovieClickListener) : RecyclerView.ViewHolder(view) {

        val moviePoster by lazy { itemView?.findViewById(R.id.movie_poster) as ImageView }

        fun bind(movie: Movie) {
            // TODO Make this dynamic
            val url = "http://image.tmdb.org/t/p/w342" + movie.posterPath
            Picasso.with(view?.context).load(url).into(moviePoster)

            moviePoster.setOnClickListener {
                clickListener.onClick(movie)
            }
        }
    }

    fun addMovie(movie: Movie){
        movies.add(movie)
        notifyDataSetChanged()
    }
}