package com.anibalbastias.android.vuro.dualcamerapp.feature

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.anibalbastias.android.vuro.dualcamerapp.R
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.invisible
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.visible
import com.anibalbastias.android.vuro.dualcamerapp.base.view.BaseSplitActivity
import com.anibalbastias.android.vuro.dualcamerapp.util.StorageExtension.latestVideoFilePathBack
import com.anibalbastias.android.vuro.dualcamerapp.util.StorageExtension.latestVideoFilePathFront
import com.anibalbastias.android.vuro.dualcamerapp.util.StorageExtension.removeLatestVideos
import com.anibalbastias.android.vuro.dualcamerapp.util.StorageExtension.videoFilePath
import com.anibalbastias.android.vuro.dualcamerapp.util.StorageExtension.videoFilePath2
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.backCameraFilepath2
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.cameraRecorderBack
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.cameraRecorderFront
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.frontCameraFilepath
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.releaseCamera
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.setUpCamera
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.setVideoListener
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.setupBackCameraView
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.setupFrontCameraView
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.vuroTouchVideoViewBack
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoExtension.vuroTouchVideoViewFront
import com.anibalbastias.android.vuro.dualcamerapp.util.VideoListener
import com.anibalbastias.android.vuro.dualcamerapp.util.formatTime
import kotlinx.android.synthetic.main.activity_dual_camera.*


class VuroDualCameraActivity : BaseSplitActivity(), VideoListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dual_camera)
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
        iv_back.setOnClickListener {
            finish()
        }
    }

    override fun onSuccessfulLoad(moduleName: String, launch: Boolean) {}

    override fun onResume() {
        setUpCamera()
        super.onResume()
    }

    override fun onStop() {
//        releaseCamera()
        super.onStop()
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
            removeLatestVideos(this, latestVideoFilePathFront)
            removeLatestVideos(this, latestVideoFilePathBack)
        }, 3000)
    }

    override fun onReleaseFrontCamera() {
        frontVideoView.removeView(vuroTouchVideoViewFront)
    }

    override fun onReleaseBackCamera() {
        backVideoView.removeView(vuroTouchVideoViewBack)
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
            frontVideoView?.addView(vuroTouchVideoViewFront)
        }
    }

    override fun setupBackCamera() {
        runOnUiThread {
            backVideoView?.removeAllViews()
            setupBackCameraView()
            backVideoView?.addView(vuroTouchVideoViewBack)
        }
    }
}