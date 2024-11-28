package com.example.soundnova

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soundnova.databinding.LibraryTestBinding
import com.example.soundnova.models.TrackData
import com.example.soundnova.screens.adapters.OnItemClickSong2Listener
import com.example.soundnova.screens.adapters.SongAdapter2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LibraryFragment : Fragment() {

    private val favoriteSongs = mutableListOf<Song2>() // Danh sách bài hát yêu thích
    private lateinit var binding: LibraryTestBinding
    private lateinit var adapter: SongAdapter2 // Sử dụng adapter mới
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = LibraryTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun fetchFavoriteSongs() {
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email

        if (userEmail == null) {
            Log.e("LibraryFragment", "User is not logged in.")
            return
        }

        db.collection("favorite_library")
            .get()
            .addOnSuccessListener { documents ->
                favoriteSongs.clear() // Xóa danh sách cũ trước khi thêm dữ liệu mới
                for (document in documents) {
                    val idUser = document.getString("idUser")
                    if (idUser == userEmail) {
                        val song = document.toObject(Song2::class.java)
                        favoriteSongs.add(song)
                        Log.d("LibraryFragment", "Fetched song: ${song.title}, by ${song.artist}")
                    }
                }
                adapter.notifyDataSetChanged() // Thông báo adapter cập nhật dữ liệu
            }
            .addOnFailureListener { exception ->
                Log.e("LibraryFragment", "Error fetching favorite songs: ", exception)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SongAdapter2(favoriteSongs, object : OnItemClickSong2Listener {
            override fun onItemClick(position: Int, song: Song2) {
                // Xử lý khi người dùng click vào một bài hát
                Log.d("LibraryFragment", "Clicked song: ${song.title}")
            }
        }, viewType = 1) // hoặc viewType = 0 tùy theo layout bạn muốn sử dụng
        binding.recyclerviewtest.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewtest.adapter = adapter

        // Lấy danh sách bài hát yêu thích từ Firestore
        fetchFavoriteSongs()
    }


//    fun songToTrack(song: Song2) : TrackData {
//        var track: TrackData
//        track.id = null
//        track.title = song.title
//        track.
//        return track
//    }
}
