package com.example.bmi


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
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
class CountTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val weightEditText = onView(withId(R.id.weight_edit_text))
        weightEditText.perform(scrollTo(), replaceText("65"), closeSoftKeyboard())

        val heightEditText = onView(withId(R.id.height_edit_text))
        heightEditText.perform(scrollTo(), replaceText("170"), closeSoftKeyboard())

        val countButton = onView(withId(R.id.count_button))
        countButton.perform(scrollTo(), click())

        val valueTextView = onView(withId(R.id.bmi_value_text_view))
        valueTextView.check(matches(withText("22,49")))
    }
}
