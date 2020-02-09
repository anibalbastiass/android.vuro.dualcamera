package com.anibalbastias.android.vuro.dualcamerapp.data.dataStoreFactory.video.repository

import com.anibalbastias.android.vuro.dualcamerapp.data.video.VideoApiService
import com.anibalbastias.android.vuro.dualcamerapp.domain.video.repository.IVideoRepository
import com.anibalbastias.android.vuro.dualcamerapp.presentation.mapper.VideoUiMapper
import com.anibalbastias.android.vuro.dualcamerapp.base.module.coroutines.await
import com.anibalbastias.android.vuro.dualcamerapp.presentation.model.UiVideo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class VideoRepositoryImpl @Inject constructor(
    private val videoApiService: VideoApiService,
    private val videoUiMapper: VideoUiMapper
) : IVideoRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun uploadVideo(urlVideo: String): UiVideo? {
        return withContext(ioDispatcher) {
            with(videoUiMapper) {
                val response = videoApiService.uploadVideo(urlVideo).await()
                response?.fromRemoteToUi()
            }
        }
    }
}