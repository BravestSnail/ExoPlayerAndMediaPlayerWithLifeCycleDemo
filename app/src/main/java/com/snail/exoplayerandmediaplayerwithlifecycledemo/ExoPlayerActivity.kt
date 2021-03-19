package com.snail.exoplayerandmediaplayerwithlifecycledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.ui.PlayerView

class ExoPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)
        val playerView = findViewById<PlayerView>(R.id.playerView)
        val myPlayer = MyPlayer(this, playerView, R.raw.snowboarding.toString())
        lifecycle.addObserver(myPlayer)
    }
}