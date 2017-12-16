package com.ola.playstudios.db.room

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.ola.playstudios.constants.AppConstants
import com.ola.playstudios.data.SongData


/**
 * Created by pankaj on 16/12/17.
 */
@Database(entities = arrayOf(SongData::class), version = AppConstants.DB_VERSION)
abstract class AppDataBase : RoomDatabase() {

    abstract fun songDao(): SongDao

}