package com.anibalbastias.android.vuro.dualcamerapp.data.dataStoreFactory.video.model

import com.anibalbastias.android.vuro.dualcamerapp.data.dataStoreFactory.common.TypeData
import com.google.gson.annotations.SerializedName

data class RemoteVideo(

    @field:SerializedName("success")
    val success: Boolean? = null

) : TypeData()