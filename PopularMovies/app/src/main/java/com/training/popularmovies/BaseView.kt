package com.training.popularmovies

/**
 * Created by moacir on 27/02/17.
 */
interface BaseView<in T>{

    fun setPresenter(presenter: T)
}