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
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.After
import org.junit.Before

@RunWith(AndroidJUnit4::class)
@LargeTest

open class BaseTest {

    protected val idlingResource = CountingIdlingResource("idlingResource")

    @Before
    open fun setUp() {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    open fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}

class SignInTest : BaseTest() {

    @get:Rule
    val activityRule = ActivityScenarioRule(Login::class.java)
    private val countingIdlingResource = CountingIdlingResource("SignInTest")

    @Test
    fun testLoginSuccess() {
        countingIdlingResource.increment()
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText("user@gmail.com"))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText("123456789"))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())

    } // pass

    @Test
    fun testInvalidPassword() {
        idlingResource.increment()
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText("user@gmail.com"))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText("1234567"))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())

        onView(withId(R.id.note)).check(matches(withText("Email or Password is not correct")))
        idlingResource.decrement()
    }

    @Test
    fun testEmptyPassword() {
        fun testEmptyPassword() {
            idlingResource.increment()

            onView(withId(R.id.editTextSignInUsername)).perform(typeText("user@gmail.com"))
            onView(withId(R.id.editTextSignInPassword)).perform(typeText(""))
            onView(withId(R.id.buttonConfirmLogin)).perform(click())

            onView(withId(R.id.note)).check(matches(withText("Password is empty")))
            idlingResource.decrement()
        }
    } // pass

    @Test
    fun testEmptyEmail() {

            idlingResource.increment()

            onView(withId(R.id.editTextSignInUsername)).perform(typeText(""))
            onView(withId(R.id.editTextSignInPassword)).perform(typeText("123456789"))
            onView(withId(R.id.buttonConfirmLogin)).perform(click())

            onView(withId(R.id.note)).check(matches(withText("Email is empty")))
            idlingResource.decrement()

    } //pass
    @Test
    fun testInvalidUsernameValidPassword() {
        fun testInvalidUsernameValidPassword() {
            idlingResource.increment()
            onView(withId(R.id.editTextSignInUsername))
                .perform(typeText("user@gmai.com"))
            onView(withId(R.id.editTextSignInPassword))
                .perform(typeText("123456789"))
            onView(withId(R.id.buttonConfirmLogin)).perform(click())

            onView(withId(R.id.note)).check(matches(withText("Email or Password is not correct")))
            idlingResource.decrement()
        }
    }

    @Test
    fun testInvalidUsernameInvalidPassword() {
        countingIdlingResource.increment()
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText("invalid@example.com"))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText("wrongPassword"))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        Thread.sleep(1000) // Wait for the note to update
        onView(withId(R.id.note)).check(matches(withText("Email or Password is not correct")))
        countingIdlingResource.decrement()
    }

    @Test
    fun testInvalidUsernameEmptyPassword() {
        countingIdlingResource.increment()
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText("invalid@example.com"))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText(""))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        Thread.sleep(1000) // Wait for the note to update
        onView(withId(R.id.note)).check(matches(withText("Password is empty")))
        countingIdlingResource.decrement()
    }

    @Test
    fun testEmptyUsernameValidPassword() {
        countingIdlingResource.increment()
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText(""))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText("correctPassword"))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        Thread.sleep(1000) // Wait for the note to update
        onView(withId(R.id.note)).check(matches(withText("Email is empty")))
        countingIdlingResource.decrement()
    }

    @Test
    fun testEmptyUsernameInvalidPassword() {
        countingIdlingResource.increment()
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText(""))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText("wrongPassword"))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        Thread.sleep(1000) // Wait for the note to update
        onView(withId(R.id.note)).check(matches(withText("Email is empty")))
        countingIdlingResource.decrement()
    }

    @Test
    fun testEmptyUsernameEmptyPassword() {
        idlingResource.increment()
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText(""))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText(""))
        onView(withId(R.id.buttonConfirmLogin)).perform(click())
        onView(withId(R.id.note)).check(matches(withText("Email is empty")))
        idlingResource.decrement()
    }
}
