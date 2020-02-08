package com.anibalbastias.android.vuro.dualcamerapp.base.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.anibalbastias.android.vuro.R

fun <T> LiveData<T>.initObserver(lifecycleOwner: LifecycleOwner, observer: (T?) -> Unit) {
    try {
        removeObservers(lifecycleOwner)
        observe(lifecycleOwner, Observer<T> { t -> observer.invoke(t!!) })
    } catch (e: KotlinNullPointerException) {
        e.printStackTrace()
    }

}

fun <T> LiveData<T>.initObserverForever(observer: Observer<T>) {
    removeObserver(observer)
    observeForever { t -> observer.onChanged(t) }
}

@SuppressLint("ShowToast")
fun Activity.getToastTypeFaced(text: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    val toast = Toast.makeText(this, text, duration)
    val typeface = ResourcesCompat.getFont(applicationContext!!, R.font.opensans_regular)
    val toastLayout = toast.view as? LinearLayout
    val toastTV = (toastLayout?.getChildAt(0) as? TextView)
    toastTV?.typeface = typeface
    return toast
}

fun Activity.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) =
    text?.let {
        getToastTypeFaced(text, duration).show()
    }

fun String.Companion.empty() = ""

fun <T : androidx.databinding.Observable> T.addOnPropertyChanged(callback: (T) -> Unit) =
    addOnPropertyChangedCallback(
        object : androidx.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(
                sender: androidx.databinding.Observable?,
                propertyId: Int
            ) {
                return callback(sender as T)
            }
        })