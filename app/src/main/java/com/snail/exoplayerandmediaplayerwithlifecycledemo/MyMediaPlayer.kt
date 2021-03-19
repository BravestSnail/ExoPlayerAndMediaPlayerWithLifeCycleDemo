package com.snail.exoplayerandmediaplayerwithlifecycledemo

import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/*将MediaPlayer与Activity的生命周期绑定*/
class MyMediaPlayer : MediaPlayer(), LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pausePlayer(){
        pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resumePlayer(){
        start()
    }
}
