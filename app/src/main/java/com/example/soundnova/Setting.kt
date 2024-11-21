package com.example.soundnova

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import java.io.File
import java.io.FileOutputStream


class Setting: ComponentActivity() {
    private lateinit var profileImg: ImageView
    private lateinit var userName: TextView
    private lateinit var changePass: LinearLayout
    private lateinit var logOut: Button
    private lateinit var home: ImageView
    private lateinit var changeProfile: LinearLayout
    var currentUri: Uri? = null
    private val sharedPreferences by lazy { getSharedPreferences("AppPreferences", MODE_PRIVATE) }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)

        userName = findViewById(R.id.username)
        changePass = findViewById(R.id.change_password_row)
        logOut = findViewById(R.id.buttonLogOut)
        home = findViewById(R.id.buttonHome)
        profileImg = findViewById(R.id.profile_image)
        changeProfile = findViewById(R.id.change_profile_row)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {

            val name = currentUser.displayName
            val imageUri = currentUser.photoUrl
            if (!name.isNullOrEmpty()) {
                userName.text = name
            }
            if (imageUri != null) {
                loadAvatar()
            }
        } else {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show()
        }

        changeProfile.setOnClickListener {
            userName.clearFocus()
            loadAvatar()
            if (currentUser != null) {
                val profileUpdates = userProfileChangeRequest {
                    displayName = userName.text.toString()
                    if (currentUri != null) {
                        photoUri = currentUri
                    }
                }

                currentUser.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            userName.text = currentUser.displayName
                        }
                    }
            }
        }

        profileImg.setOnClickListener {
            openGallery()
        }

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
    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            // Hiển thị ảnh trong ImageView
            saveImageToInternalStorage(uri)
            currentUri = uri
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }
    // Lưu ảnh từ URI vào bộ nhớ cục bộ
    private fun saveImageToInternalStorage(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val file = File(filesDir, "avatar.jpg") // Lưu ảnh với tên "avatar.jpg" trong internal storage
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            Toast.makeText(this, "Avatar updated successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to save avatar", Toast.LENGTH_SHORT).show()
        }
    }

    // Tải ảnh avatar từ bộ nhớ cục bộ và hiển thị
    private fun loadAvatar() {
        val file = File(filesDir, "avatar.jpg")
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            profileImg.setImageBitmap(bitmap)
        } else {
            Toast.makeText(this, "No avatar saved", Toast.LENGTH_SHORT).show()
        }
    }
}