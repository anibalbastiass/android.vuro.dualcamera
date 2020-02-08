package com.anibalbastias.android.vuro.dualcamerapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitcompat.SplitCompat


abstract class BaseSplitActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        val ctx = newBase?.let { LanguageHelper.getLanguageConfigurationContext(it) }
        super.attachBaseContext(ctx)
        SplitCompat.install(this)
    }

}
