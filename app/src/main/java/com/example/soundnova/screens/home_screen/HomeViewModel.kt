package com.example.soundnova.screens.home_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundnova.models.Albums
import com.example.soundnova.models.Artists
import com.example.soundnova.models.TrackData
import com.example.soundnova.models.Tracks
import com.example.soundnova.service.DeezerApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//class HomeViewModel : ViewModel() {
////    var tracks by mutableStateOf<Tracks?>(null)
////    var isLoading by mutableStateOf(true)
////
////    fun fetchPopularTracks() {
////        viewModelScope.launch {
////            try {
////                isLoading = true
////                tracks = DeezerApiHelper.fetchPopularTracks()
////                Log.d("HomeViewModel", "Fetched popular tracks: $tracks")
////            } catch (e: Exception) {
////                e.printStackTrace() // Xử lý lỗi
////            } finally {
////                isLoading = false
////            }
////        }
////    }
//}

class HomeViewModel : ViewModel() {
    private val _tracks = MutableStateFlow<Tracks?>(null)
    private val _albums = MutableStateFlow<Albums?>(null)
    private val _artists = MutableStateFlow<Artists?>(null)
    val tracks: StateFlow<Tracks?> = _tracks
    val albums: StateFlow<Albums?> = _albums
    val artists: StateFlow<Artists?> = _artists

    fun fetchPopularTracks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val popularTracks = DeezerApiHelper.fetchPopularTracks()
                _tracks.value = popularTracks
            } catch (e: Exception) {
                e.printStackTrace() // Handle error
            }
        }
    }

    fun fetchPopularAlbums() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val popularAlbums = DeezerApiHelper.fetchPopularAlbums()
                _albums.value = popularAlbums
            } catch (e: Exception) {
                e.printStackTrace() // Handle error
            }
        }
    }

    fun fetchPopularArtists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val popularArtists = DeezerApiHelper.fetchPopularArtists()
                _artists.value = popularArtists
            } catch (e: Exception) {
                e.printStackTrace() // Handle error
            }
        }
    }

}

@SuppressLint("SuspiciousIndentation")
fun main() {

  runBlocking {
    val viewModel = HomeViewModel()

    // Launch the fetch operation in a coroutine

      launch {
        viewModel.fetchPopularTracks()
    }

    // Wait for the tracks to be fetched by observing the flow and then print the result
    // Collect the tracks and print when available
     viewModel.tracks.collect { popularTracks ->
        if (popularTracks != null) {
            println("Fetched popular tracks: $popularTracks")
            // You can stop the collection once you have fetched the tracks
//            return@collect
        } else {
            println("Tracks are still loading or not available.")
        }
    }

}}


