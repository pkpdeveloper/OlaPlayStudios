package com.ola.playstudios.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.ola.playstudios.R
import com.ola.playstudios.adapter.SongsRecycleViewAdapter
import com.ola.playstudios.data.SongData
import com.ola.playstudios.listener.SongsItemClickListener
import com.ola.playstudios.ui.player.PlayerActivity
import kotterknife.bindView
import java.io.Serializable

/**
 * Created by pankaj on 16/12/17.
 */
class MainFragment : Fragment(), MainView, SongsItemClickListener {

    val songsRecycleView: RecyclerView by bindView(R.id.songsRecycleView)
    val progressBar: ProgressBar by bindView(R.id.progressBar)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainImpl = MainImpl(this)
        mainImpl.loadSongs()
    }

    override fun initView() {


    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE

    }

    override fun displaySongList(songsList: List<SongData>) {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        //  val layoutManager = GridLayoutManager(context,2)
        val songsRecycleViewAdapter = SongsRecycleViewAdapter(context, songsList, this)
        songsRecycleView.setHasFixedSize(true)
        songsRecycleView.layoutManager = layoutManager
        songsRecycleView.adapter = songsRecycleViewAdapter

    }

    override fun onPlayButtonClicked(songData: SongData?) {
        val intent = Intent(activity, PlayerActivity::class.java)
        intent.putExtra(PlayerActivity.KEY_SONG_DATA, songData)
        startActivity(intent)
    }

    override fun onDownloadButtonClicked(songData: SongData?) {
    }

    override fun onError(message: String) {

    }
}