package com.example.soundnova

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Setting: AppCompatActivity() {
    private lateinit var profileImg: Image
    private lateinit var userName: TextView
    private lateinit var userStatus: TextView
    private lateinit var changePass: Button
    private lateinit var logOut: Button
    private lateinit var home: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)

        userName = findViewById(R.id.username)
        userStatus = findViewById(R.id.user_status)
        changePass = findViewById(R.id.btn_change_password)
        logOut = findViewById(R.id.btn_logout)
        home = findViewById(R.id.buttonHome)

        home.setOnClickListener {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        }

        logOut.setOnClickListener{
            val intent = Intent(applicationContext, Login::class.java)
            FirebaseAuth.getInstance().signOut()
            startActivity(intent)
            finish()
        }
        changePass.setOnClickListener {
            val intent = Intent(applicationContext, ChangePassword::class.java)
            startActivity(intent)
            finish()
        }


    }
}