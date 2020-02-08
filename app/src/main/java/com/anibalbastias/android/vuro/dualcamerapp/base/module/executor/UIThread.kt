package com.anibalbastias.android.vuro.dualcamerapp.base.module.executor

import com.anibalbastias.android.vuro.dualcamerapp.domain.base.executor.APIPostExecutionThread

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UIThread @Inject constructor() :
    APIPostExecutionThread {

    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()

}