package com.example.soundnova

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.soundnova.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {
//    private lateinit var recentRecyclerView: RecyclerView
//    private lateinit var favoriteRecyclerView: RecyclerView
//    private lateinit var btnsetting:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    //
//        recentRecyclerView = findViewById(R.id.recyclerViewTabsSongs)
//        favoriteRecyclerView = findViewById(R.id.recyclerViewFavoriteSongs)
//        btnsetting = findViewById(R.id.buttonSettings)

//        btnsetting.setOnClickListener {
//            startActivity(Intent(applicationContext, Setting::class.java))
//        }

//        lifecycleScope.launch {
//            loadSongs()
//        }

//        SpotifyService.getAccessToken { token ->
//            if (token != null) {
//                loadSongs()
//            }
//        }
    }

//    private suspend fun loadSongs() {
//        val deezerApiHelper = DeezerApiHelper
//        val popularTracks : Tracks = deezerApiHelper.fetchPopularTracks()
//        Log.d("TAG", "0")
//        runOnUiThread {
//            recentRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            recentRecyclerView.adapter = SongAdapter(popularTracks)
//        }
//        runOnUiThread {
//            favoriteRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            favoriteRecyclerView.adapter = SongAdapter(popularTracks)
//        }
//    }
}