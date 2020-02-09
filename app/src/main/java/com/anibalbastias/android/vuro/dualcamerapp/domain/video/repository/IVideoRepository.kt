package com.anibalbastias.android.vuro.dualcamerapp.domain.video.repository

import com.anibalbastias.android.vuro.dualcamerapp.presentation.model.UiVideo


interface IVideoRepository {
    suspend fun uploadVideo(urlVideo: String): UiVideo?
}