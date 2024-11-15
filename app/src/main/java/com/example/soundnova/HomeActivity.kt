package com.example.soundnova

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : ComponentActivity() {

    private lateinit var editTextSearch: EditText
    private lateinit var tabRecent: TextView
    private lateinit var tabTop50 : TextView
    private lateinit var tabChill: TextView
    private lateinit var tabRnB : TextView
    private lateinit var tabFestival: TextView

    private lateinit var recentRecyclerView: RecyclerView
    private lateinit var favoriteRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)


        editTextSearch = findViewById(R.id.editTextSearch)
        tabRecent = findViewById(R.id.tabRecent)
        tabTop50 = findViewById(R.id.tabTop50)
        tabChill = findViewById(R.id.tabChill)
        tabFestival = findViewById(R.id.tabFestival)
        tabRnB = findViewById(R.id.tabRnB)

        recentRecyclerView = findViewById(R.id.recyclerViewTabsSongs)
        favoriteRecyclerView = findViewById(R.id.recyclerViewFavoriteSongs)

        SpotifyService.getAccessToken { token ->
            if (token != null) {
                loadSongs()
            }
        }
    }

    fun loadSongs() {
        SpotifyService.fetchRecommendations("pop") { songs ->
            runOnUiThread {
                recentRecyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                recentRecyclerView.adapter = SongAdapter(songs)
            }
        }

        SpotifyService.fetchRecommendations("chill") { songs ->
            runOnUiThread {
                favoriteRecyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                favoriteRecyclerView.adapter = SongAdapter(songs)
            }
        }
    }
}
