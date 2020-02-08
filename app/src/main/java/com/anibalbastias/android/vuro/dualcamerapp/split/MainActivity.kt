package com.anibalbastias.android.vuro.dualcamerapp.split

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.anibalbastias.android.vuro.BuildConfig
import com.anibalbastias.android.vuro.R
import com.anibalbastias.android.vuro.dualcamerapp.presentation.LANG_EN
import com.anibalbastias.android.vuro.dualcamerapp.presentation.LANG_ES
import com.anibalbastias.android.vuro.dualcamerapp.presentation.LanguageHelper
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import java.util.*

private const val PACKAGE_NAME = "com.anibalbastias.android.vuro.dualcamerapp.feature"

private const val DUAL_CAMERA_FEATURE_CLASSNAME = "$PACKAGE_NAME.DualCameraActivity"

private const val CONFIRMATION_REQUEST_CODE = 1

private const val TAG = "DynamicFeatures"

class MainActivity : BaseSplitActivity() {

    @SuppressLint("SwitchIntDef")
    private val listener = SplitInstallStateUpdatedListener { state ->
        val multiInstall = state.moduleNames().size > 1
        val langsInstall = state.languages().isNotEmpty()

        val names = if (langsInstall) {
            // We always request the installation of a single language in this sample
            state.languages().first()
        } else state.moduleNames().joinToString(" - ")

        when (state.status()) {
            SplitInstallSessionStatus.DOWNLOADING -> {
                //  In order to see this, the application has to be uploaded to the Play Store.
                displayLoadingState(state, getString(R.string.downloading, names))
            }
            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                /*
                  This may occur when attempting to download a sufficiently large module.

                  In order to see this, the application has to be uploaded to the Play Store.
                  Then features can be requested until the confirmation path is triggered.
                 */
                manager.startConfirmationDialogForResult(state, this, CONFIRMATION_REQUEST_CODE)
            }
            SplitInstallSessionStatus.INSTALLED -> {
                if (langsInstall) {
                    onSuccessfulLanguageLoad(names)
                } else {
                    onSuccessfulLoad(names, launch = !multiInstall)
                }
            }

            SplitInstallSessionStatus.INSTALLING -> displayLoadingState(
                state,
                getString(R.string.installing, names)
            )
            SplitInstallSessionStatus.FAILED -> {
                toastAndLog(
                    getString(
                        R.string.error_for_module, state.errorCode(),
                        state.moduleNames()
                    )
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CONFIRMATION_REQUEST_CODE) {
            // Handle the user's decision. For example, if the user selects "Cancel",
            // you may want to disable certain functionality that depends on the module.
            if (resultCode == Activity.RESULT_CANCELED) {
                toastAndLog(getString(R.string.user_cancelled))
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private val moduleDualCamera by lazy { getString(R.string.module_feature_dualcamera) }

    private val clickListener by lazy {
        View.OnClickListener {
            when (it.id) {
                R.id.btn_load_dualcamera -> loadAndLaunchModule(moduleDualCamera)
                R.id.lang_en -> loadAndSwitchLanguage(LANG_EN)
                R.id.lang_pl -> loadAndSwitchLanguage(LANG_ES)
            }
        }
    }

    private lateinit var manager: SplitInstallManager

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.app_name)
        setContentView(R.layout.activity_main)
        manager = SplitInstallManagerFactory.create(this)
        initializeViews()
    }

    override fun onResume() {
        // Listener can be registered even without directly triggering a download.
        manager.registerListener(listener)
        super.onResume()
    }

    override fun onPause() {
        // Make sure to dispose of the listener once it's no longer needed.
        manager.unregisterListener(listener)
        super.onPause()
    }

    /**
     * Load a feature by module name.
     * @param name The name of the feature module to load.
     */
    private fun loadAndLaunchModule(name: String) {
        updateProgressMessage(getString(R.string.loading_module, name))
        // Skip loading if the module already is installed. Perform success action directly.
        if (manager.installedModules.contains(name)) {
            updateProgressMessage(getString(R.string.already_installed))
            onSuccessfulLoad(name, launch = true)
            return
        }

        // Create request to install a feature module by name.
        val request = SplitInstallRequest.newBuilder()
            .addModule(name)
            .build()

        // Load and install the requested feature module.
        manager.startInstall(request)

        updateProgressMessage(getString(R.string.starting_install_for, name))
    }

    private fun loadAndSwitchLanguage(lang: String) {
        updateProgressMessage(getString(R.string.loading_language, lang))
        // Skip loading if the language is already installed. Perform success action directly.
        if (manager.installedLanguages.contains(lang)) {
            updateProgressMessage(getString(R.string.already_installed))
            onSuccessfulLanguageLoad(lang)
            return
        }

        // Create request to install a language by name.
        val request = SplitInstallRequest.newBuilder()
            .addLanguage(Locale.forLanguageTag(lang))
            .build()

        // Load and install the requested language.
        manager.startInstall(request)

        updateProgressMessage(getString(R.string.starting_install_for, lang))
    }

    private fun onSuccessfulLoad(moduleName: String, launch: Boolean) {
        if (launch) {
            when (moduleName) {
                moduleDualCamera -> launchActivity(DUAL_CAMERA_FEATURE_CLASSNAME)
            }
        }
    }

    private fun onSuccessfulLanguageLoad(lang: String) {
        LanguageHelper.language = lang
        recreate()
    }

    private fun launchActivity(name: String) {
        Intent().setClassName(BuildConfig.APPLICATION_ID, name)
            .also {
                startActivity(it)
            }
    }

    private fun displayLoadingState(state: SplitInstallSessionState, message: String) {
        progressBar.max = state.totalBytesToDownload().toInt()
        progressBar.progress = state.bytesDownloaded().toInt()

        updateProgressMessage(message)
    }

    private fun initializeViews() {
        progressBar = findViewById(R.id.progress_bar)
        progressText = findViewById(R.id.progress_text)
        setupClickListener()
    }

    private fun setupClickListener() {
        setClickListener(R.id.btn_load_dualcamera, clickListener)
        setClickListener(R.id.lang_en, clickListener)
        setClickListener(R.id.lang_pl, clickListener)
    }

    private fun setClickListener(id: Int, listener: View.OnClickListener) {
        findViewById<View>(id).setOnClickListener(listener)
    }

    private fun updateProgressMessage(message: String) {
        progressText.text = message
    }
}

fun MainActivity.toastAndLog(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    Log.d(TAG, text)
}
