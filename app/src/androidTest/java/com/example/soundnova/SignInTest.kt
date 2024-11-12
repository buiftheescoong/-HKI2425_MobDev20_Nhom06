package com.example.soundnova


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches


@RunWith(AndroidJUnit4::class)
@LargeTest
class SignInTest {
//    @get:Rule
//    val composeTestRule = createComposeRule()
    @get:Rule
    val activityRule = ActivityScenarioRule(Login::class.java)

    @Test
    fun testLoginSuccess() {
        // Nhập đúng email và mật khẩu
        onView(withId(R.id.editTextSignInUsername))
            .perform(typeText("test@example.com"))
        onView(withId(R.id.editTextSignInPassword))
            .perform(typeText("correctPassword"))

        // Nhấn nút đăng nhập
        onView(withId(R.id.buttonConfirmLogin)).perform(click())

        // Kiểm tra thông báo "Thành công"
        onView(withId(R.id.note)).check(matches(withText("H")))
    }
}