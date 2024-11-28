package com.example.soundnova.screens.music_player

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soundnova.R
import com.example.soundnova.Song2
import com.example.soundnova.databinding.LibraryBinding
import com.example.soundnova.models.Album
import com.example.soundnova.models.Artist
import com.example.soundnova.models.TrackData
import com.example.soundnova.models.Tracks
import com.example.soundnova.screens.adapters.OnItemClickSong2Listener
import com.example.soundnova.screens.adapters.OnItemClickTrackListener
import com.example.soundnova.screens.adapters.SongAdapter
import com.example.soundnova.screens.adapters.SongAdapter2
import com.example.soundnova.service.DeezerApiHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LibraryFragment : Fragment() {

    private var favoriteSongs: Tracks = Tracks(mutableListOf()) // Khởi tạo danh sách trống
    private lateinit var binding: LibraryBinding
    private lateinit var adapter: SongAdapter // Sử dụng adapter mới
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = LibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun fetchFavoriteSongs() : Tracks {
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email

        if (userEmail == null) {
            Log.e("LibraryFragment", "User is not logged in.")
        }

        db.collection("favorite_library")
            .get()
            .addOnSuccessListener { documents ->
                favoriteSongs.data = mutableListOf() // Reset danh sách trước khi thêm dữ liệu mới
                for (document in documents) {
                    val idUser = document.getString("idUser")
                    if (idUser == userEmail) {
                        val song = document.toObject(Song2::class.java)
                        val track = songToTrack(song)
                        favoriteSongs.data.add(track)

                        Log.d(
                            "LibraryFragment",
                            "Fetched song: ${track.title}, Artist: ${track.artist?.name}"
                        )

                    }
                }
                Log.d(
                    "LibraryFragment",
                    "Fetched song: ${favoriteSongs.data.size}"
                )

                adapter.notifyDataSetChanged()

            }
            .addOnFailureListener { exception ->
                Log.e("LibraryFragment", "Error fetching favorite songs: ", exception)
            }
        return favoriteSongs
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = LibraryBinding.bind(view)

        // Lấy danh sách bài hát yêu thích từ Firestore
        lifecycleScope.launch {
            val track = fetchFavoriteSongs() // Gọi hàm để lấy dữ liệu
            Log.d("LibraryFragment1", "Fetched song: ${track.data.size}")
            binding.libraryRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = SongAdapter(track, object : OnItemClickTrackListener {
                override fun onItemClick(position: Int, tracks: Tracks) {
                    findNavController().navigate(
                        R.id.action_lib_to_music,
                        Bundle().apply {

                            putParcelable("tracks", tracks)
                            putInt("position", position)
                        }
                    )
                    Log.d("LibraryFragment","da an")
                }
            }, 1)


            binding.libraryRecyclerView.adapter = adapter

        }
    }


    fun songToTrack(song: Song2): TrackData {
            return TrackData(
                id = null, // Lấy id nếu có
                title = song.title,
                duration = null,
                artist = Artist(
                    id = null,
                    name = song.artist?.getOrNull(0) ?: "Unknown",
                    pictureBig = song.image
                ),
                album = null,
                preview = song.audioUrl
                    ?: "No preview available",
                isLiked = false
            )
        }

}
