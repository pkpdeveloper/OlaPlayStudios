package com.ola.playstudios.ui.history

/**
 * Created by pankaj on 16/12/17.
 */
interface HistoryPresenter {

    fun loadSongs()

    fun switchView()

    fun loadMore(page: Int)

}