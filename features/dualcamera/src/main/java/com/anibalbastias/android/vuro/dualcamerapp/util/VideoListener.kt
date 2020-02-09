package com.anibalbastias.android.vuro.dualcamerapp.util

interface VideoListener {
    fun onTickCounter(diffTime: Long)
    fun onTickFinish()
    fun onReleaseFrontCamera()
    fun onReleaseBackCamera()
    fun onFrontCameraRecordComplete()
    fun onBackCameraRecordComplete()
    fun onFrontCameraThreadFinish()
    fun onBackCameraThreadFinish()
    fun setupFrontCamera()
    fun setupBackCamera()
}