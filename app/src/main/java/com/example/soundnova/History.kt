package com.example.soundnova

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class History(private val context: Context) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    fun reorderDocumentIds() {
        val historyCollection = db.collection("history")

        historyCollection.get()
            .addOnSuccessListener { documents ->
                val sortedDocuments = documents.sortedBy { it.id }
                var count = 1

                for (document in sortedDocuments) {
                    val data = document.data

                    // Tạo tài liệu mới với ID là số thứ tự
                    historyCollection.document(count.toString())
                        .set(data)
                        .addOnSuccessListener {
                            Log.d("Firestore", "Tài liệu mới với ID: $count được tạo thành công")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Lỗi khi tạo tài liệu mới với ID: $count", e)
                        }

                    count++
                }

                // Xóa tất cả các tài liệu cũ (ID không phải là số)
                for (document in documents) {
                    val documentId = document.id
                    if (!documentId.matches(Regex("\\d+"))) { // Kiểm tra nếu ID không phải số
                        historyCollection.document(documentId).delete()
                            .addOnSuccessListener {
                                Log.d("Firestore", "Tài liệu cũ với ID: $documentId đã được xóa")
                            }
                            .addOnFailureListener { e ->
                                Log.w("Firestore", "Lỗi khi xóa tài liệu cũ với ID: $documentId", e)
                            }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Lỗi khi lấy dữ liệu", exception)
            }
    }

    fun addHistorySong(
        title: String,
        artist: List<String>,
        image: String,
        duration: Int,
        audioUrl: String,
//        album: String,
        genre: String
    ) {
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email
        val newSong = SongData(
            idUser = userEmail,
            title = title,
            artist = artist,
            duration = duration,
            image = image,
//            album = album,
            audioUrl = audioUrl,
            genre = genre
        )
        db.collection("history")
            .add(newSong)
            .addOnSuccessListener { documentReference ->
                Log.d("Song", "DocumentSnapshot added with ID: ${documentReference.id}")
                    // Gọi hàm sắp xếp lại ID sau khi thêm bài hát
                    reorderDocumentIds()
                }
                .addOnFailureListener { exception ->
                    Log.w("Song", "Error adding document", exception)
                }
    }
}



