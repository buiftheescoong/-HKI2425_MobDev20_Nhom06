package com.example.soundnova

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.soundnova.Login
import com.example.soundnova.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UILoginTest {

    @get:Rule
    val mActivityScenarioRule = ActivityScenarioRule(Login::class.java)

    @Test
    fun uILoginNoteTest() {
        onView(
            allOf(
                withId(R.id.note), withText("Email or Password is not correct"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        ).check(matches(withText("Email or Password is not correct")))
    }


    @Test
    fun uILoginForgotTest() {
        onView(
            allOf(
                withId(R.id.textViewForgetPassword), withText("Forgot your password?"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        ).check(matches(withText("Forgot your password?")))
    }
}
