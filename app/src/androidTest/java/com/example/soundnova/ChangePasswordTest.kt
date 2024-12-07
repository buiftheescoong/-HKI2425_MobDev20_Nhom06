package com.example.soundnova

import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ChangePasswordTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(ChangePassword::class.java)
    private val countingIdlingResource = CountingIdlingResource("ChangePasswordTest")

    @Test
    fun testInvalidCurrentPassword() {
        countingIdlingResource.increment()
        onView(withId(R.id.editTextCurrentPass))
            .perform(typeText("wrongPassword"))
        onView(withId(R.id.editTextNewPass))
            .perform(typeText("ValidNewPassword123!"))
        onView(withId(R.id.editTextConfirmNewPass))
            .perform(typeText("ValidNewPassword123!"))
        onView(withId(R.id.buttonSendChange)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("Password is not correct")))
        countingIdlingResource.decrement()
    }

    @Test
    fun testEmptyCurrentPassword() {
        countingIdlingResource.increment()
        onView(withId(R.id.editTextCurrentPass))
            .perform(typeText(""))
        onView(withId(R.id.editTextNewPass))
            .perform(typeText("Valid1NewPassword123!"))
        onView(withId(R.id.editTextConfirmNewPass))
            .perform(typeText("Valid2NewPassword123!"))
        onView(withId(R.id.buttonSendChange)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("Password is not correct")))
        countingIdlingResource.decrement()
    }

    @Test
    fun testInvalidNewPassword() {
        countingIdlingResource.increment()
        onView(withId(R.id.editTextCurrentPass))
            .perform(typeText("validPassword"))
        onView(withId(R.id.editTextNewPass))
            .perform(typeText("short*"))
        onView(withId(R.id.editTextConfirmNewPass))
            .perform(typeText("short"))
        onView(withId(R.id.buttonSendChange)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("New password is not correct")))
        countingIdlingResource.decrement()
    }

    @Test
    fun testMismatchedNewPassword() {
        countingIdlingResource.increment()
        onView(withId(R.id.editTextCurrentPass))
            .perform(typeText("validPassword"))
        onView(withId(R.id.editTextNewPass))
            .perform(typeText("ValidNewPassword123!"))
        onView(withId(R.id.editTextConfirmNewPass))
            .perform(typeText("MismatchedPassword123!"))
        onView(withId(R.id.buttonSendChange)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("New password is not correct")))
        countingIdlingResource.decrement()
    }

    @Test
    fun testSuccessfulPasswordChange() {
        countingIdlingResource.increment()
        onView(withId(R.id.editTextCurrentPass))
            .perform(typeText("validPassword"))
        onView(withId(R.id.editTextNewPass))
            .perform(typeText("ValidNewPassword123!"))
        onView(withId(R.id.editTextConfirmNewPass))
            .perform(typeText("ValidNewPassword123!"))
        onView(withId(R.id.buttonSendChange)).perform(click())

        // Kiểm tra Intent được gửi đi bằng Espresso Intents
        Intents.init()
        intended(hasComponent(HomeActivity::class.java.name))
        Intents.release()

        countingIdlingResource.decrement()
    }

}
