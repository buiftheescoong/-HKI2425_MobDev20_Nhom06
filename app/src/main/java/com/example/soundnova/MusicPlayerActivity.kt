
package com.example.soundnova

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide

class MusicPlayerActivity : ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var  imageViewSong: ImageView
    private lateinit var  textViewArtistName: TextView
    private lateinit var  textViewSongName: TextView
    private lateinit var buttonPlayPause: ImageView

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

        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource("https://drive.google.com/uc?export=download&id=1RK3xC6iWne5Oe5tS1m7Xhs0DfP20rjvg")
        mediaPlayer.prepare()

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
