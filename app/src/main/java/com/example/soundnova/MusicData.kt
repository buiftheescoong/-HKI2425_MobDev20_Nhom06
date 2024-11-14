package com.example.soundnova

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MusicData : ComponentActivity() {

    private lateinit var musicTitle : String
    private lateinit var musicAlbum : String
    private lateinit var musicArtist : String
    private lateinit var musicAudioUrl : String
    private lateinit var musicDuration : String
    private lateinit var musicInformation : String
    private lateinit var musicGenre : String

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    fun getmusicName() : String {
        return musicTitle
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        if(title.isNotEmpty()) {
            firestore.collection("songs")
                .whereEqualTo("$title", musicTitle)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        // Lấy từng trường dữ liệu từ Firestore document
                        musicTitle = document.getString("title") ?: "Unknown Title"
                        musicAlbum = document.getString("album") ?: "Unknown Album"
                        musicArtist = document.getString("artist") ?: "Unknown Artist"
                        musicAudioUrl = document.getString("audioUrl") ?: "Unknown Audio URL"
                        musicDuration = document.get("duration")?.toString() ?: "Unknown Duration"
                        musicInformation = document.getString("information") ?: "No Information"
                        musicGenre = document.getString("genre") ?: "Unknown Genre"
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("MusicData", "Error getting documents: ", exception)
                }
        }
    }
}
