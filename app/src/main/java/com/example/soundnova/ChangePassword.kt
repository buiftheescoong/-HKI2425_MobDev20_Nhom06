package com.example.soundnova

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ChangePassword : ComponentActivity() {
    private lateinit var editTextCurrentPass : EditText
    private lateinit var editTextNewPass : EditText
    private lateinit var editTextConfirmNewPass : EditText
    private lateinit var note : TextView
    private lateinit var buttonSendChange : Button
    private lateinit var buttonCancel : Button
    private lateinit var validator: Validator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)
        editTextCurrentPass = findViewById(R.id.editTextCurrentPass)
        editTextNewPass = findViewById(R.id.editTextNewPass)
        editTextConfirmNewPass = findViewById(R.id.editTextConfirmNewPass)
        note = findViewById(R.id.note)
        buttonSendChange = findViewById(R.id.buttonSendChange)
        buttonCancel = findViewById(R.id.buttonCancel)
        validator = Validator()

        buttonCancel.setOnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonSendChange.setOnClickListener {
            val oldPassword = editTextCurrentPass.text.toString()
            val newPassword = editTextNewPass.text.toString()
            val confirmNewPassword = editTextConfirmNewPass.text.toString()
            val user = FirebaseAuth.getInstance().currentUser
            val email = user?.email
            if(email != null) {
                val credential = EmailAuthProvider.getCredential(email, oldPassword)

                user?.reauthenticate(credential)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            if (newPassword == confirmNewPassword
                                && validator.checkPassword(newPassword) == "valid") {
                                user?.updatePassword(newPassword)
                                    ?.addOnCompleteListener() {task->
                                        if(task.isSuccessful) {
                                            val intent = Intent(applicationContext, HomeActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        } else {
                                            note.setText("Password is not updated")
                                        }
                                    }
                            } else {
                                note.setText("New password is not correct")
                            }
                        } else {
                            note.setText("Password is not correct")
                        }
                    }
            } else {
                note.setText("user state is not saved")
            }
        }
    }
}