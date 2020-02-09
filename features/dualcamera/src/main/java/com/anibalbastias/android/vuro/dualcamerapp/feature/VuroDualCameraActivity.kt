package com.anibalbastias.android.vuro.dualcamerapp.feature

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.anibalbastias.android.vuro.dualcamerapp.R
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.gone
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.invisible
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.visible
import com.anibalbastias.android.vuro.dualcamerapp.base.view.BaseSplitActivity
import com.anibalbastias.android.vuro.dualcamerapp.util.StorageExtension
import com.anibalbastias.android.vuro.dualcamerapp.util.StorageExtension.videoFilePath
import com.anibalbastias.android.vuro.dualcamerapp.util.StorageExtension.videoFilePath2
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.cameraRecorderFront
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.cameraRecorderBack
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.frontCameraFilepath
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.backCameraFilepath2
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.releaseCamera
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.setUpCamera
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.setVideoListener
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.setupBackCameraView
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.setupFrontCameraView
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoListener
import com.anibalbastias.android.vuro.dualcamerapp.util.formatTime
import kotlinx.android.synthetic.main.activity_square.*


class VuroDualCameraActivity : BaseSplitActivity(), VideoListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square)
        setView()
        setVideoListener(this)
    }

    private fun setView() {

        btn_record.setOnClickListener { v: View? ->
            if (btn_record.text == getString(R.string.app_record)) {
                frontCameraFilepath = videoFilePath
                backCameraFilepath2 = videoFilePath2
                cameraRecorderFront?.start(frontCameraFilepath)
                cameraRecorderBack?.start(backCameraFilepath2)
                btn_record.text = getString(R.string.app_stop)
            } else {
                cameraRecorderFront?.stop()
                cameraRecorderBack?.stop()
                btn_record.text = getString(R.string.app_record)
            }
        }
    }

    override fun onSuccessfulLoad(moduleName: String, launch: Boolean) {}

    override fun onResume() {
        super.onResume()
        setUpCamera()
    }

    override fun onStop() {
        super.onStop()
        releaseCamera()
    }

    override fun onTickCounter(diffTime: Long) {
        tv_counter.visible()
        tv_counter.text = formatTime(diffTime)
    }

    override fun onTickFinish() {
        btn_record.performClick()

        Handler().postDelayed({
            btn_record.performClick()
        }, 100)

        Handler().postDelayed({
            // Simulate Upload Video from Use Case

            // Remove Video
            StorageExtension.removeLatestVideos(this, StorageExtension.latestVideoFilePath)
            StorageExtension.removeLatestVideos(this, StorageExtension.latestVideoFilePath2)
        }, 3000)
    }

    override fun onReleaseFrontCamera() {
        frontVideoView.removeView(VideoExtension.vuroTouchVideoViewFront)
    }

    override fun onReleaseBackCamera() {
        backVideoView.removeView(VideoExtension.vuroTouchVideoViewBack)
    }

    override fun onFrontCameraRecordComplete() {
        tv_counter.invisible()
    }

    override fun onBackCameraRecordComplete() {
        runOnUiThread { setUpCamera() }
    }

    override fun onFrontCameraThreadFinish() {
        tv_counter.invisible()
    }

    override fun onBackCameraThreadFinish() {
        runOnUiThread { setUpCamera() }
    }

    override fun setupFrontCamera() {
        runOnUiThread {
            frontVideoView?.removeAllViews()
            setupFrontCameraView()
            frontVideoView?.addView(VideoExtension.vuroTouchVideoViewFront)
            if (frontVideoView.indexOfChild(VideoExtension.vuroTouchVideoViewFront) == -1) frontVideoView.gone()
        }
    }

    override fun setupBackCamera() {
        runOnUiThread {
            backVideoView?.removeAllViews()
            setupBackCameraView()
            backVideoView?.addView(VideoExtension.vuroTouchVideoViewBack)
            if (backVideoView.indexOfChild(VideoExtension.vuroTouchVideoViewBack) == -1) backVideoView.gone()
        }
    }
}