package com.anibalbastias.android.vuro.dualcamerapp.base.module.coroutines.scope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface ManagedCoroutineScope : CoroutineScope {
    fun launch(block: suspend CoroutineScope.() -> Unit): Job
}