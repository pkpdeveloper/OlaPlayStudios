package com.ola.playstudios

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ola.playstudios.api.ApiManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ApiManager.getSongsList().subscribe({ songsList ->
            println("songsList size=" + songsList.size)
        })

    }
}
