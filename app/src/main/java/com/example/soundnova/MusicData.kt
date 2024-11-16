package com.example.soundnova

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class MusicData : ComponentActivity() {
    private lateinit var title: String
    private lateinit var album : String
    private lateinit var artist: String
    private lateinit var audioUrl: String
    private lateinit var duration: String
    private lateinit var information : String
    private lateinit var genre: String
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db.collection("songs")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val song = document.toObject(SongData::class.java)
                    // Sử dụng thông tin của bài hát, ví dụ:
                    Log.d("Song", "${song.title} - ${song.artist}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Song", "Error getting documents.", exception)
            }
        val newSong = SongData("test2", "Ca sĩ mới", "album" ,"https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", "180s","Pop","thongtinbaihat","2024" )
        db.collection("songs")
            .add(newSong)
            .addOnSuccessListener { documentReference ->
                Log.d("Song", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { exception ->
                Log.w("Song", "Error adding document", exception)
            }
    }

}


data class SongData(
    val title: String? = null,
    val artist: String? = null,
    val album: String? = null,
    val audioUrl: String? = null,
    val duration: String? = null,
    val genre: String? = null,
    val infomation: String? = null,
    val releaseDate: String? = null,

    )


