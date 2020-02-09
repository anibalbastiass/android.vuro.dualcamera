package com.anibalbastias.android.vuro.dualcamerapp.data.video

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class VuroPIGSONManager private constructor() {

    private fun createGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    private fun createGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder
    }

    companion object {

        private var instance: VuroPIGSONManager? = null

        fun create(): Gson {
            if (instance == null) {
                instance = VuroPIGSONManager()
            }
            return instance!!.createGson()
        }

        fun createGsonBuilder(): GsonBuilder {
            if (instance == null) {
                instance = VuroPIGSONManager()
            }
            return instance!!.createGsonBuilder()
        }
    }
}