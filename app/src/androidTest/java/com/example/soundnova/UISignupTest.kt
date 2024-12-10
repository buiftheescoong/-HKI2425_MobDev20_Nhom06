package com.example.soundnova

import android.view.View
import android.widget.TextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.intent.Intents

import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class UISignupTest {
    @Before
    fun setUp() {
        // Khởi tạo Intents trước khi test
        Intents.init()
    }

    @After
    fun tearDown() {
        // Giải phóng Intents sau khi test xong
        Intents.release()
    }

    // Sử dụng FragmentScenario trực tiếp trong phương thức
    @Test
    fun uiSignUpEmailTest() {
        // Khởi tạo FragmentScenario
        val scenario = FragmentScenario.launchInContainer(Register::class.java)

        // Thực hiện các hành động Espresso trực tiếp
        onView(withId(R.id.editTextSignUpEmail)).perform(typeText("testhm@gmail.com"), closeSoftKeyboard())

        // Kiểm tra email có hiển thị đúng trên giao diện
        onView(withId(R.id.editTextSignUpEmail))
            .check(matches(isDisplayed()))
            .check(matches(withText("testhm@gmail.com")))

        scenario.close() // Đảm bảo đóng FragmentScenario khi kết thúc
    }


    @Test
    fun uiSignUpPasswordTest() {
        val scenario = FragmentScenario.launchInContainer(Register::class.java)

        // Giả lập trạng thái giao diện với nội dung password
        onView(withId(R.id.editTextSignUpPassword)).perform(typeText("testpassword123"), closeSoftKeyboard())

        // Kiểm tra password có hiển thị đúng trên giao diện
        onView(withId(R.id.editTextSignUpPassword))
            .check(matches(isDisplayed()))
            .check(matches(withText("testpassword123")))

        scenario.close()
    }

    @Test
    fun uiSignUpRepeatPasswordTest() {
        val scenario = FragmentScenario.launchInContainer(Register::class.java)

        // Giả lập trạng thái giao diện với nội dung repeat password
        onView(withId(R.id.editTextSignUpRepeatPassword)).perform(typeText("testpassword123"), closeSoftKeyboard())

        // Kiểm tra repeat password có hiển thị đúng trên giao diện
        onView(withId(R.id.editTextSignUpRepeatPassword))
            .check(matches(isDisplayed()))
            .check(matches(withText("testpassword123")))

        scenario.close()
    }

    @Test
    fun uiSignUpButtonTest() {
        val scenario = FragmentScenario.launchInContainer(Register::class.java)

        // Giả lập nhập liệu vào tất cả các trường
        onView(withId(R.id.editTextSignUpEmail)).perform(typeText("testhm@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.editTextSignUpPassword)).perform(typeText("testpassword123"), closeSoftKeyboard())
        onView(withId(R.id.editTextSignUpRepeatPassword)).perform(typeText("testpassword123"), closeSoftKeyboard())

        // Giả lập nhấn nút đăng ký
        onView(withId(R.id.buttonRegister)).perform(click())

        // Kiểm tra có hiển thị thông báo lỗi hay không (trong trường hợp đăng ký thất bại)
        onView(withText("Invalid password format")).check(matches(isDisplayed()))

        scenario.close()
    }

    @Test
    fun uiSignUpTextViewSignInTest() {
        val scenario = FragmentScenario.launchInContainer(Register::class.java)

        // Giả lập nhấn vào đường link chuyển sang màn hình login
        onView(withId(R.id.textViewSignIn)).perform(click())

        // Kiểm tra Intent có mở đúng Login Activity
        // Lưu ý: Đoạn này cần phải thêm logic kiểm tra intent nếu bạn muốn kiểm tra chuyển hướng tới Login Activity
        intended(hasComponent(Login::class.java.name))

        scenario.close()
    }
}
