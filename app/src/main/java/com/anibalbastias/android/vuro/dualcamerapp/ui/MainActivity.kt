package com.anibalbastias.android.vuro.dualcamerapp.ui

import android.os.Bundle
import com.anibalbastias.android.vuro.R
import com.anibalbastias.android.vuro.dualcamerapp.base.view.BaseSplitActivity
import com.anibalbastias.android.vuro.dualcamerapp.presentation.appComponent
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory

class MainActivity : BaseSplitActivity() {

    companion object {
        private const val PACKAGE_NAME = "com.anibalbastias.android.vuro.dualcamerapp.feature"
        private const val DUAL_CAMERA_FEATURE_CLASSNAME = "$PACKAGE_NAME.DualCameraActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.app_name)
        setContentView(R.layout.activity_main)
        appComponent().inject(this)

        manager = SplitInstallManagerFactory.create(this)
    }

    override fun onResume() {
        manager.registerListener(listener)
        super.onResume()
    }

    override fun onPause() {
        manager.unregisterListener(listener)
        super.onPause()
    }

    override fun onSuccessfulLoad(moduleName: String, launch: Boolean) {
        if (launch) {
            when (moduleName) {
                moduleDualCamera -> launchActivity(DUAL_CAMERA_FEATURE_CLASSNAME)
            }
        }
    }
}
