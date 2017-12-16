package com.ola.playstudios.ui.player

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.ola.playstudios.R
import com.ola.playstudios.data.SongData
import kotterknife.bindView


/**
 * Created by pankaj on 16/12/17.
 */
class PlayerFragment : Fragment() {
    val mHandler = Handler()
    val userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:40.0) Gecko/20100101 Firefox/40.0"
    val exoPlayerView: SimpleExoPlayerView by bindView(R.id.exoPlayerView)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    private var exoPlayer: SimpleExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataSourceFactory = DefaultHttpDataSourceFactory(
                userAgent, null,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true)

        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector())
        exoPlayerView.player = exoPlayer
        exoPlayer?.setPlayWhenReady(true)


        val songData = arguments?.getParcelable(PlayerActivity.KEY_SONG_DATA) as SongData?
        if (songData != null) {
            activity?.setTitle(songData.song)
            val uri = Uri.parse(songData.url)
            val mediaSource = ExtractorMediaSource(uri, dataSourceFactory, Mp3Extractor.FACTORY,
                    mHandler, null)
            exoPlayer?.prepare(mediaSource)
            exoPlayerView.background
        }
    }

    override fun onStop() {
        super.onStop()
        exoPlayer?.stop()
    }
}