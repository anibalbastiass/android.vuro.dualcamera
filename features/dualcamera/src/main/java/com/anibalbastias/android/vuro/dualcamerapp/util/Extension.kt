package com.anibalbastias.android.vuro.dualcamerapp.util

import android.content.Context
import com.anibalbastias.android.vuro.dualcamerapp.R
import java.util.concurrent.TimeUnit

fun Context.formatTime(millis: Long): String {

    val hours = TimeUnit.MILLISECONDS.toHours(millis) % 24
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60

    return when (hours) {
        0L -> String.format(
            getString(R.string.time_minutes_seconds_formatter)!!, minutes, seconds
        )
        else -> getString(R.string.time_hours_minutes_seconds_formatter, hours, minutes, seconds)!!
    }
}