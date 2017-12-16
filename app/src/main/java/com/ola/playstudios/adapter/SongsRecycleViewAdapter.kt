package com.ola.playstudios.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ola.playstudios.R
import com.ola.playstudios.data.SongData
import com.ola.playstudios.listener.SongsItemClickListener
import kotterknife.bindView

/**
 * Created by pankaj on 16/12/17.
 */
class SongsRecycleViewAdapter(context: Context?, val songsList: List<SongData>?, val songsItemClickListener: SongsItemClickListener) : RecyclerView.Adapter<SongsRecycleViewAdapter.SongsViewHolder>() {
    private val mLayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int {
        return if (songsList != null) songsList?.size!! else 0
    }

    override fun onBindViewHolder(holder: SongsViewHolder?, position: Int) {
        val songData = songsList?.get(position)
        holder?.songTitleTextView?.text = songData?.song
        holder?.artistTextView?.text = songData?.artists
        holder?.thumbImageView?.setImageResource(R.mipmap.ic_launcher)
        Glide.with(holder?.thumbImageView?.context).load(songData?.coverImage).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                holder?.thumbImageView?.setImageResource(R.mipmap.ic_launcher)
                return true
            }

            override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {

                //do something when picture already loaded
                return true
            }
        }).into(holder?.thumbImageView)

        holder?.playImageView?.setOnClickListener({
            songsItemClickListener.onPlayButtonClicked(songData)
        })

        holder?.downloadImageView?.setOnClickListener({
            songsItemClickListener.onDownloadButtonClicked(songData)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SongsViewHolder {
        // Inflate the view for song view holder
        val itemView = mLayoutInflater.inflate(R.layout.row_songs_list, parent, false)
        // Call the view holder's constructor, and pass the view to it;
        // return that new view holder
        return SongsViewHolder(itemView)
    }


    inner class SongsViewHolder(val itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val thumbImageView: AppCompatImageView by bindView(R.id.thumbImageView)
        val songTitleTextView: AppCompatTextView by bindView(R.id.songTitleTextView)
        val artistTextView: AppCompatTextView by bindView(R.id.artistTextView)
        val playImageView: AppCompatImageView by bindView(R.id.playImageView)
        val downloadImageView: AppCompatImageView by bindView(R.id.downloadImageView)
    }

    /* fun addSongs(newSongsList: List<SongData>) {
         this.songsList?.clear()
         this.songsList?.addAll(newSongsList)
         this.notifyDataSetChanged()
     }*/

}