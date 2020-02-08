package com.anibalbastias.android.vuro.dualcamerapp.base.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.anibalbastias.android.vuro.BuildConfig
import com.anibalbastias.android.vuro.R
import com.anibalbastias.android.vuro.dualcamerapp.presentation.LanguageHelper
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import java.util.*


abstract class BaseSplitActivity : AppCompatActivity() {

    val moduleDualCamera by lazy { getString(R.string.module_feature_dualcamera) }
    lateinit var manager: SplitInstallManager

    abstract fun onSuccessfulLoad(moduleName: String, launch: Boolean)

    @SuppressLint("SwitchIntDef")
    val listener = SplitInstallStateUpdatedListener { state ->
        val multiInstall = state.moduleNames().size > 1
        val langsInstall = state.languages().isNotEmpty()

        val names = if (langsInstall) {
            state.languages().first()
        } else state.moduleNames().joinToString(" - ")

        when (state.status()) {
            SplitInstallSessionStatus.INSTALLED -> {
                if (langsInstall) {
                    onSuccessfulLanguageLoad(names)
                } else {
                    onSuccessfulLoad(names, launch = !multiInstall)
                }
            }
        }
    }

    fun loadAndLaunchModule(name: String) {
        if (manager.installedModules.contains(name)) {
            onSuccessfulLoad(name, launch = true)
            return
        }

        val request = SplitInstallRequest.newBuilder()
            .addModule(name)
            .build()

        manager.startInstall(request)
    }

    fun loadAndSwitchLanguage(lang: String) {
        if (manager.installedLanguages.contains(lang)) {
            onSuccessfulLanguageLoad(lang)
            return
        }

        val request = SplitInstallRequest.newBuilder()
            .addLanguage(Locale.forLanguageTag(lang))
            .build()

        manager.startInstall(request)
    }


    fun launchActivity(name: String) {
        Intent().setClassName(BuildConfig.APPLICATION_ID, name)
            .also {
                startActivity(it)
            }
    }

    private fun onSuccessfulLanguageLoad(lang: String) {
        LanguageHelper.language = lang
        recreate()
    }

    override fun attachBaseContext(newBase: Context?) {
        val ctx = newBase?.let { LanguageHelper.getLanguageConfigurationContext(it) }
        super.attachBaseContext(ctx)
        SplitCompat.install(this)
    }

}
