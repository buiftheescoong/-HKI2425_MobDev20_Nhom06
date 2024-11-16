package com.example.soundnova

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class PhoneAuthActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phone_auth)

        // Khởi tạo FirebaseAuth
        auth = FirebaseAuth.getInstance()

        val phoneNumberInput = findViewById<EditText>(R.id.phoneNumberInput)
        val sendCodeButton = findViewById<Button>(R.id.sendCodeButton)
        val verificationCodeInput = findViewById<EditText>(R.id.verificationCodeInput)
        val verifyCodeButton = findViewById<Button>(R.id.verifyCodeButton)

        // Gửi mã xác thực
        sendCodeButton.setOnClickListener {
            val phoneNumber = phoneNumberInput.text.toString().trim()
            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sendVerificationCode(phoneNumber)
        }

        // Xác nhận mã
        verifyCodeButton.setOnClickListener {
            val code = verificationCodeInput.text.toString().trim()
            if (code.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mã xác nhận!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            verifyCode(code)
        }
    }

    private fun sendVerificationCode(phoneNumber: String) {
        val formattedPhoneNumber = formatPhoneNumber(phoneNumber)
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(formattedPhoneNumber)  // Số điện thoại đã được định dạng
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // Xác minh thành công
            signInWithCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // Xác minh thất bại
            Toast.makeText(this@PhoneAuthActivity, "Lỗi: ${e.message}", Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(verificationId, token)
            this@PhoneAuthActivity.verificationId = verificationId
            this@PhoneAuthActivity.resendToken = token
            Toast.makeText(this@PhoneAuthActivity, "Mã xác minh đã được gửi!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                    // Xử lý sau khi đăng nhập (chuyển sang màn hình chính hoặc lưu trạng thái)
                } else {
                    Toast.makeText(this, "Lỗi: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun formatPhoneNumber(phoneNumber: String): String {
        // Bỏ ký tự không phải số và chuẩn hóa định dạng
        val trimmedPhone = phoneNumber.trim().replace(" ", "").replace("-", "")
        return if (trimmedPhone.startsWith("0")) {
            // Chuyển số bắt đầu với "0" thành "+84"
            "+84" + trimmedPhone.substring(1)
        } else if (!trimmedPhone.startsWith("+")) {
            // Nếu không có "+" ở đầu, thêm "+84"
            "+84$trimmedPhone"
        } else {
            // Nếu đã ở định dạng đúng, trả về như cũ
            trimmedPhone
        }
    }

}
