package com.anibalbastias.android.vuro.dualcamerapp.base.module

import com.anibalbastias.android.vuro.dualcamerapp.ui.VuroApplication
import com.anibalbastias.android.vuro.dualcamerapp.domain.base.executor.APIPostExecutionThread
import com.anibalbastias.android.vuro.dualcamerapp.domain.base.executor.APIThreadExecutor
import com.anibalbastias.android.vuro.dualcamerapp.base.module.executor.JobExecutor
import com.anibalbastias.android.vuro.dualcamerapp.base.module.executor.UIThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class BaseApplicationModule(private val application: VuroApplication) {

    @Provides
    @Singleton
    fun provideApp(): VuroApplication = application

    @Provides
    @Singleton
    fun provideAPIThreadExecutor(jobExecutor: JobExecutor): APIThreadExecutor = jobExecutor

    @Provides
    @Singleton
    fun provideAPIPostExecutionThread(uiThread: UIThread): APIPostExecutionThread = uiThread
}