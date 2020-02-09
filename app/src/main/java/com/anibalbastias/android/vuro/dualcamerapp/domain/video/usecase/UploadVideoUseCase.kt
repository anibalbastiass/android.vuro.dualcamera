package com.anibalbastias.android.vuro.dualcamerapp.domain.video.usecase

import com.anibalbastias.android.vuro.dualcamerapp.domain.video.repository.IVideoRepository
import com.anibalbastias.android.vuro.dualcamerapp.base.module.coroutines.ResultUseCase
import com.anibalbastias.android.vuro.dualcamerapp.presentation.model.UiVideo
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class UploadVideoUseCase @Inject constructor(private val videoRepository: IVideoRepository) :
    ResultUseCase<String, UiVideo>(
        backgroundContext = Dispatchers.IO,
        foregroundContext = Dispatchers.Main
    ) {

    override suspend fun executeOnBackground(params: String): UiVideo? =
        videoRepository.uploadVideo(params)

}