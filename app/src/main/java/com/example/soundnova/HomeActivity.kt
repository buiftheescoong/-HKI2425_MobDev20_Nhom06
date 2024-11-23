package com.example.soundnova

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soundnova.models.Tracks
import com.example.soundnova.service.DeezerApiHelper
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
//    private lateinit var recommendRecyclerView: FragmentContainerView
    private lateinit var recentRecyclerView: RecyclerView
    private lateinit var popularAlbumsView: RecyclerView
    private lateinit var favoriteArtistsView: RecyclerView
    private lateinit var btnsetting: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

//        recommendRecyclerView = findViewById(R.id.recyclerViewRecommendSongs)
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
//        runOnUiThread {
//            val recommendSongsFragment = RecyclerViewSongFragment(popularTracks)
//
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.recyclerViewRecommendSongsContainer, recommendSongsFragment)
//                .commit()
//        }
//        runOnUiThread {
//            recentRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            recentRecyclerView.adapter = SongAdapter(popularTracks, supportFragmentManager)
//        }
//        runOnUiThread {
//            popularAlbumsView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            popularAlbumsView.adapter = SongAdapter(popularTracks, supportFragmentManager)
//        }
//        runOnUiThread {
//            favoriteArtistsView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            favoriteArtistsView.adapter = SongAdapter(popularTracks, supportFragmentManager)
//        }
    }
}