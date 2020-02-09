package com.anibalbastias.android.vuro.dualcamerapp.base.module.component

import com.anibalbastias.android.vuro.dualcamerapp.ui.VuroApplication
import com.anibalbastias.android.vuro.dualcamerapp.base.module.executor.JobExecutor
import com.anibalbastias.android.vuro.dualcamerapp.base.module.executor.UIThread


interface BaseApplicationComponent {

    fun inject(application: VuroApplication)
    fun threadExecutor(): JobExecutor
    fun postExecutionThread(): UIThread
}