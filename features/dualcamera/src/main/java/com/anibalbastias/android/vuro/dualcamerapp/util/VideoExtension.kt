package com.anibalbastias.android.vuro.dualcamerapp.util

import android.app.Activity
import android.os.CountDownTimer
import android.util.Log
import android.view.MotionEvent
import com.anibalbastias.android.vuro.dualcamerapp.feature.widget.VuroTouchVideoView
import com.anibalbastias.android.vuro.dualcamerapp.ui.VuroApplication.Companion.appContext
import com.daasuu.camerarecorder.CameraRecordListener
import com.daasuu.camerarecorder.CameraRecorder
import com.daasuu.camerarecorder.CameraRecorderBuilder
import com.daasuu.camerarecorder.LensFacing

object VideoExtension {

    var cameraWidth = 1280
    var cameraHeight = 720
    var videoWidth = 720
    var videoHeight = 720

    var frontCameraFilepath: String? = null
    var backCameraFilepath2: String? = null

    const val intervalCounter: Long = 1000
    const val maxCounter: Long = 3 * 60 * intervalCounter

    var vuroTouchVideoViewFront: VuroTouchVideoView? = null
    var vuroTouchVideoViewBack: VuroTouchVideoView? = null

    var cameraRecorderFront: CameraRecorder? = null
    var cameraRecorderBack: CameraRecorder? = null

    var lensFacingFront = LensFacing.FRONT
    var lensFacingBack = LensFacing.BACK

    var toggleClick = false

    private var videoListener: VideoListener? = null
    var recordCount: CountDownTimer? = null

    fun setVideoListener(callback: VideoListener) {
        videoListener = callback
        setTimer()
    }

    private fun setTimer() {
        recordCount = object : CountDownTimer(maxCounter, intervalCounter) {
            override fun onTick(millisUntilFinished: Long) {
                val diff = maxCounter - millisUntilFinished
                videoListener?.onTickCounter(diff)
            }

            override fun onFinish() {
                videoListener?.onTickFinish()
            }
        }
    }

    fun releaseCamera() {
        if (vuroTouchVideoViewFront != null) {
            vuroTouchVideoViewFront?.onPause()
        }
        if (vuroTouchVideoViewBack != null) {
            vuroTouchVideoViewBack?.onPause()
        }
        if (cameraRecorderFront != null) {
            cameraRecorderFront?.stop()
            cameraRecorderFront?.release()
            cameraRecorderFront = null
        }
        if (cameraRecorderBack != null) {
            cameraRecorderBack?.stop()
            cameraRecorderBack?.release()
            cameraRecorderBack = null
        }
        if (vuroTouchVideoViewFront != null) {
            videoListener?.onReleaseFrontCamera()
            vuroTouchVideoViewFront = null
        }
        if (vuroTouchVideoViewBack != null) {
            videoListener?.onReleaseBackCamera()
            vuroTouchVideoViewBack = null
        }
    }

    fun setupFrontCameraView() {
        vuroTouchVideoViewFront = null
        vuroTouchVideoViewFront = VuroTouchVideoView(appContext)
    }

    fun setupBackCameraView() {
        vuroTouchVideoViewBack = null
        vuroTouchVideoViewBack = VuroTouchVideoView(appContext)
    }


    fun Activity.setUpCamera() {
        setUpCameraView()
        cameraRecorderFront = CameraRecorderBuilder(this, vuroTouchVideoViewFront)
            .recordNoFilter(true)
            .cameraRecordListener(object : CameraRecordListener {
                override fun onGetFlashSupport(flashSupport: Boolean) {

                }

                override fun onRecordComplete() {
                    StorageExtension.latestVideoFilePathFront = frontCameraFilepath!!
                    StorageExtension.exportMp4ToGallery(
                        appContext,
                        frontCameraFilepath
                    )

                    recordCount?.cancel()
                    videoListener?.onFrontCameraRecordComplete()
                }

                override fun onRecordStart() {
                    recordCount?.start()
                }

                override fun onError(exception: Exception) {

                }

                override fun onCameraThreadFinish() {
                    if (toggleClick) {
                        videoListener?.onFrontCameraThreadFinish()
                    }
                    toggleClick = false
                }
            })
            .videoSize(videoWidth, videoHeight)
            .cameraSize(cameraWidth, cameraHeight)
            .lensFacing(lensFacingFront)
            .build()

        cameraRecorderBack = CameraRecorderBuilder(this, vuroTouchVideoViewBack)
            .recordNoFilter(true)
            .cameraRecordListener(object : CameraRecordListener {
                override fun onGetFlashSupport(flashSupport: Boolean) {

                }

                override fun onRecordComplete() {
                    StorageExtension.latestVideoFilePathBack = backCameraFilepath2!!

                    StorageExtension.exportMp4ToGallery(
                        appContext,
                        backCameraFilepath2
                    )

                    recordCount?.cancel()
                    videoListener?.onBackCameraRecordComplete()
                }

                override fun onRecordStart() {
                    recordCount?.start()
                }

                override fun onError(exception: Exception) {
                    Log.e("CameraRecorder", exception.toString())
                }

                override fun onCameraThreadFinish() {
                    if (toggleClick) {
                        videoListener?.onBackCameraThreadFinish()
                    }
                    toggleClick = false
                }
            })
            .videoSize(videoWidth, videoHeight)
            .cameraSize(cameraWidth, cameraHeight)
            .lensFacing(lensFacingBack)
            .build()
    }

    private fun setUpCameraView() {
        videoListener?.setupFrontCamera()
        videoListener?.setupBackCamera()
    }
}