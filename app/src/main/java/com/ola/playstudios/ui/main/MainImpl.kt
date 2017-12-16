package com.ola.playstudios.ui.main

import com.ola.playstudios.api.ApiManager

/**
 * Created by pankaj on 16/12/17.
 */
class MainImpl(val mainView: MainView) : MainPresenter {

    override fun loadSongs() {
        // show progress
        mainView.showProgress()
        ApiManager.getSongsList().subscribe({ songsList ->
            //hide progress
            mainView.hideProgress()

            // callback to display songs
            mainView.displaySongList(songsList)
            println("songsList size=" + songsList.size)
        })
    }

    override fun switchView() {
    }

    override fun loadMore(page: Int) {
    }

}