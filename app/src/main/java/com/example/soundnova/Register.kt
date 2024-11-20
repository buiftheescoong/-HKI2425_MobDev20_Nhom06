
package com.example.soundnova

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest


class Register: AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonSignUp: Button
    private lateinit var textViewLoginLink: TextView
    private lateinit var note: TextView
    private lateinit var validator: Validator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        editTextName = findViewById(R.id.editTextSignUpUsername)
        editTextEmail = findViewById(R.id.editTextSignUpEmail)
        editTextPassword = findViewById(R.id.editTextSignUpPassword)
        editTextConfirmPassword = findViewById(R.id.editTextSignUpRepeatPassword)
        buttonSignUp = findViewById(R.id.buttonRegister)


        textViewLoginLink = findViewById(R.id.textViewSignIn)
        note = findViewById(R.id.note)
        firebaseAuth = FirebaseAuth.getInstance()
        validator = Validator()


        buttonSignUp.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val name = editTextName.text.toString()


            val emailStatus = validator.checkEmail(email)
            val passwordStatus = validator.checkPassword(password)


            if (emailStatus == "valid" && passwordStatus == "valid") {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            val profileUpdates = userProfileChangeRequest {
                                displayName = name
                            }
                            user!!.updateProfile(profileUpdates)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "User profile updated.")
                                    }
                                }

                            val intent = Intent(applicationContext, Login::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            } else {
                val message = when {
                    emailStatus == "empty" -> "Email is empty"
                    emailStatus == "invalid" -> "Invalid email format"
                    passwordStatus == "empty" -> "Password is empty"
                    passwordStatus == "invalid" -> "Invalid password format"
                    else -> "Unknown error"
                }
                note.setText("$message")
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        textViewLoginLink.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (firebaseAuth.currentUser != null) {
            currentUser?.reload()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.w("", "Failed to reload user", task.exception)
                }
            }
        }
    }
}
