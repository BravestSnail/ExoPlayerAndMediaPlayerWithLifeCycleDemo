package com.snail.exoplayerandmediaplayerwithlifecycledemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startExoPlayerActivity(view: View){
        startActivity(Intent(this, ExoPlayerActivity::class.java))
    }

    fun startPlayerActivity(view: View){
        startActivity(Intent(this, PlayerActivity::class.java))
    }
}