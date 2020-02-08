package com.anibalbastias.android.vuro.dualcamerapp.base.module.coroutines.scope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * Created by Anibal Bastias Soto on 2019-12-26.
 */

interface ManagedCoroutineScope : CoroutineScope {
    fun launch(block: suspend CoroutineScope.() -> Unit): Job
}