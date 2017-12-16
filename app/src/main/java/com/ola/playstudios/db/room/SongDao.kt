package com.ola.playstudios.db.room

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.ola.playstudios.data.SongData
import io.reactivex.Maybe


/**
 * Created by pankaj on 16/12/17.
 */
@Dao
interface SongDao {

    @Query("SELECT * FROM song_data ORDER BY timestamp DESC")
    fun all(): Maybe<List<SongData>>

    @Insert(onConflict = REPLACE)
    fun insert(songData: SongData): Long

    @Delete
    fun delete(songData: SongData): Int
}