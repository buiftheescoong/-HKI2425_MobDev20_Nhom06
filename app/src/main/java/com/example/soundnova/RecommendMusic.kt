package com.example.soundnova

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecommendMusic : ComponentActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db.collection("history")
            .get()
            .addOnSuccessListener { documents ->

                val artistCount = mutableMapOf<String, Int>()
                val genreCount = mutableMapOf<String, Int>()

                for (document in documents) {
                    val song = document.toObject(SongData::class.java)
                    /// check artist và genre nhieu nhat
                    val currentUser = firebaseAuth.currentUser
                    val userEmail = currentUser?.email

                    if (userEmail == song.idUser) {
                        song.artist?.forEach { artist ->
                            artistCount[artist] = artistCount.getOrDefault(artist, 0) + 1
                        }

                        song.genre?.let { genre ->
                            genreCount[genre] = genreCount.getOrDefault(genre, 0) + 1
                        }
                    }
                }

                // Sắp xếp nghệ sĩ và thể loại theo số lần nghe giảm dần
                val sortedArtists = artistCount.entries.sortedByDescending { it.value }
                val sortedGenres = genreCount.entries.sortedByDescending { it.value }

                // Lấy nghệ sĩ phổ biến nhất và thứ hai
                val mostPopularArtist = sortedArtists.getOrNull(0)
                val secondPopularArtist = sortedArtists.getOrNull(1)

                // Lấy thể loại phổ biến nhất và thứ hai
                val mostPopularGenre = sortedGenres.getOrNull(0)
                val secondPopularGenre = sortedGenres.getOrNull(1)

                Log.d(
                    "RecommentMusic",
                    "Most Popular Artist: ${mostPopularArtist?.key} with ${mostPopularArtist?.value} listens"
                )
                Log.d(
                    "RecommentMusic",
                    "Second Popular Artist: ${secondPopularArtist?.key} with ${secondPopularArtist?.value} listens"
                )
                Log.d(
                    "RecommentMusic",
                    "Second Popular Genre: ${mostPopularGenre?.key} with ${mostPopularGenre?.value} listens"
                )

                Log.d(
                    "RecommentMusic",
                    "Second Popular Genre: ${secondPopularGenre?.key} with ${secondPopularGenre?.value} listens"
                )
            }
            .addOnFailureListener { exception ->
                Log.w("Song", "Error getting documents.", exception)
            }
    }
}