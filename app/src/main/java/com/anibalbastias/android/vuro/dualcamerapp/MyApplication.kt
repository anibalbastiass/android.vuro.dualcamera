package com.anibalbastias.android.vuro.dualcamerapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import com.google.android.play.core.splitcompat.SplitCompat
import java.util.Locale

class MyApplication : Application() {
    override fun attachBaseContext(base: Context) {
        LanguageHelper.init(base)
        val ctx = LanguageHelper.getLanguageConfigurationContext(base)
        super.attachBaseContext(ctx)
        SplitCompat.install(this)
    }
}

internal const val LANG_EN = "en"

internal const val LANG_ES = "es"

private const val PREFS_LANG = "language"

object LanguageHelper {
    lateinit var prefs: SharedPreferences
    var language: String
        get() {
            return prefs.getString(PREFS_LANG, LANG_EN)!!
        }
        set(value) {
            prefs.edit().putString(PREFS_LANG, value).apply()
        }

    fun init(ctx: Context){
        prefs = ctx.getSharedPreferences(PREFS_LANG, Context.MODE_PRIVATE)
    }

    fun getLanguageConfigurationContext(ctx: Context): Context {
        val conf = getLanguageConfiguration()
        return ctx.createConfigurationContext(conf)
    }

    private fun getLanguageConfiguration(): Configuration {
        val conf = Configuration()
        conf.setLocale(Locale.forLanguageTag(language))
        return conf
    }

}