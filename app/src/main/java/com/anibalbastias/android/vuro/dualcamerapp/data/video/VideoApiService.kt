package com.anibalbastias.android.vuro.dualcamerapp.data.video

import com.anibalbastias.android.vuro.dualcamerapp.data.dataStoreFactory.video.model.RemoteVideo
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query


interface VideoApiService {

    //region Video
    @POST("v1/upload")
    fun uploadVideo(@Query("url_video") urlVideo: String): Call<RemoteVideo>
    //endregion

}