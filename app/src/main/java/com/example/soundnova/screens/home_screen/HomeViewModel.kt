//package com.example.soundnova.screens.home_screen
//
//import android.util.Log
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.soundnova.models.TrackData
//import com.example.soundnova.models.Tracks
//import com.example.soundnova.service.DeezerApiHelper
//import kotlinx.coroutines.launch
//
////class HomeViewModel : ViewModel() {
//////    var tracks by mutableStateOf<Tracks?>(null)
//////    var isLoading by mutableStateOf(true)
//////
//////    fun fetchPopularTracks() {
//////        viewModelScope.launch {
//////            try {
//////                isLoading = true
//////                tracks = DeezerApiHelper.fetchPopularTracks()
//////                Log.d("HomeViewModel", "Fetched popular tracks: $tracks")
//////            } catch (e: Exception) {
//////                e.printStackTrace() // Xử lý lỗi
//////            } finally {
//////                isLoading = false
//////            }
//////        }
//////    }
////}
//
//class HomeViewModel : ViewModel() {
//
//    private val _tracks = MutableLiveData<Tracks?>(null)
//    val tracks: LiveData<Tracks?> = _tracks
//
//    fun fetchPopularTracks() {
//        viewModelScope.launch {
//            try {
//                val fetchedTracks = DeezerApiHelper.fetchPopularTracks()
//                _tracks.value = fetchedTracks // Gán giá trị cho LiveData
//            } catch (e: Exception) {
//                e.printStackTrace() // Xử lý lỗi
//            }
//        }
//    }
//}
