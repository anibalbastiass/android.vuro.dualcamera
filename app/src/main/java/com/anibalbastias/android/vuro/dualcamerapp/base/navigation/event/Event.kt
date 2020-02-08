package com.anibalbastias.android.vuro.dualcamerapp.base.navigation.event

import com.anibalbastias.android.vuro.dualcamerapp.base.view.ResourceState

/**
 * Created by anibalbastias on 2019-08-15.
 */

open class Event<out T>(private val content: T) {

    var status = ResourceState.LOADING
    var consumed = false
        private set // Allow external read but not write

    /**
     * Consumes the content if it's not been consumed yet.
     * @return The unconsumed content or `null` if it was consumed already.
     */
    fun consume(): T? {
        return if (consumed) {
            null
        } else {
            consumed = true
            status = ResourceState.DEFAULT
            content
        }
    }

    /**
     * @return The content whether it's been handled or not.
     */
    fun peek(): T = content

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event<*>

        if (content != other.content) return false
        if (consumed != other.consumed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content?.hashCode() ?: 0
        result = 31 * result + consumed.hashCode()
        return result
    }
}