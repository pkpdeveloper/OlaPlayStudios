package com.ola.playstudios.listener

import com.ola.playstudios.data.SongData

/**
 * Created by pankaj on 16/12/17.
 */
interface SongsItemClickListener {

    fun onPlayButtonClicked(songData: SongData?)

    fun onDownloadButtonClicked(songData: SongData?)

}