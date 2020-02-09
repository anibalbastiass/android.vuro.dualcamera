package com.anibalbastias.android.vuro.dualcamerapp.ui.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build


@SuppressLint("ObsoleteSdkInt")
fun Activity.checkPermissions(requestCode: Int): Boolean {

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return true
    }

    // request camera permission if it has not been grunted.
    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
        checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
    ) {
        requestPermissions(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), requestCode
        )
        return false
    }
    return true
}