package com.example.soundnova

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        loadSongs()
//        SpotifyService.getAccessToken { token ->
//            if (token != null) {
//                loadSongs()
//            }
//        }
    }

    private fun loadSongs() {
//        SpotifyService.fetchRecommendations("pop") { songs ->
//            runOnUiThread {
//                recentRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//                recentRecyclerView.adapter = SongAdapter(songs)
//            }
//        }
//
//        SpotifyService.fetchRecommendations("chill") { songs ->
//            runOnUiThread {
//                favoriteRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//                favoriteRecyclerView.adapter = SongAdapter(songs)
//            }
//        }

        runOnUiThread {
            recentRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recentRecyclerView.adapter = SongAdapter(sampleSongList)
        }
        runOnUiThread {
            favoriteRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            favoriteRecyclerView.adapter = SongAdapter(sampleSongList)
        }
    }
}