package com.anibalbastias.android.vuro.dualcamerapp.presentation

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.fragment.app.Fragment
import androidx.multidex.MultiDexApplication
import com.anibalbastias.android.vuro.dualcamerapp.base.view.BaseModuleFragment
import com.anibalbastias.android.vuro.dualcamerapp.di.component.ApplicationComponent
import com.anibalbastias.android.vuro.dualcamerapp.di.component.DaggerApplicationComponent
import com.anibalbastias.android.vuro.dualcamerapp.di.module.ApplicationModule
import com.google.android.play.core.splitcompat.SplitCompat
import java.util.*

var context: VuroApplication? = null
fun getAppContext(): VuroApplication {
    return context!!
}

/**
 * Created by anibalbastias on 2019-11-25.
 */

class VuroApplication : MultiDexApplication() {

    companion object {
        lateinit var appContext: Context
        var applicationComponent: ApplicationComponent? = null
    }

    override fun attachBaseContext(base: Context) {
        LanguageHelper.init(base)
        val ctx = LanguageHelper.getLanguageConfigurationContext(base)
        super.attachBaseContext(ctx)
        SplitCompat.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
        context = this
        appContext = this
    }
}

private fun buildDagger(context: Context): ApplicationComponent {
    if (VuroApplication.applicationComponent == null) {
        VuroApplication.applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(context as VuroApplication))
            .build()
    }
    return VuroApplication.applicationComponent!!
}

fun Context.appComponent(): ApplicationComponent {
    return buildDagger(this.applicationContext)
}

fun BaseModuleFragment.appComponent(): ApplicationComponent {
    return buildDagger(this.context!!.applicationContext)
}