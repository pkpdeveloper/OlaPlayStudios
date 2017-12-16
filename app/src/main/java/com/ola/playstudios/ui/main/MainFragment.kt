package com.ola.playstudios.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.koushikdutta.ion.Ion
import com.ola.playstudios.R
import com.ola.playstudios.adapter.SongsRecycleViewAdapter
import com.ola.playstudios.data.SongData
import com.ola.playstudios.listener.SongsItemClickListener
import com.ola.playstudios.ui.player.PlayerActivity
import kotterknife.bindView
import java.io.File
import android.widget.Toast
import android.content.ActivityNotFoundException
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.webkit.MimeTypeMap


/**
 * Created by pankaj on 16/12/17.
 */
class MainFragment : Fragment(), MainView, SongsItemClickListener {

    val songsRecycleView: RecyclerView by bindView(R.id.songsRecycleView)
    val progressBar: ProgressBar by bindView(R.id.progressBar)
    val downloadProgressBar: ProgressBar by bindView(R.id.downloadProgressBar)

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
        val targetFile = getLocalFile(songData?.song)
        downloadProgressBar.isIndeterminate = true
        downloadProgressBar.visibility = View.VISIBLE
        Ion.with(this)
                .load(songData?.url)
                .progress { downloaded, total ->
                    val progress = downloaded / total * 100.0f
                    downloadProgressBar.setProgress(progress.toInt())
                }
                .write(targetFile)
                .setCallback({ e, downloadedFile ->

                    if (downloadedFile.exists()) {
                        downloadProgressBar.visibility = View.INVISIBLE
                        showAlert(downloadedFile)
                    }

                })
    }

    private fun showAlert(downloadedFile: File?) {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setMessage("Song downloaded successfully!")
        alertBuilder.setPositiveButton("Open", { dialogInterface, k ->
            dialogInterface.dismiss()
            // openFileIntent(downloadedFile)
            Toast.makeText(context, "File saved at "+downloadedFile?.path, Toast.LENGTH_LONG).show()
        })
        alertBuilder.setNegativeButton("Cancel", null)
        alertBuilder.create().show()
    }

    private fun openFileIntent(downloadedFile: File?) {
        val myMime = MimeTypeMap.getSingleton()
        val newIntent = Intent(Intent.ACTION_VIEW)
        val mimeType = myMime.getMimeTypeFromExtension(fileExt(downloadedFile?.absolutePath!!)?.substring(1))
        newIntent.setDataAndType(Uri.fromFile(downloadedFile), mimeType)
        newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            context?.startActivity(newIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No handler for this type of file.", Toast.LENGTH_LONG).show()
        }

    }

    private fun getLocalFile(songName: String?): File? {
        val file = File(Environment.getExternalStorageDirectory(), songName + ".mp3")
        if (!file.exists()) {
            file.createNewFile()
        }
        return file
    }

    override fun onError(message: String) {

    }

    private fun fileExt(url: String): String? {
        var url = url
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"))
        }
        if (url.lastIndexOf(".") == -1) {
            return null
        } else {
            var ext = url.substring(url.lastIndexOf(".") + 1)
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"))
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"))
            }
            return ext.toLowerCase()

        }
    }
}