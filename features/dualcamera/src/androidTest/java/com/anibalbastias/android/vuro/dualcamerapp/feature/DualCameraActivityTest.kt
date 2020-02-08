package com.anibalbastias.android.vuro.dualcamerapp.feature

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anibalbastias.android.vuro.dualcamerapp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DualCameraActivityTest {

    @get:Rule
    val rule = ActivityScenarioRule<DualCameraActivity>(DualCameraActivity::class.java)

    @Test
    fun launchKotlinSampleActivity() {
        onView(withText(R.string.content_text)).check(matches(isDisplayed()))
    }
}
