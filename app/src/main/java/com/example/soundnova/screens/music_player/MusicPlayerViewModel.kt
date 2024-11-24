package com.example.soundnova.screens.music_player

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundnova.models.TrackData
import com.example.soundnova.models.Tracks

class MusicPlayerViewModel : ViewModel() {
    private lateinit var tracks: Tracks
    private var currentIndex = 0

    private val _currentTrack = MutableLiveData<TrackData>()
    val currentTrack: LiveData<TrackData> get() = _currentTrack

    internal val mediaPlayer = MediaPlayer()

    fun setTrack(tracks: Tracks, index: Int) {
        this.tracks = tracks
        this.currentIndex = index
        playTrack()
    }

    fun togglePlayPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
    }

    fun playNext() {
        currentIndex = (currentIndex + 1) % tracks.data.size
        playTrack()
    }

    fun playPrevious() {
        currentIndex = if (currentIndex == 0) tracks.data.size - 1 else currentIndex - 1
        playTrack()
    }

    private fun playTrack() {
        val track = tracks.data.get(currentIndex)
        _currentTrack.postValue(track)
        mediaPlayer.reset()
        mediaPlayer.setDataSource(track.preview)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }


    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }
}
