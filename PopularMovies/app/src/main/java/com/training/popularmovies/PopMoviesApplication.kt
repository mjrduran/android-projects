package com.training.popularmovies

import android.app.Application
import com.training.popularmovies.data.di.DaggerMoviesRepositoryComponent
import com.training.popularmovies.data.di.MoviesRepositoryComponent

/**
 * Created by moacir on 01/03/17.
 */
class PopMoviesApplication: Application() {

    var repositoryComponent: MoviesRepositoryComponent? = null

    override fun onCreate() {
        super.onCreate()
        repositoryComponent = DaggerMoviesRepositoryComponent.builder().build()
    }

}