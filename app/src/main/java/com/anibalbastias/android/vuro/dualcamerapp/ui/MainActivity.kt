package com.anibalbastias.android.vuro.dualcamerapp.ui

import android.content.pm.PackageManager
import android.os.Bundle
import com.anibalbastias.android.vuro.R
import com.anibalbastias.android.vuro.dualcamerapp.base.extension.toast
import com.anibalbastias.android.vuro.dualcamerapp.base.view.BaseSplitActivity
import com.anibalbastias.android.vuro.dualcamerapp.presentation.appComponent
import com.anibalbastias.android.vuro.dualcamerapp.ui.util.checkPermissions
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory

class MainActivity : BaseSplitActivity() {

    companion object {
        private const val PACKAGE_NAME = "com.anibalbastias.android.vuro.dualcamerapp.feature"
        private const val DUAL_CAMERA_FEATURE_CLASSNAME = "$PACKAGE_NAME.VuroDualCameraActivity"
        private const val CAMERA_PERMISSION_REQUEST_CODE = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent().inject(this)

        manager = SplitInstallManagerFactory.create(this)
    }

    override fun onResume() {
        manager.registerListener(listener)
        super.onResume()
        checkPermissions(CAMERA_PERMISSION_REQUEST_CODE)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toast("camera permission has been grunted.")
                } else {
                    toast("[WARN] camera permission is not grunted.")

                }
            }
        }
    }
}
