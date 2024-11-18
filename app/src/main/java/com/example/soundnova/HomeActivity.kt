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
    private lateinit var recentRecyclerView: RecyclerView
    private lateinit var favoriteRecyclerView: RecyclerView
    private lateinit var btnsetting:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        recentRecyclerView = findViewById(R.id.recyclerViewTabsSongs)
        favoriteRecyclerView = findViewById(R.id.recyclerViewFavoriteSongs)
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
            recentRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recentRecyclerView.adapter = SongAdapter(popularTracks)
        }
        runOnUiThread {
            favoriteRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            favoriteRecyclerView.adapter = SongAdapter(popularTracks)
        }
    }
}