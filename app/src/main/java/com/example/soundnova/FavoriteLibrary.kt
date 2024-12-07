package com.example.soundnova

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FavoriteLibrary(private val context: Context) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    fun reorderDocumentIds() {
        val favCollection = db.collection("favorite_library")

        favCollection.get()
            .addOnSuccessListener { documents ->
                val sortedDocuments = documents.sortedBy { it.id }
                var count = 1

                for (document in sortedDocuments) {
                    val data = document.data

                    // Tạo tài liệu mới với ID là số thứ tự
                    favCollection.document(count.toString())
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
                        favCollection.document(documentId).delete()
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

    fun addFavSong(
        title: String,
        artist: List<String>,
        image: String,
        audioUrl: String,
    ) {
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email
        val newSong = SongData(
            idUser = userEmail,
            title = title,
            artist = artist,
            image = image,
            audioUrl = audioUrl,
        )
        db.collection("favorite_library")
            .add(newSong)
            .addOnSuccessListener { documentReference ->
                Log.d("Song", "DocumentSnapshot added with ID: ${documentReference.id}")
                reorderDocumentIds()
            }
            .addOnFailureListener { exception ->
                Log.w("Song", "Error adding document", exception)
            }
    }

    fun removeFavSong(
        title: String,
    ) {
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email
        val newSong = SongData(
            idUser = userEmail,
            title = title,
        )
        db.collection("favorite_library")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val documentTitle =
                        document.getString("title") // Lấy giá trị "title" từ tài liệu
                    val documentUserEmail =
                        document.getString("idUser") // Lấy giá trị "idUser" từ tài liệu

                    if (documentTitle == newSong.title && documentUserEmail == newSong.idUser) {
                        val documentId = document.id
                        db.collection("favorite_library").document(documentId).delete()
                            .addOnSuccessListener {
                                Log.d("Firestore", "Tài liệu cũ với ID: $documentId đã được xóa")
                            }
                            .addOnFailureListener { e ->
                                Log.w("Firestore", "Lỗi khi xóa tài liệu cũ với ID: $documentId", e)
                            }
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Lỗi khi lấy dữ liệu: ", e)
            }

    }

    fun checkFavSong(
        title: String,
        callback: (Boolean) -> Unit
    ) {
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email
        val newSong = SongData(
            idUser = userEmail,
            title = title,
        )

        db.collection("favorite_library")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val documentTitle = document.getString("title")
                    val documentUserEmail = document.getString("idUser")

                    if (documentTitle == newSong.title && documentUserEmail == newSong.idUser) {
                        callback(true)
                        return@addOnSuccessListener
                    }
                }
                callback(false)
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Lỗi khi lấy dữ liệu: ", e)
                callback(false)
            }
    }

}

