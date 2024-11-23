package com.example.soundnova

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soundnova.models.Tracks
import com.example.soundnova.service.DeezerApiHelper
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var recommendRecyclerView: FragmentContainerView
    private lateinit var recentRecyclerView: RecyclerView
    private lateinit var popularAlbumsView: RecyclerView
    private lateinit var favoriteArtistsView: RecyclerView
    private lateinit var btnSetting: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_activity, container, false)

        recommendRecyclerView = view.findViewById(R.id.recyclerViewRecommendSongsContainer)
        recentRecyclerView = view.findViewById(R.id.recyclerViewRecentSongs)
        popularAlbumsView = view.findViewById(R.id.recyclerViewPopularAlbums)
        favoriteArtistsView = view.findViewById(R.id.recyclerViewFavoriteArtists)
        btnSetting = view.findViewById(R.id.buttonSettings)

        btnSetting.setOnClickListener {
            startActivity(Intent(requireContext(), Setting::class.java))
        }

        lifecycleScope.launch {
            loadSongs()
        }

        return view
    }

    private suspend fun loadSongs() {
        val deezerApiHelper = DeezerApiHelper
        val popularTracks: Tracks = deezerApiHelper.fetchPopularTracks()
        Log.d("TAG", "0")

        val recommendSongs = RecyclerViewSongFragment(popularTracks)
        parentFragmentManager.beginTransaction()
            .replace(R.id.recyclerViewRecommendSongsContainer, recommendSongs)
            .commit()



//        // Setup recent songs RecyclerView
//        requireActivity().runOnUiThread {
//            recentRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            recentRecyclerView.adapter = SongAdapter(popularTracks, parentFragmentManager)
//        }
//
//        // Setup popular albums RecyclerView
//        requireActivity().runOnUiThread {
//            popularAlbumsView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            popularAlbumsView.adapter = SongAdapter(popularTracks, parentFragmentManager)
//        }
//
//        // Setup favorite artists RecyclerView
//        requireActivity().runOnUiThread {
//            favoriteArtistsView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            favoriteArtistsView.adapter = SongAdapter(popularTracks, parentFragmentManager)
//        }
    }
}