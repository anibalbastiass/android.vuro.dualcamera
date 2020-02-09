package com.anibalbastias.android.vuro.dualcamerapp.base.module.coroutines.scope

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


class LifecycleManagedCoroutineScope(
    val lifecycleCoroutineScope: LifecycleCoroutineScope,
    override val coroutineContext: CoroutineContext
) : ManagedCoroutineScope {
    override fun launch(block: suspend CoroutineScope.() -> Unit): Job =
        lifecycleCoroutineScope.launchWhenStarted(block)
}