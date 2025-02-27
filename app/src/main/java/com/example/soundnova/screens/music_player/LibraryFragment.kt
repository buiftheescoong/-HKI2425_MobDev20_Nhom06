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
import com.example.soundnova.HomeActivity
import com.example.soundnova.R
import com.example.soundnova.SongData
import com.example.soundnova.databinding.LibraryBinding
import com.example.soundnova.models.Album
import com.example.soundnova.models.Artist
import com.example.soundnova.models.TrackData
import com.example.soundnova.models.Tracks
import com.example.soundnova.screens.adapters.OnItemClickTrackListener
import com.example.soundnova.screens.adapters.SongAdapter
import com.example.soundnova.service.DeezerApiHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LibraryFragment : Fragment() {

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

    private suspend fun fetchFavoriteSongs(): Tracks = withContext(Dispatchers.IO) {
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email

        if (userEmail == null) {
            Log.e("LibraryFragment", "User is not logged in.")
            return@withContext Tracks()
        }

        try {
            val documents = db.collection("favorite_library")
                .get()
                .await()

            val favoriteSongs = Tracks()
            favoriteSongs.data = mutableListOf()

            for (document in documents) {
                val idUser = document.getString("idUser")
                if (idUser == userEmail) {
                    val song = document.toObject(SongData::class.java)
                    val track = songToTrack(song)
                    favoriteSongs.data.add(track!!)

                    Log.d(
                        "LibraryFragment",
                        "Fetched song: ${track.title}, Artist: ${track.artist?.name}"
                    )
                }
            }

            Log.d("LibraryFragment", "Fetched song count: ${favoriteSongs.data.size}")

            return@withContext favoriteSongs

        } catch (exception: Exception) {
            Log.e("LibraryFragment", "Error fetching favorite songs: ", exception)
            throw exception
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val tracks = fetchFavoriteSongs()
            Log.d("LibraryFragment1", "Fetched favorite tracks: ${tracks.data.size}")

            binding.libraryRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            adapter = SongAdapter(tracks, object : OnItemClickTrackListener {
                override fun onItemClick(position: Int, tracks: Tracks) {
                    val bundle = Bundle().apply {
                        putParcelable("tracks", tracks)
                        putInt("position", position)
                    }
                    (activity as? HomeActivity)?.handleMusicBottomBar(bundle)
                }
            }, 1)

            binding.libraryRecyclerView.adapter = adapter
        }
    }



//    fun songToTrack(song: Song2): TrackData {
//        return TrackData(
//            id = 1,
//            title = song.title,
//            duration = 20000,
//            artist = Artist(
//                id = 123456789,
//                name = song.artist!!.getOrNull(0),
//                pictureBig = song.image
//            ),
//            album = Album(1,"1","1","1","1",Artist(
//                id = 123456789,
//                name = song.artist!!.getOrNull(0),
//                pictureBig = song.image
//            ),null),
//            preview = song.audioUrl,
//            isLiked = true
//        )
//    }
    suspend fun songToTrack(song: SongData): TrackData? {
        val trackId = song.idSong?.toString() ?: return null

        return try {
            val trackData = DeezerApiHelper.getTrack(trackId)

            TrackData(
                id = trackData.id,
                title = trackData.title,
                duration = trackData.duration ?: 0,
                artist = trackData.artist ?: null,
                album = trackData.album ?: null,
                preview = trackData.preview ?: null,
                isLiked = false
            )
        } catch (e: Exception) {
            Log.e("songToTrack", "Error fetching track details: ${e.message}", e)
            null
        }
    }

}
