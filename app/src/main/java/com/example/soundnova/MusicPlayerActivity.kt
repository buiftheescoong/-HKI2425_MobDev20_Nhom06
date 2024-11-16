package com.example.soundnova

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MusicPlayerActivity : ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var  imageViewSong: ImageView
    private lateinit var  textViewArtistName: TextView
    private lateinit var  textViewSongName: TextView
    private lateinit var buttonPlayPause: ImageView
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_music_player)
            Log.d("Debug", "Layout loaded successfully")
        } catch (e: Exception) {
            Log.e("Error", "Layout failed to load: ${e.message}")
            return
        }

        val song = intent.getParcelableExtra<Song>("song")
        val songName = song?.name
        val artistName = song?.artist
        val songImage = song?.imageUrl
        var songUrl :String? = null
        mediaPlayer = MediaPlayer()


        db.collection("songs")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val song = document.toObject(SongData::class.java)
                    if (song.title == "test2") {
                        songUrl = song.audioUrl
                        break
                    }
                }
                if (songUrl != null) {
                    try {
                        mediaPlayer.setDataSource(songUrl)
                        mediaPlayer.prepareAsync()
                    } catch (e: Exception) {
                        Log.e("Error", "Failed to set data source: ${e.message}")
                    }
                } else {
                    Log.e("Error", "Song URL not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Song", "Error getting documents.", exception)
            }


//        val songName = intent.getStringExtra("songName")
//        val artistName = intent.getStringExtra("artistName")
//        val songImage = intent.getStringExtra("songImage")

//        val songName = intent.getStringExtra("songName")
//        val artistName = intent.getStringExtra("artistName")
//        val songImage = intent.getStringExtra("songImage")
//        val songUrl = intent.getStringExtra("songUrl")

        imageViewSong = findViewById(R.id.imageViewSong)
        textViewSongName = findViewById(R.id.textViewSongName)
        textViewArtistName = findViewById(R.id.textViewArtistName)
        buttonPlayPause = findViewById(R.id.buttonPlayPause)

        textViewSongName.text = songName
        textViewArtistName.text = artistName
        Glide.with(this).load(songImage).into(imageViewSong)

//        mediaPlayer = MediaPlayer()
        //mediaPlayer.setDataSource("https://drive.google.com/uc?export=download&id=1RK3xC6iWne5Oe5tS1m7Xhs0DfP20rjvg")


        buttonPlayPause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                buttonPlayPause.setImageResource(R.drawable.play)
            } else {
                mediaPlayer.start()
                buttonPlayPause.setImageResource(R.drawable.pause)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}