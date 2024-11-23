package com.example.soundnova

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Tag", "-1")
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            startActivity(Intent(this, Login::class.java))
            Log.d("Tag", "0")
            finish()
        } else {
//            startActivity(Intent(this, HomeActivity::class.java))
//            Log.d("Tag", "0")
//            finish()

            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, HomeFragment())
                .commit()
        }
    }
}