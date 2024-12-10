//package com.example.soundnova
//
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.ComponentActivity
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
//
//class RecommendMusic : ComponentActivity() {
//    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val db = Firebase.firestore
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        db.collection("history")
//            .get()
//            .addOnSuccessListener { documents ->
//
//                val artistCount = mutableMapOf<String, Int>()
//                val genreCount = mutableMapOf<String, Int>()
//
//                for (document in documents) {
//                    val song = document.toObject(SongData::class.java)
//                    /// check artist và genre nhieu nhat
//                    val currentUser = firebaseAuth.currentUser
//                    val userEmail = currentUser?.email
//
//                    if (userEmail == song.idUser) {
//                        song.artist?.forEach { artist ->
//                            artistCount[artist] = artistCount.getOrDefault(artist, 0) + 1
//                        }
//                    }
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("Song", "Error getting documents.", exception)
//            }
//    }
//}