package com.example.soundnova

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.soundnova.models.Tracks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MusicPlayerFragment : Fragment() {

    companion object {
        var shuffleBoolean: Boolean = false
        var repeatBoolean: Boolean = false
        var heartBoolean: Boolean = false
        var currentSongLyrics = ""
        var currentPreLyricsColor: Int = 0
    }

    private var currentSongIndex = 0
    private var seekBarUpdateJob: Job? = null
    private var isRotating = false

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var imageViewSong: ImageView
    private lateinit var textViewArtistName: TextView
    private lateinit var textViewSongName: TextView
    private lateinit var buttonPlayPause: ImageButton
    private lateinit var backBtn: ImageView
    private lateinit var moreBtn: ImageView
    private lateinit var heartBtn: ImageView
    private lateinit var durationPlayed: TextView
    private lateinit var durationTotal: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var shuffleBtn: ImageView
    private lateinit var prevBtn: ImageView
    private lateinit var nextBtn: ImageView
    private lateinit var repeatBtn: ImageView
    private lateinit var previewLyrics: TextView
    private lateinit var showFullLyricsBtn: Button
    private lateinit var layoutPreviewLyrics: ConstraintLayout
    private lateinit var rotationAnimator: ObjectAnimator
    private lateinit var tracks: Tracks

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.player_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageViewSong = view.findViewById(R.id.cover_art)
        textViewSongName = view.findViewById(R.id.song_name)
        textViewArtistName = view.findViewById(R.id.song_artist)
        buttonPlayPause = view.findViewById(R.id.play_pause)
        backBtn = view.findViewById(R.id.back_btn)
        moreBtn = view.findViewById(R.id.more_btn)
        heartBtn = view.findViewById(R.id.heart_btn)
        durationPlayed = view.findViewById(R.id.durationPlayed)
        durationTotal = view.findViewById(R.id.durationTotal)
        seekBar = view.findViewById(R.id.seekBar)
        shuffleBtn = view.findViewById(R.id.id_shuffle)
        prevBtn = view.findViewById(R.id.id_prev)
        nextBtn = view.findViewById(R.id.id_next)
        repeatBtn = view.findViewById(R.id.id_repeat)
        previewLyrics = view.findViewById(R.id.preview_lyrics)
        showFullLyricsBtn = view.findViewById(R.id.show_full_lyrics_button)
        layoutPreviewLyrics = view.findViewById(R.id.preview_layout)

        mediaPlayer = MediaPlayer()

        rotationAnimator = ObjectAnimator.ofFloat(imageViewSong, "rotation", 0f, 360f).apply {
            duration = 30000L
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
        }

        try {
            tracks = requireArguments().getParcelable("tracks")!!
            currentSongIndex = requireArguments().getInt("index", 0)
            playSong(currentSongIndex)
            Log.d("MusicPlayer", "Received position: $currentSongIndex")
        } catch (e: Exception) {
            Log.e("MusicPlayer", "Error receiving data from intent: ${e.message}")
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    durationPlayed.text = formatDuration(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                stopSeekBarUpdate()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    mediaPlayer.seekTo(it.progress)
                    startSeekBarUpdate()
                }
            }
        })

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
                stopRotationAnimator()
            } else {
                mediaPlayer.start()
                buttonPlayPause.setImageResource(R.drawable.icon_pause)
                startSeekBarUpdate()
                startRotationAnimator()
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
                startSeekBarUpdate()
                startRotationAnimator()
            } else {
                playNextSong()
            }
        }

        nextBtn.setOnClickListener {
            playNextSong()
        }

        prevBtn.setOnClickListener {
            playPreviousSong()
        }

        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        showFullLyricsBtn.setOnClickListener {
            val fullLyricsFragment = LyricsFragment()

            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_up,
                    R.anim.slide_out_down,
                    R.anim.slide_in_up,
                    R.anim.slide_out_down
                )
                .replace(R.id.main_container, fullLyricsFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun playNextSong() {
        currentSongIndex = if (!shuffleBoolean) {
            (0 until tracks.data.size).random()
        } else {
            (currentSongIndex + 1) % tracks.data.size
        }
        playSong(currentSongIndex)
    }

    private fun playPreviousSong() {
        currentSongIndex = if (!shuffleBoolean) {
            (0 until tracks.data.size).random()
        } else {
            if (currentSongIndex == 0) {
                tracks.data.size - 1
            } else {
                currentSongIndex - 1
            }
        }
        playSong(currentSongIndex)
    }

    private fun playSong(index: Int) {
        stopSeekBarUpdate()

        val song = tracks.data[index]

        textViewSongName.text = song.title
        textViewSongName.isSelected = true
        textViewArtistName.text = song.artist.name
        Glide.with(this).load(song.artist.pictureBig).circleCrop().into(imageViewSong)

        seekBar.max = 30000
        seekBar.progress = 0

        durationPlayed.text = formatDuration(0)
        durationTotal.text = formatDuration(30000)

        val curBackgroundPreLyrics = layoutPreviewLyrics.background as GradientDrawable
        currentPreLyricsColor = getRandomColor()
        curBackgroundPreLyrics.setColor(currentPreLyricsColor)

        mediaPlayer.reset()
        mediaPlayer.setDataSource(song.preview)
        mediaPlayer.prepare()

        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            startSeekBarUpdate()
            startRotationAnimator()
            buttonPlayPause.setImageResource(R.drawable.icon_pause)
        }
    }

    private fun startRotationAnimator() {
        if (!isRotating) {
            rotationAnimator.start()
            isRotating = true
        } else {
            rotationAnimator.resume()
        }
    }

    private fun stopRotationAnimator() {
        rotationAnimator.pause()
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

    private fun getRandomColor(): Int {
        val random = java.util.Random()

        val red = random.nextInt(100) + 100
        val green = random.nextInt(100) + 100
        val blue = random.nextInt(100) + 100

        return Color.rgb(red, green, blue)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
        stopSeekBarUpdate()
        stopRotationAnimator()
    }
}