package com.ola.playstudios.ui.history

import android.content.Context
import com.ola.playstudios.db.room.DatabaseManager

/**
 * Created by pankaj on 16/12/17.
 */
class HistoryImpl(val context: Context?, val historyView: HistoryView) : HistoryPresenter {

    override fun loadSongs() {
        // show progress
        historyView.showProgress()
        DatabaseManager.getDataBase(context)?.songDao()?.all()?.subscribe({ songsList ->
            //hide progress
            historyView.hideProgress()

            // callback to display songs
            historyView.displaySongList(songsList)
            println("songsList size=" + songsList.size)
        })
    }

    override fun switchView() {
    }

    override fun loadMore(page: Int) {
    }

}