package com.example.soundnova

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soundnova.models.Tracks
import com.example.soundnova.service.DeezerApiHelper
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    private lateinit var recommendRecyclerView: RecyclerView
    private lateinit var recentRecyclerView: RecyclerView
    private lateinit var popularAlbumsView: RecyclerView
    private lateinit var favoriteArtistsView: RecyclerView
    private lateinit var btnsetting: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        recommendRecyclerView = findViewById(R.id.recyclerViewRecommendSongs)
        recentRecyclerView = findViewById(R.id.recyclerViewRecentSongs)
        popularAlbumsView = findViewById(R.id.recyclerViewPopularAlbums)
        favoriteArtistsView = findViewById(R.id.recyclerViewFavoriteArtists)
        btnsetting = findViewById(R.id.buttonSettings)

        btnsetting.setOnClickListener {
            startActivity(Intent(applicationContext, Setting::class.java))
        }

        lifecycleScope.launch {
            loadSongs()
        }

//        SpotifyService.getAccessToken { token ->
//            if (token != null) {
//                loadSongs()
//            }
//        }
    }

    private suspend fun loadSongs() {
        val deezerApiHelper = DeezerApiHelper
        val popularTracks : Tracks = deezerApiHelper.fetchPopularTracks()
        Log.d("TAG", "0")
        runOnUiThread {
            recommendRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recommendRecyclerView.adapter = SongAdapter(popularTracks)
        }
        runOnUiThread {
            recentRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recentRecyclerView.adapter = SongAdapter(popularTracks)
        }
        runOnUiThread {
            popularAlbumsView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            popularAlbumsView.adapter = SongAdapter(popularTracks)
        }
        runOnUiThread {
            favoriteArtistsView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            favoriteArtistsView.adapter = SongAdapter(popularTracks)
        }
    }
}