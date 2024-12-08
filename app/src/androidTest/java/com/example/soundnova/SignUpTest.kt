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
class SignUpTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Register::class.java)

    @Test
    fun testSignUpSuccess() {

            // Case 1: Valid email and valid password
            onView(withId(R.id.editTextSignUpEmail))
                .perform(typeText("testhm@gmail.com"))
            onView(withId(R.id.editTextSignUpPassword))
                .perform(typeText("Abc123456@"))
            onView(withId(R.id.buttonRegister)).perform(click())
    }

    @Test
    fun testSignUpInvalidPassword() {
        fun testSignUpInvalidPassword() {
            // Case 2: Valid email, invalid password
            onView(withId(R.id.editTextSignUpEmail))
                .perform(typeText("test@example.com"))
            onView(withId(R.id.editTextSignUpPassword))
                .perform(typeText("123")) // Invalid password format based on requirements

            // Click the sign-up button
            onView(withId(R.id.buttonRegister)).perform(click())

            // Check for "Invalid password format" message
            onView(withId(R.id.note)).check(matches(withText("Invalid password format")))
        }
    }

    @Test
    fun testSignUpInvalidEmail() {
        fun testSignUpInvalidEmail() {
            // Case 3: Invalid email, valid password
            onView(withId(R.id.editTextSignUpEmail))
                .perform(typeText("invalid-email"))
            onView(withId(R.id.editTextSignUpPassword))
                .perform(typeText("ValidPassword123"))

            // Click the sign-up button
            onView(withId(R.id.buttonRegister)).perform(click())

            // Check for "Invalid email format" message
            onView(withId(R.id.note)).check(matches(withText("Invalid email format")))
        }
    }

    @Test
    fun testSignUpInvalidEmailAndPassword() {

            // Case 4: Invalid email and invalid password
            onView(withId(R.id.editTextSignUpEmail))
                .perform(typeText("invalid-email"))
            onView(withId(R.id.editTextSignUpPassword))
                .perform(typeText("123"))
            onView(withId(R.id.buttonRegister)).perform(click())

            onView(withId(R.id.note)).check(matches(withText("Invalid email format")))
        }
    }




