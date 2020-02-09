package com.anibalbastias.android.vuro.dualcamerapp.presentation.mapper

import com.anibalbastias.android.vuro.dualcamerapp.data.dataStoreFactory.video.model.RemoteVideo
import com.anibalbastias.android.vuro.dualcamerapp.presentation.model.UiVideo
import javax.inject.Inject


open class VideoUiMapper @Inject constructor() {

    fun RemoteVideo.fromRemoteToUi() = UiVideo(
        success = success
    )

}
