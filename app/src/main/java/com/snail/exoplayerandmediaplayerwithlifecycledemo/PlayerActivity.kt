package com.snail.exoplayerandmediaplayerwithlifecycledemo

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.SurfaceHolder
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.snail.exoplayerandmediaplayerwithlifecycledemo.databinding.ActivityPlayerBinding

/*该Player根据b站up主“longway777”视频所写
* 由MyMediaPlayer、PlayerViewModel和PlayerActivity组成*/

@Suppress("DEPRECATION")
class PlayerActivity : AppCompatActivity() {
    private val viewModel: PlayerViewModel by viewModels()

    private lateinit var binding : ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPlayerBinding>(this, R.layout.activity_player)
        binding.lifecycleOwner = this

        /*将Activity的生命周期与mediaPlayer绑定*/
        lifecycle.addObserver(viewModel.mediaPlayer)

        viewModel.apply {
            progressBarVisibility.observe(this@PlayerActivity, Observer {
                binding.progressBar.visibility = it
            })

            videoResolution.observe(this@PlayerActivity, Observer {
                /*将resizePlayer放入playFrame的消息队列里
                * 当layout构建完毕再执行函数*/
                binding.playFrame.post {
                    resizePlayer(it.first, it.second)
                }
            })
        }

        binding.surfaceView.holder.addCallback(object :SurfaceHolder.Callback{
            override fun surfaceCreated(holder: SurfaceHolder) {

            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                viewModel.mediaPlayer.setDisplay(holder)
                viewModel.mediaPlayer.setScreenOnWhilePlaying(true)//一直点亮屏幕
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {

            }

        })


    }

    /*隐藏系统状态栏*/
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            hideSystemUI()
            viewModel.emmitVideoResolution()
        }
    }

    private fun resizePlayer(width: Int, height: Int){
        if (width == 0 || height == 0) return
        binding.surfaceView.layoutParams = FrameLayout.LayoutParams(
            binding.playFrame.height * width / height,
            FrameLayout.LayoutParams.MATCH_PARENT,
            Gravity.CENTER)
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}