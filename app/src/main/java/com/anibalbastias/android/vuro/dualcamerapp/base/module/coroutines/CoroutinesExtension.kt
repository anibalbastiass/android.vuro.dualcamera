package com.anibalbastias.android.vuro.dualcamerapp.base.module.coroutines

import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Anibal Bastias Soto on 2019-12-26.
 */

fun View.onClickOnce(onClick: () -> Unit) {
    setOnClickListener(object : OnClickListener {
        override fun onClick(view: View) {
            view.setOnClickListener(null)

            also { listener ->
                CoroutineScope(Dispatchers.Main).launch {
                    onClick()

                    withContext(Dispatchers.IO) { delay(500) }

                    view.setOnClickListener(listener)
                }
            }
        }
    })
}

suspend fun <T> Call<T>.await() = suspendCoroutine<T?> { continuation ->
    enqueue(object : Callback<T?> {
        override fun onResponse(call: Call<T?>, response: Response<T?>) {
            if (response.isSuccessful)
                continuation.resume(response.body())
            else
                continuation.resumeWithException(HttpException(response))
        }

        override fun onFailure(call: Call<T?>, t: Throwable) {
            continuation.resumeWithException(t)
        }
    })
}

typealias LiveResult<T> = MutableLiveData<Result<T>>

@JvmName("postCompleteResult")
fun <T> LiveResult<T>.postSuccess(value: T) = postValue(Result.OnSuccess(value))

@JvmName("postThrowableResult")
fun <T> LiveResult<T>.postThrowable(throwable: Throwable) = postValue(Result.OnError(throwable))

@JvmName("postLoadingResult")
fun <T> LiveResult<T>.postLoading() = postValue(Result.OnLoading())

@JvmName("postCancelResult")
fun <T> LiveResult<T>.postCancel() = postValue(Result.OnCancel())

@JvmName("postEmptyResult")
fun <T> LiveResult<T>.postEmpty() = postValue(Result.OnEmpty())

fun <T, L : LiveData<T>> Fragment.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(viewLifecycleOwner, Observer(body))

fun <T, L : LiveData<T>> FragmentActivity.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

private val ISO8601format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
fun String.parseISO8601Date(): Date = ISO8601format.parse(this)!!

private val yearFormat = SimpleDateFormat("yyyy", Locale.US)
fun Date.formatYear(): String = yearFormat.format(this)