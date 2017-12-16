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
import android.content.Context
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatImageView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap


/**
 * Created by pankaj on 16/12/17.
 */
class MainFragment : Fragment(), MainView, SongsItemClickListener, TextWatcher {

    private var backupSongsList: List<SongData>? = null

    val songsRecycleView: RecyclerView by bindView(R.id.songsRecycleView)
    val progressBar: ProgressBar by bindView(R.id.progressBar)
    val downloadProgressBar: ProgressBar by bindView(R.id.downloadProgressBar)
    val searchEditText: AppCompatEditText by bindView(R.id.searchEditText)
    val inputClearImageView: AppCompatImageView by bindView(R.id.inputClearImageView)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        songsRecycleView.layoutManager = layoutManager
        songsRecycleView.setHasFixedSize(true)

        searchEditText.addTextChangedListener(this)
        inputClearImageView.setOnClickListener({
            searchEditText.editableText.clear()
            hideKeyBoard(searchEditText)
        })
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
        this.backupSongsList = songsList
        songsRecycleView.adapter = getAdapter(songsList)
    }


    private fun getAdapter(songsList: List<SongData>?): SongsRecycleViewAdapter {

        //  val layoutManager = GridLayoutManager(context,2)
        return SongsRecycleViewAdapter(context, songsList, this)
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
            Toast.makeText(context, "File saved at " + downloadedFile?.path, Toast.LENGTH_LONG).show()
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

    override fun afterTextChanged(editable: Editable?) {
        if (searchEditText.text.length == 0) {
            inputClearImageView.visibility = View.INVISIBLE
        } else {
            inputClearImageView.visibility = View.VISIBLE

        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (TextUtils.isEmpty(input)) {
            songsRecycleView.adapter = getAdapter(backupSongsList)
        } else {
            songsRecycleView.adapter = getAdapter(filterSongList(input, backupSongsList))
        }
    }

    private fun filterSongList(input: CharSequence?, backupSongsList: List<SongData>?): List<SongData> {
        val songs = arrayListOf<SongData>()
        if (backupSongsList != null && input != null) {
            for (songData: SongData in backupSongsList) {
                if (songData.song.toLowerCase().contains(input.toString().toLowerCase())) {
                    songs.add(songData)
                }
            }
        }
        return songs
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

    private fun hideKeyBoard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}