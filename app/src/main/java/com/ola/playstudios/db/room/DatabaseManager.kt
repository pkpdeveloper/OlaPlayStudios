package com.ola.playstudios.db.room

import android.arch.persistence.room.Room
import android.content.Context
import com.ola.playstudios.constants.AppConstants


/**
 * Created by pankaj on 16/12/17.
 */
class DatabaseManager {
    companion object {

        fun getDataBase(context: Context?): AppDataBase? {
            return context?.let {
                Room.databaseBuilder(it.applicationContext,
                        AppDataBase::class.java, AppConstants.DB_NAME).allowMainThreadQueries().build()
            }
        }
    }
}