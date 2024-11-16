package com.example.soundnova

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.soundnova.data.local.room.Convert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MusicPlayerActivity : ComponentActivity() {

    companion object {
        var shuffleBoolean: Boolean = false
        var repeatBoolean: Boolean = false
        var heartBoolean: Boolean = false
    }

    private var currentSongIndex = 0
    val convert = Convert()
    private var seekBarUpdateJob: Job? = null

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var imageViewSong: ImageView
    private lateinit var textViewArtistName: TextView
    private lateinit var textViewSongName: TextView
    private lateinit var buttonPlayPause: ImageButton
    private lateinit var backBtn : ImageView
    private lateinit var moreBtn : ImageView
    private lateinit var heartBtn : ImageView
    private lateinit var durationPlayed: TextView
    private lateinit var durationTotal: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var shuffleBtn: ImageView
    private lateinit var prevBtn: ImageView
    private lateinit var nextBtn: ImageView
    private lateinit var repeatBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)

//        val songName = intent.getStringExtra("songName")
//        val artistName = intent.getStringExtra("artistName")
//        val songImage = intent.getStringExtra("songImage")
//        val songUrl = intent.getStringExtra("songUrl")

        imageViewSong = findViewById(R.id.cover_art)
        textViewSongName = findViewById(R.id.song_name)
        textViewArtistName = findViewById(R.id.song_artist)
        buttonPlayPause = findViewById(R.id.play_pause)
        backBtn = findViewById(R.id.back_btn)
        moreBtn = findViewById(R.id.more_btn)
        heartBtn = findViewById(R.id.heart_btn)
        durationPlayed = findViewById(R.id.durationPlayed)
        durationTotal = findViewById(R.id.durationTotal)
        seekBar = findViewById(R.id.seekBar)
        shuffleBtn = findViewById(R.id.id_shuffle)
        prevBtn = findViewById(R.id.id_prev)
        nextBtn = findViewById(R.id.id_next)
        repeatBtn = findViewById(R.id.id_repeat)


        mediaPlayer = MediaPlayer()

        playSong(currentSongIndex)

        heartBtn.setOnClickListener {
            heartBoolean = !heartBoolean
            val heartIcon = if (heartBoolean) {
                R.drawable.icon_heart_on
            } else {
                R.drawable.icon_heart
            }
            heartBtn.setImageResource(heartIcon)
        }

        buttonPlayPause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                buttonPlayPause.setImageResource(R.drawable.icon_play)
                stopSeekBarUpdate()
            } else {
                mediaPlayer.start()
                buttonPlayPause.setImageResource(R.drawable.icon_pause)
                startSeekBarUpdate()
            }
        }

        shuffleBtn.setOnClickListener {
            shuffleBoolean = !shuffleBoolean
            val shuffleIcon = if (shuffleBoolean) {
                R.drawable.icon_shuffle_on
            } else {
                R.drawable.icon_shuffle_off
            }
            shuffleBtn.setImageResource(shuffleIcon)
        }

        repeatBtn.setOnClickListener {
            repeatBoolean = !repeatBoolean
            val repeatIcon = if (repeatBoolean) {
                R.drawable.icon_repeat_on
            } else {
                R.drawable.icon_repeat
            }
            repeatBtn.setImageResource(repeatIcon)
        }

        mediaPlayer.setOnCompletionListener {
            if (repeatBoolean) {
                mediaPlayer.seekTo(0)
                mediaPlayer.start()
            } else {
                playNextSong()
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                    buttonPlayPause.setImageResource(R.drawable.icon_pause)
                }
            }
        }

        nextBtn.setOnClickListener {
            playNextSong()
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                buttonPlayPause.setImageResource(R.drawable.icon_pause)
            }
        }

        prevBtn.setOnClickListener {
            playPreviousSong()
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                buttonPlayPause.setImageResource(R.drawable.icon_pause)
            }
        }

        backBtn.setOnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun playNextSong() {
        if (!shuffleBoolean) {
            currentSongIndex = (0 until sampleSongList.size).random()
        } else {
            currentSongIndex = (currentSongIndex + 1) % sampleSongList.size
        }
        playSong(currentSongIndex)
    }

    private fun playPreviousSong() {
        currentSongIndex = if (currentSongIndex == 0) {
            sampleSongList.size - 1
        } else {
            currentSongIndex - 1
        }
        playSong(currentSongIndex)
    }

    private fun playSong(index: Int) {
        val song = sampleSongList[index]

        textViewSongName.text = song.name
        val songArtistsOfString = convert.fromListOfString(song.artists)
        textViewArtistName.text = songArtistsOfString
        Glide.with(this).load(song.imageUrl).into(imageViewSong)

        seekBar.max = song.duration
        seekBar.progress = 0

        durationPlayed.text = formatDuration(0)
        durationTotal.text = formatDuration(song.duration)

        mediaPlayer.reset()
        mediaPlayer.setDataSource(song.musicUrl)
        mediaPlayer.prepare()
    }

    private fun startSeekBarUpdate() {
        seekBarUpdateJob = CoroutineScope(Dispatchers.Main).launch {
            while (mediaPlayer.isPlaying) {
                val currentPosition = mediaPlayer.currentPosition
                seekBar.progress = currentPosition
                durationPlayed.text = formatDuration(currentPosition)
                delay(1000L)
            }
        }
    }

    private fun stopSeekBarUpdate() {
        seekBarUpdateJob?.cancel()
        seekBarUpdateJob = null
    }

    @SuppressLint("DefaultLocale")
    private fun formatDuration(duration: Int): String {
        val minutes = duration / 1000 / 60
        val seconds = duration / 1000 % 60
        return String.format("%2d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
