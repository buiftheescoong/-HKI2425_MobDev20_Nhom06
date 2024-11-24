package com.example.soundnova

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlin.time.Duration

class MusicData : ComponentActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
    }

}


data class SongData(
    val idUser : String? = null,
    val title: String? = null,
    val artist: List<String>? = null,
    val image: String? = null,
    val duration: Int? = null,
    val album: String? = null,
    val audioUrl: String? = null,
    val genre: String? = null,
    )


