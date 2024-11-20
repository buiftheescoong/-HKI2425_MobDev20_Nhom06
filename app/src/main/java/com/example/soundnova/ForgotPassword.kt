package com.example.soundnova

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ForgotPassword : AppCompatActivity() {
    private lateinit var editSignUpEmail: EditText
    private lateinit var buttonSendBTN: Button
    private lateinit var buttonBack : Button
    private lateinit var note : TextView
    private lateinit var validator : Validator

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.forget_password_activity)

        editSignUpEmail = findViewById(R.id.editTextSignUpEmail)
        buttonSendBTN = findViewById(R.id.buttonSendBTN)
        buttonBack = findViewById(R.id.buttonBack)
        note = findViewById(R.id.note)
        validator = Validator()


        buttonBack.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)

        }


        buttonSendBTN.setOnClickListener {
            val email = editSignUpEmail.text.toString()
            val emailStatus = validator.checkEmail(email)
            if (email.isEmpty()) {
                note.setText("Email is empty")
            } else if (emailStatus == "valid"){
                // gui thong bao ve email
                Firebase.auth.sendPasswordResetEmail(email)
            } else
                note.setText("Email is $emailStatus")
        }

    }

}