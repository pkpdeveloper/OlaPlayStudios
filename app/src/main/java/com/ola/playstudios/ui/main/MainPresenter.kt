package com.ola.playstudios.ui.main

/**
 * Created by pankaj on 16/12/17.
 */
interface MainPresenter {

    fun loadSongs()

    fun switchView()

    fun loadMore(page: Int)

}