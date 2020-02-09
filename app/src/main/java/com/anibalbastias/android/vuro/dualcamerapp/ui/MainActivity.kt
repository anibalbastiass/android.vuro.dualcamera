package com.anibalbastias.android.vuro.dualcamerapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.anibalbastias.android.vuro.R
import com.anibalbastias.android.vuro.dualcamerapp.base.view.BaseSplitActivity
import com.anibalbastias.android.vuro.dualcamerapp.presentation.appComponent
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory

class MainActivity : BaseSplitActivity() {

    companion object {
        private const val PACKAGE_NAME = "com.anibalbastias.android.vuro.dualcamerapp.feature"
        private const val SQUARE_FEATURE_CLASSNAME = "$PACKAGE_NAME.SquareActivity"
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
        checkPermission()
    }

    override fun onPause() {
        manager.unregisterListener(listener)
        super.onPause()
    }

    override fun onSuccessfulLoad(moduleName: String, launch: Boolean) {
        if (launch) {
            when (moduleName) {
                moduleDualCamera -> launchActivity(SQUARE_FEATURE_CLASSNAME)
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
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        this@MainActivity,
                        "camera permission has been grunted.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "[WARN] camera permission is not grunted.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        // request camera permission if it has not been grunted.
        if (checkSelfPermission(Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED || checkSelfPermission(
                Manifest.permission.RECORD_AUDIO
            ) !== PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), MainActivity.CAMERA_PERMISSION_REQUEST_CODE
            )
            return false
        }
        return true
    }
}
