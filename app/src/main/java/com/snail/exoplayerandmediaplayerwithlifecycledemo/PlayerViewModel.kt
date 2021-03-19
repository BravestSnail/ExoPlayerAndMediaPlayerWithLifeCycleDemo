package com.snail.exoplayerandmediaplayerwithlifecycledemo

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.common.reflect.Reflection.getPackageName

class PlayerViewModel() : ViewModel() {
    val mediaPlayer = MyMediaPlayer()
    private val _progressBarVisibility = MutableLiveData(View.VISIBLE)
    private val _videoResolution = MutableLiveData(Pair(0, 0))//存储视频的尺寸

    //    private var videoPath = "android.resource://${
//        getPackageName(PlayerViewModel::class.java)
//    }/${R.raw.snowboarding}"
    val videoPath =
        "android.resource://${getPackageName(PlayerViewModel::class.java)}/${R.raw.snowboarding}"

    /*对外版本*/
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility
    val videoResolution: LiveData<Pair<Int, Int>> = _videoResolution

    init {
        loadVideo()
    }

    fun loadVideo() {
        mediaPlayer.apply {
            reset()
            _progressBarVisibility.value = View.VISIBLE
            setDataSource(videoPath)
            setOnPreparedListener {
                _progressBarVisibility.value = View.INVISIBLE
                isLooping = true
                it.start()
            }
            setOnVideoSizeChangedListener { mp, width, height ->
                _videoResolution.value = Pair(width, height)
            }
            prepareAsync()
        }
    }

    /*ViewModel被破坏的时候释放资源*/
    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }

    /*重新发送视频的宽高比*/
    fun emmitVideoResolution() {
        _videoResolution.value = _videoResolution.value
    }
}