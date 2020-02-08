package com.anibalbastias.android.vuro.dualcamerapp.domain.base.executor

import io.reactivex.Scheduler

interface APIPostExecutionThread {
    val scheduler: Scheduler
}
