package com.ola.playstudios.ui.main

import com.ola.playstudios.data.SongData

/**
 * Created by pankaj on 16/12/17.
 */
interface MainView {

    fun initView()

    fun showProgress()

    fun hideProgress()

    fun displaySongList(songsList: List<SongData>)

    fun onError(message: String)

}