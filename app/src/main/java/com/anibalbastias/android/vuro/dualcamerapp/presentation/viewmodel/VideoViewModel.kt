package com.anibalbastias.android.vuro.dualcamerapp.presentation.viewmodel

import androidx.databinding.ObservableBoolean
import com.anibalbastias.android.vuro.dualcamerapp.base.module.coroutines.LiveResult
import com.anibalbastias.android.vuro.dualcamerapp.base.view.BaseViewModel
import com.anibalbastias.android.vuro.dualcamerapp.domain.video.usecase.UploadVideoUseCase
import com.anibalbastias.android.vuro.dualcamerapp.presentation.model.UiVideo
import javax.inject.Inject

open class VideoViewModel @Inject constructor(
    private val uploadVideoUseCase: UploadVideoUseCase
) : BaseViewModel() {

    // region Observables
    var isLoading: ObservableBoolean = ObservableBoolean(false)
    var isError: ObservableBoolean = ObservableBoolean(false)

    //region Live Data
    private val uploadVideoLiveData = LiveResult<UiVideo>()

    fun getUploadVideoLiveData() = uploadVideoLiveData
    //endregion

    fun uploadVideoData(urlVideo: String) {
        isLoading.set(true)

        uploadVideoUseCase.execute(
            liveData = uploadVideoLiveData,
            params = urlVideo
        )
    }
}