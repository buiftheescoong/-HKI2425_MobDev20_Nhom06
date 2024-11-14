package com.example.soundnova

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
class SignInTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Login::class.java)

    @Test
    fun testLoginSuccess() {
        // Case 1: Valid username and password
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText("test@example.com"))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText("correctPassword"))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("Thành công")))
    }

    @Test
    fun testInvalidPassword() {
        // Case 2: Valid username, invalid password
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText("test@example.com"))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText("wrongPassword"))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("Email or Password is not correct")))
    }

    @Test
    fun testEmptyPassword() {
        // Case 3: Valid username, empty password
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText("test@example.com"))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText(""))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("Password is empty")))
    }

    @Test
    fun testInvalidUsernameValidPassword() {
        // Case 4: Invalid username, valid password
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText("invalid@example.com"))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText("correctPassword"))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("Email or Password is not correct")))
    }

    @Test
    fun testInvalidUsernameInvalidPassword() {
        // Case 5: Invalid username, invalid password
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText("invalid@example.com"))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText("wrongPassword"))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("Email or Password is not correct")))
    }

    @Test
    fun testInvalidUsernameEmptyPassword() {
        // Case 6: Invalid username, empty password
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText("invalid@example.com"))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText(""))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("Password is empty")))
    }

    @Test
    fun testEmptyUsernameValidPassword() {
        // Case 7: Empty username, valid password
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText(""))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText("correctPassword"))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("Email is empty")))
    }

    @Test
    fun testEmptyUsernameInvalidPassword() {
        // Case 8: Empty username, invalid password
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText(""))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText("wrongPassword"))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("Email is empty")))
    }

    @Test
    fun testEmptyUsernameEmptyPassword() {
        // Case 9: Empty username, empty password
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText(""))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText(""))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("Email is empty")))
    }
}
