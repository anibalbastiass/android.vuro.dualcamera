package com.anibalbastias.android.vuro.dualcamerapp.util

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object StorageExtension {

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

    var latestVideoFilePathFront: String = ""
    var latestVideoFilePathBack: String = ""

    val videoFilePath: String
        @SuppressLint("SimpleDateFormat")
        get() = androidMoviesFolder.absolutePath + "/" + SimpleDateFormat(
            "yyyyMM_dd-HHmmss"
        ).format(Date()) + "_VuroCamera_Front.mp4"

    val videoFilePath2: String
        @SuppressLint("SimpleDateFormat")
        get() = androidMoviesFolder.absolutePath + "/" + SimpleDateFormat(
            "yyyyMM_dd-HHmmss"
        ).format(Date()) + "_VuroCamera_Back.mp4"

    private val androidMoviesFolder: File
        get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)

    fun removeLatestVideos(
        context: Context,
        filePath: String
    ) {
        val array = arrayListOf<String>()
        array.add(filePath)

        MediaScannerConnection.scanFile(
            context,
            array.toTypedArray(), null
        ) { path, uri ->
            run {
                context.contentResolver.delete(uri, null, null)
            }
        }
    }
}