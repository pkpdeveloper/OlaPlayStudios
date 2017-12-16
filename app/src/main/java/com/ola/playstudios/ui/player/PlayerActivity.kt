package com.ola.playstudios.ui.player

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ola.playstudios.R

class PlayerActivity : AppCompatActivity() {
    companion object {
        val KEY_SONG_DATA = "key_song_data"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val playerFragment = PlayerFragment()
        playerFragment.arguments = intent.extras
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, playerFragment).commit()
    }


}
