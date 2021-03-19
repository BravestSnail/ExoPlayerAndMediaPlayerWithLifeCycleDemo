package com.snail.exoplayerandmediaplayerwithlifecycledemo

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.common.reflect.Reflection.getPackageName
/*将ExoPlayer与Activity的生命周期绑定*/
class MyPlayer(val context: Context, playerView: PlayerView, videoSrc: String) : LifecycleObserver {
    private lateinit var player: SimpleExoPlayer
    init {
        player = SimpleExoPlayer.Builder(context).build()
        playerView.player = player
        player.run {
            setMediaItem(MediaItem.fromUri(Uri.parse("android.resource://${
            getPackageName(MyPlayer::class.java)
        }/${videoSrc}")))
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun startPlayer(){
        player.prepare()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun resumePlayer(){
        player.play()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun pausePlayer(){
        player.pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun destroyPlayer(){
        player.release()
    }
}