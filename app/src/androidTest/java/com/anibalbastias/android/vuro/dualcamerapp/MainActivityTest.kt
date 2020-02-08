package com.anibalbastias.android.vuro.dualcamerapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anibalbastias.android.vuro.R
import com.anibalbastias.android.vuro.dualcamerapp.split.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * A basic test to demonstrate testing of a base class in `com.android.application`.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val rule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Test
    fun launchMainActivity() {
        onView(withId(R.id.instructions)).check(matches(isDisplayed()))
    }
}
