package com.anibalbastias.android.vuro.dualcamerapp.base.view

import androidx.lifecycle.SavedStateHandle
import com.anibalbastias.android.vuro.dualcamerapp.base.view.BaseViewModel
import javax.inject.Inject


class SharedViewModel @Inject constructor(state: SavedStateHandle) : BaseViewModel() {

    // Keep the key as a constant
    companion object {
        private const val RESULT_ITEM_KEY = "resultItemKey"
        private const val FULL_IMAGE_KEY = "fullImageKey"
    }

    private val savedStateHandle = state

}