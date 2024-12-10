//package com.example.soundnova
//
//import androidx.fragment.app.testing.launchFragmentInContainer
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.action.ViewActions.typeText
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.matcher.ViewMatchers.withId
//import androidx.test.espresso.matcher.ViewMatchers.withText
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.filters.LargeTest
//import com.example.soundnova.screens.music_player.MusicPlayerFragment
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//@LargeTest
//class SignUpTest {
//
//    @Test
//    fun testSignUpSuccess() {
//        // Launch the fragment
//        launchFragmentInContainer<Register>()
//
//        // Case 1: Valid email and valid password
//        onView(withId(R.id.editTextSignUpEmail))
//            .perform(typeText("hoamai@gmail.com"))
//        onView(withId(R.id.editTextSignUpPassword))
//            .perform(typeText("Abcd1256@"))
//        onView(withId(R.id.buttonRegister)).perform(click())
//    }
//
//    @Test
//    fun testSignUpInvalidPassword() {
//        // Launch the fragment
//        launchFragmentInContainer<Register>()
//
//        // Case 2: Valid email, invalid password
//        onView(withId(R.id.editTextSignUpEmail))
//            .perform(typeText("hoamai@gmail.com"))
//        onView(withId(R.id.editTextSignUpPassword))
//            .perform(typeText("123")) // Invalid password format based on requirements
//
//        // Click the sign-up button
//        onView(withId(R.id.buttonRegister)).perform(click())
//        Thread.sleep(1000)
//
//        // Check for "Invalid password format" message
//        onView(withId(R.id.note)).check(matches(withText("Invalid password format")))
//    }
//
//    @Test
//    fun testSignUpInvalidEmail() {
//        // Launch the fragment
//        launchFragmentInContainer<Register>()
//
//        // Case 3: Invalid email, valid password
//        onView(withId(R.id.editTextSignUpEmail))
//            .perform(typeText("invalid-email"))
//        onView(withId(R.id.editTextSignUpPassword))
//            .perform(typeText("ValidPassword123"))
//
//        // Click the sign-up button
//        onView(withId(R.id.buttonRegister)).perform(click())
//
//        // Check for "Invalid email format" message
//        onView(withId(R.id.note)).check(matches(withText("Invalid email format")))
//    }
//
//    @Test
//    fun testSignUpInvalidEmailAndPassword() {
//        // Launch the fragment
//        launchFragmentInContainer<Register>()
//
//        // Case 4: Invalid email and invalid password
//        onView(withId(R.id.editTextSignUpEmail))
//            .perform(typeText("invalid-email"))
//        onView(withId(R.id.editTextSignUpPassword))
//            .perform(typeText("123"))
//        onView(withId(R.id.buttonRegister)).perform(click())
//
//        // Check for "Invalid email format" message
//        onView(withId(R.id.note)).check(matches(withText("Invalid email format")))
//    }
//}

package com.example.soundnova

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SignUpTest {

    @Test
    fun testSignUpSuccess() {
        // Launch the fragment
        launchFragmentInContainer<Register>()

        // Case 1: Valid email, valid password, valid repeat password
        onView(withId(R.id.editTextSignUpEmail))
            .perform(typeText("hoamai@gmail.com"))
        onView(withId(R.id.editTextSignUpPassword))
            .perform(typeText("Abcd1256@"))
        onView(withId(R.id.editTextSignUpRepeatPassword))
            .perform(typeText("Abcd1256@"))
        onView(withId(R.id.buttonRegister)).perform(click())
    }

    @Test
    fun testSignUpInvalidPassword() {
        // Launch the fragment
        launchFragmentInContainer<Register>()

        // Case 2: Valid email, invalid password, matching repeat password
        onView(withId(R.id.editTextSignUpEmail))
            .perform(typeText("hoamai@gmail.com"))
        onView(withId(R.id.editTextSignUpPassword))
            .perform(typeText("123")) // Invalid password format based on requirements
        onView(withId(R.id.editTextSignUpRepeatPassword))
            .perform(typeText("123"))
        onView(withId(R.id.buttonRegister)).perform(click())

        // Check for "Invalid password format" message
        onView(withId(R.id.note)).check(matches(withText("Invalid password format")))
    }

    @Test
    fun testSignUpInvalidEmail() {
        // Launch the fragment
        launchFragmentInContainer<Register>()

        // Case 3: Invalid email, valid password, valid repeat password
        onView(withId(R.id.editTextSignUpEmail))
            .perform(typeText("testhm@gmai.com"))
        onView(withId(R.id.editTextSignUpPassword))
            .perform(typeText("Abcd1256@"))
        onView(withId(R.id.editTextSignUpRepeatPassword))
            .perform(typeText("Abcd1256@"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.buttonRegister)).perform(click())
        Thread.sleep(3000)
        // Check for "Invalid email format" message
        onView(withId(R.id.note)).check(matches(withText("Invalid email format")))
    }

    @Test
    fun testSignUpInvalidRepeatPassword() {
        // Launch the fragment
        launchFragmentInContainer<Register>()

        // Case 4: Valid email, valid password, invalid repeat password
        onView(withId(R.id.editTextSignUpEmail))
            .perform(typeText("hoamai@gmail.com"))
        onView(withId(R.id.editTextSignUpPassword))
            .perform(typeText("ValidPassword123"))
        onView(withId(R.id.editTextSignUpRepeatPassword))
            .perform(typeText("DifferentPassword"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.buttonRegister)).perform(click())

        // Check for "Repeat password is incorrect" message
        onView(withId(R.id.note)).check(matches(withText("repeatpassword is incorrect")))
    }

    @Test
    fun testSignUpEmptyFields() {
        // Launch the fragment
        launchFragmentInContainer<Register>()

        // Case 5: All fields are empty
        onView(withId(R.id.buttonRegister)).perform(click())

        // Check for "Email is empty" message
        onView(withId(R.id.note)).check(matches(withText("Email is empty")))
    }
}
