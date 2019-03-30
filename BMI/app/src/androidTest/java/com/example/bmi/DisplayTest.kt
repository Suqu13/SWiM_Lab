package com.example.bmi


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DisplayTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val weightTextView = onView(withId(R.id.weight_text_view))
        weightTextView.check(matches(isDisplayed()))

        val weightEditText = onView(withId(R.id.weight_edit_text))
        weightEditText.check(matches(isDisplayed()))

        val heightTextView = onView(withId(R.id.height_text_view))
        heightTextView.check(matches(isDisplayed()))

        val heightEditText = onView(withId(R.id.height_edit_text))
        heightEditText.check(matches(isDisplayed()))

        val countButton = onView(withId(R.id.count_button))
        countButton.check(matches(isDisplayed()))

        val mainImageView = onView(withId(R.id.main_image_view))
        mainImageView.check(matches(isDisplayed()))
    }
}
