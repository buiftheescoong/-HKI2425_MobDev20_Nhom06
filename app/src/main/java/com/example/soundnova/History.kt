package com.example.soundnova

import android.content.Context
import android.util.Log
import com.example.soundnova.models.Album
import com.example.soundnova.models.Artist
import com.example.soundnova.models.TrackData
import com.example.soundnova.models.Tracks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

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
        audioUrl: String,

    ) {
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email

        val historyCollection = db.collection("history")

        // Lấy số lượng tài liệu hiện có để sinh ID số nguyên mới
        historyCollection.get()
            .addOnSuccessListener { documents ->
                val newId = (documents.size() + 1) // Sinh ID số dựa trên số lượng tài liệu hiện tại

                // Tạo đối tượng SongData với ID mới
                val newSong = SongData(
                    idUser = userEmail,
                    title = title,
                    artist = artist,
                    image = image,
                    audioUrl = audioUrl,
                    id = newId // Gán ID số
                )

                // Thêm tài liệu vào Firestore
                historyCollection.document(newId.toString()) // Dùng ID số làm ID tài liệu
                    .set(newSong)
                    .addOnSuccessListener {
                        Log.d("Song", "DocumentSnapshot added with ID: $newId")

                        // Gọi hàm sắp xếp lại ID sau khi thêm bài hát
                        reorderDocumentIds()
                    }
                    .addOnFailureListener { exception ->
                        Log.w("Song", "Error adding document", exception)
                    }
            }
            .addOnFailureListener { exception ->
                Log.w("Song", "Error fetching documents for ID generation", exception)
            }
    }

    suspend fun fetchHistorySongs(): Tracks = withContext(Dispatchers.IO) {
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email

        if (userEmail == null) {
            Log.e("LibraryFragment", "User is not logged in.")
            return@withContext Tracks() // Trả về danh sách rỗng nếu người dùng chưa đăng nhập
        }

        try {
            // Lấy dữ liệu từ Firestore
            val documents = db.collection("history")
                .orderBy("id", com.google.firebase.firestore.Query.Direction.DESCENDING) // Sắp xếp giảm dần
                .get()
                .await()  // Đợi Firebase trả về kết quả

            val recentSongs = Tracks() // Tạo một đối tượng Tracks mới
            recentSongs.data = mutableListOf() // Reset danh sách

            // Lặp qua các tài liệu và thêm bài hát yêu thích vào danh sách
            for (document in documents) {
                val idUser = document.getString("idUser")
                if (idUser == userEmail) {
                    val song = document.toObject(Song2::class.java)
                    val track = songToTrack(song)
                    recentSongs.data.add(track)

                    Log.d(
                        "HistoryFragment",
                        "Fetched song: ${track.title}, Artist: ${track.artist?.name}"
                    )
                }
            }

            Log.d("HistoryFragment", "Fetched song count: ${recentSongs.data.size}")

            return@withContext recentSongs // Trả về kết quả

        } catch (exception: Exception) {
            Log.e("LibraryFragment", "Error fetching favorite songs: ", exception)
            throw exception // Ném lỗi nếu có
        }
    }

    fun songToTrack(song: Song2): TrackData {
        return TrackData(
            id = 1,
            title = song.title,
            duration = 20000,
            artist = Artist(
                id = 123456789,
                name = song.artist!!.getOrNull(0),
                pictureBig = song.image
            ),
            album = Album(1,"1","1","1","1", Artist(
                id = 123456789,
                name = song.artist!!.getOrNull(0),
                pictureBig = song.image
            ),null),
            preview = song.audioUrl,
            isLiked = true
        )
    }
}