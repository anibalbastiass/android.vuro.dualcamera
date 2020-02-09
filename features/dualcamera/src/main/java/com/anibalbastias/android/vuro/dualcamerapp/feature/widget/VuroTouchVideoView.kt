package com.anibalbastias.android.vuro.dualcamerapp.feature.widget

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener

class VuroTouchVideoView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs), OnTouchListener {

    private var touchListener: TouchListener? = null

    override fun onTouch(
        v: View,
        event: MotionEvent
    ): Boolean {
        val actionMasked = event.actionMasked
        if (actionMasked != MotionEvent.ACTION_DOWN) {
            return false
        }
        if (touchListener != null) {
            touchListener?.onTouch(event, v.width, v.height)
        }
        return false
    }

    interface TouchListener {
        fun onTouch(event: MotionEvent?, width: Int, height: Int)
    }

    fun setTouchListener(touchListener: TouchListener?) {
        this.touchListener = touchListener
    }

    init {
        setOnTouchListener(this)
    }
}