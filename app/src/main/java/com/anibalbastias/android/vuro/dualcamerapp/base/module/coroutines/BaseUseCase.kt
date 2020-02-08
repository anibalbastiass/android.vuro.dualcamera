package com.anibalbastias.android.vuro.dualcamerapp.base.module.coroutines

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

/**
 * Created by anibal.bastias on 2019-12-26.
 */

abstract class BaseUseCase<P, L : MutableLiveData<*>>(
    protected open val backgroundContext: CoroutineContext,
    protected open val foregroundContext: CoroutineContext
) {
    private var parentJob = Job()

    abstract fun execute(liveData: L, params: P)

    protected fun newJob(): Job {
        parentJob = parentJob.run {
            cancelChildren()
            cancel()

            Job()
        }

        return parentJob
    }
}