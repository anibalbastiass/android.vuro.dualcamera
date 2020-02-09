package com.anibalbastias.android.vuro.dualcamerapp.feature

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.anibalbastias.android.vuro.dualcamerapp.R
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.gone
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.invisible
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.visible
import com.anibalbastias.android.vuro.dualcamerapp.base.view.BaseSplitActivity
import com.anibalbastias.android.vuro.dualcamerapp.feature.VuroTouchVideoView.TouchListener
import com.anibalbastias.android.vuro.dualcamerapp.util.formatTime
import com.daasuu.camerarecorder.CameraRecordListener
import com.daasuu.camerarecorder.CameraRecorder
import com.daasuu.camerarecorder.CameraRecorderBuilder
import com.daasuu.camerarecorder.LensFacing
import kotlinx.android.synthetic.main.activity_square.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class SquareActivity : BaseSplitActivity() {

    private var cameraWidth = 1280
    private var cameraHeight = 720
    private var videoWidth = 720
    private var videoHeight = 720

    private var vuroTouchVideoView: VuroTouchVideoView? = null
    private var vuroTouchVideoView2: VuroTouchVideoView? = null

    var cameraRecorder: CameraRecorder? = null
    var cameraRecorder2: CameraRecorder? = null

    private var filepath: String? = null
    private var filepath2: String? = null

    private var lensFacing = LensFacing.BACK
    private var lensFacing2 = LensFacing.FRONT

    private var toggleClick = false

    val intervalCounter: Long = 1000
    //val maxCounter: Long = 3 * 60 * intervalCounter
    val maxCounter: Long = 5 * intervalCounter

    var recordCount: CountDownTimer = object : CountDownTimer(maxCounter, intervalCounter) {
        override fun onTick(millisUntilFinished: Long) {
            val diff = maxCounter - millisUntilFinished

            tv_counter.visible()
            tv_counter.text = formatTime(diff)
        }

        override fun onFinish() {
            btn_record.performClick()

            Handler().postDelayed({
                btn_record.performClick()
            }, 100)
        }
    }

    override fun onSuccessfulLoad(moduleName: String, launch: Boolean) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square)
        onCreateActivity()
    }

    private fun onCreateActivity() {

        btn_record.setOnClickListener { v: View? ->
            if (btn_record.text == getString(R.string.app_record)) {
                filepath =
                    videoFilePath
                filepath2 =
                    videoFilePath2
                cameraRecorder!!.start(filepath)
                cameraRecorder2!!.start(filepath2)
                btn_record.text = "Stop"
            } else {
                cameraRecorder!!.stop()
                cameraRecorder2!!.stop()
                btn_record.text = getString(R.string.app_record)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setUpCamera()
    }

    override fun onStop() {
        super.onStop()
        releaseCamera()
    }

    private fun releaseCamera() {
        if (vuroTouchVideoView != null) {
            vuroTouchVideoView!!.onPause()
        }
        if (vuroTouchVideoView2 != null) {
            vuroTouchVideoView2!!.onPause()
        }
        if (cameraRecorder != null) {
            cameraRecorder!!.stop()
            cameraRecorder!!.release()
            cameraRecorder = null
        }
        if (cameraRecorder2 != null) {
            cameraRecorder2!!.stop()
            cameraRecorder2!!.release()
            cameraRecorder2 = null
        }
        if (vuroTouchVideoView != null) {
            wrap_view.removeView(vuroTouchVideoView)
            vuroTouchVideoView = null
        }
        if (vuroTouchVideoView2 != null) {
            wrap_view2.removeView(
                vuroTouchVideoView2
            )
            vuroTouchVideoView2 = null
        }
    }

    private fun setUpCameraView() {
        runOnUiThread {

            wrap_view?.removeAllViews()
            vuroTouchVideoView = null
            vuroTouchVideoView = VuroTouchVideoView(applicationContext)
            vuroTouchVideoView!!.setTouchListener(object : TouchListener {
                override fun onTouch(event: MotionEvent?, width: Int, height: Int) {
                    if (cameraRecorder == null) return
                    cameraRecorder!!.changeManualFocusPoint(event?.x!!, event.y, width, height)
                }
            })
            wrap_view?.addView(vuroTouchVideoView)

            if (wrap_view.indexOfChild(vuroTouchVideoView) == -1) wrap_view.gone()
        }

        runOnUiThread {
            wrap_view2?.removeAllViews()
            vuroTouchVideoView2 = null
            vuroTouchVideoView2 = VuroTouchVideoView(applicationContext)
            vuroTouchVideoView2!!.setTouchListener(object : TouchListener {
                override fun onTouch(event: MotionEvent?, width: Int, height: Int) {
                    if (cameraRecorder2 == null) return
                    cameraRecorder2!!.changeManualFocusPoint(event?.x!!, event.y, width, height)
                }
            })
            wrap_view2?.addView(vuroTouchVideoView2)

            if (wrap_view2.indexOfChild(vuroTouchVideoView2) == -1) wrap_view2.gone()
        }
    }

    private fun setUpCamera() {
        setUpCameraView()
        cameraRecorder = CameraRecorderBuilder(this, vuroTouchVideoView)
            .recordNoFilter(true)
            .cameraRecordListener(object : CameraRecordListener {
                override fun onGetFlashSupport(flashSupport: Boolean) {

                }

                override fun onRecordComplete() {
                    exportMp4ToGallery(
                        applicationContext,
                        filepath
                    )

                    recordCount.cancel()
                    tv_counter.invisible()
                }

                override fun onRecordStart() {
                    recordCount.start()
                }

                override fun onError(exception: Exception) {

                }

                override fun onCameraThreadFinish() {
                    if (toggleClick) {
                        runOnUiThread { setUpCamera() }
                    }
                    toggleClick = false
                }
            })
            .videoSize(videoWidth, videoHeight)
            .cameraSize(cameraWidth, cameraHeight)
            .lensFacing(lensFacing)
            .build()

        cameraRecorder2 = CameraRecorderBuilder(this, vuroTouchVideoView2)
            .recordNoFilter(true)
            .cameraRecordListener(object : CameraRecordListener {
                override fun onGetFlashSupport(flashSupport: Boolean) {

                }

                override fun onRecordComplete() {
                    exportMp4ToGallery(
                        applicationContext,
                        filepath2
                    )
                }

                override fun onRecordStart() {}
                override fun onError(exception: Exception) {
                    Log.e("CameraRecorder", exception.toString())
                }

                override fun onCameraThreadFinish() {
                    if (toggleClick) {
                        runOnUiThread { setUpCamera() }
                    }
                    toggleClick = false
                }
            })
            .videoSize(videoWidth, videoHeight)
            .cameraSize(cameraWidth, cameraHeight)
            .lensFacing(lensFacing2)
            .build()
    }

    companion object {
        fun exportMp4ToGallery(
            context: Context,
            filePath: String?
        ) {
            val values = ContentValues(2)
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            values.put(MediaStore.Video.Media.DATA, filePath)
            context.contentResolver.insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                values
            )
            context.sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse("file://$filePath")
                )
            )
        }

        val videoFilePath: String
            @SuppressLint("SimpleDateFormat")
            get() = androidMoviesFolder.absolutePath + "/" + SimpleDateFormat(
                "yyyyMM_dd-HHmmss"
            ).format(Date()) + "cameraRecorder_front.mp4"

        val videoFilePath2: String
            @SuppressLint("SimpleDateFormat")
            get() = androidMoviesFolder.absolutePath + "/" + SimpleDateFormat(
                "yyyyMM_dd-HHmmss"
            ).format(Date()) + "cameraRecorder_back.mp4"

        private val androidMoviesFolder: File
            get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)

    }
}