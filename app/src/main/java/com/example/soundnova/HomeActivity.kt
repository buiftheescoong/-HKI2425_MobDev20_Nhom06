package com.example.soundnova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : ComponentActivity() {
    private lateinit var recentRecyclerView: RecyclerView
    private lateinit var favoriteRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        recentRecyclerView = findViewById(R.id.recyclerViewTabsSongs)
        favoriteRecyclerView = findViewById(R.id.recyclerViewFavoriteSongs)
        FirebaseAuth.getInstance().signOut()
        SpotifyService.getAccessToken { token ->
            if (token != null) {
                loadSongs()
            }
        }
    }

    private fun loadSongs() {
        SpotifyService.fetchRecommendations("pop") { songs ->
            runOnUiThread {
                recentRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                recentRecyclerView.adapter = SongAdapter(songs)
            }
        }

        SpotifyService.fetchRecommendations("chill") { songs ->
            runOnUiThread {
                favoriteRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                favoriteRecyclerView.adapter = SongAdapter(songs)
            }
        }
    }
}