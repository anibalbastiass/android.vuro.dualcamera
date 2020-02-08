package com.anibalbastias.android.vuro.dualcamerapp.feature

import android.os.Bundle
import com.anibalbastias.android.vuro.dualcamerapp.split.BaseSplitActivity
import com.anibalbastias.android.vuro.dualcamerapp.R

class DualCameraActivity : BaseSplitActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_dualcamera)
    }

}
