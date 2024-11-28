package com.example.soundnova.screens.music_player

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.soundnova.FavoriteLibrary
import com.example.soundnova.History
import com.example.soundnova.R
import com.example.soundnova.databinding.PlayerActivityBinding
import com.example.soundnova.file.sendSongUrlToServer
import com.example.soundnova.models.Tracks
import com.example.soundnova.service.LyricsApiHelper

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File


class MusicPlayerFragment : Fragment() {

    private lateinit var history: History
    private lateinit var fav: FavoriteLibrary

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

    private lateinit var binding: PlayerActivityBinding
    //    private val viewModel: MusicPlayerViewModel by viewModels()
    private lateinit var rotationAnimator: ObjectAnimator
    private lateinit var tracks: Tracks
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = PlayerActivityBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = PlayerActivityBinding.bind(view)

        mediaPlayer = MediaPlayer()

        rotationAnimator = ObjectAnimator.ofFloat(binding.coverArt, "rotation", 0f, 360f).apply {
            duration = 30000L
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
        }

        try {
            tracks = arguments?.getParcelable<Tracks>("tracks")!!
            currentSongIndex = arguments?.getInt("position") ?: 0
            playSong(currentSongIndex)
        } catch (e: Exception) {
            Log.e("MusicPlayerFragment", "Error retrieving tracks", e)
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.durationPlayed.text = formatDuration(progress)
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

        binding.heartBtn.setOnClickListener {
            heartBoolean = !heartBoolean
            fav = FavoriteLibrary(requireContext())
            if (heartBoolean) {

                val currentSong = tracks.data[currentSongIndex]
                fav.addFavSong(
                    currentSong.title,
                    currentSong.artist.name.split(","),
                    currentSong.artist.pictureBig,
                    currentSong.preview
                )
                binding.heartBtn.setImageResource(R.drawable.icon_heart_on)
            } else {
                fav.removeFavSong(tracks.data[currentSongIndex].title)
                binding.heartBtn.setImageResource(R.drawable.icon_heart)
            }
        }

        binding.playPause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                binding.playPause.setImageResource(R.drawable.icon_play)
                stopSeekBarUpdate()
                stopRotationAnimator()
            } else {
                mediaPlayer.start()
                binding.playPause.setImageResource(R.drawable.icon_pause)
                startSeekBarUpdate()
                startRotationAnimator()
            }
        }

        binding.idShuffle.setOnClickListener {
            shuffleBoolean = !shuffleBoolean
            val shuffleIcon = if (shuffleBoolean) {
                R.drawable.icon_shuffle_on
            } else {
                R.drawable.icon_shuffle_off
            }
            binding.idShuffle.setImageResource(shuffleIcon)
        }

        binding.idRepeat.setOnClickListener {
            repeatBoolean = !repeatBoolean
            val repeatIcon = if (repeatBoolean) {
                R.drawable.icon_repeat_on
            } else {
                R.drawable.icon_repeat
            }
            binding.idRepeat.setImageResource(repeatIcon)
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

        binding.idNext.setOnClickListener {
            playNextSong()
        }

        binding.idPrev.setOnClickListener {
            playPreviousSong()
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.showFullLyricsButton.setOnClickListener {
            findNavController().navigate(R.id.action_musicPlayerFragment_to_lyricsFragment)
        }

        binding.karaokeBtn.setOnClickListener {
            val song = tracks.data.get(currentSongIndex)
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
            }
            val karaokeFile = File(requireContext().getExternalFilesDir(null), "karaoke_${song.id}.wav")
            if (karaokeFile.exists()) {
                song.karaokeTrack = karaokeFile.absolutePath
                playKaraokeFromPath(song.karaokeTrack!!)
                Log.e("MusicPlayerFragment", "Playing karaoke from file: ${song.karaokeTrack}")
            } else {
                val serverUrl = "https://d43e-123-30-177-118.ngrok-free.app/process-audio" // URL server
                CoroutineScope(Dispatchers.Main).launch {
                    val karaokePath = sendSongUrlToServer(song.id, song.preview, serverUrl, requireContext())
                    if (karaokePath != null) {
                        song.karaokeTrack = karaokePath
                        playKaraokeFromPath(karaokePath)
                        Log.e("MusicPlayerFragment", "Download file: ${song.karaokeTrack}")
                    } else {
                        Toast.makeText(requireContext(), "Không thể tải nhạc karaoke", Toast.LENGTH_SHORT).show()
                    }
                }
        }}
    }

    private fun playNextSong() {
        currentSongIndex = if (!shuffleBoolean) {
            (0 until tracks.data.size).random()
        } else {
            (currentSongIndex + 1) % tracks.data.size
        }
        playSong(currentSongIndex)
        fav = FavoriteLibrary(requireContext())
        fav.checkFavSong(tracks.data[currentSongIndex].title) { isFavorite ->
            if (isFavorite) {
                binding.heartBtn.setImageResource(R.drawable.icon_heart_on)
            } else {
                binding.heartBtn.setImageResource(R.drawable.icon_heart)
            }
        }
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
        fav = FavoriteLibrary(requireContext())
        fav.checkFavSong(tracks.data[currentSongIndex].title) { isFavorite ->
            if (isFavorite) {
                binding.heartBtn.setImageResource(R.drawable.icon_heart_on)
            } else {
                binding.heartBtn.setImageResource(R.drawable.icon_heart)
            }
        }
    }

    private fun playSong(index: Int) {
        stopSeekBarUpdate()

        val song = tracks.data[index]
        lifecycleScope.launch {
            try {
                currentSongLyrics = LyricsApiHelper.fetchLyrics(song.artist.name, song.title)
            } catch (e: Exception) {
                Log.e("LyricsFragment", "Error fetching lyrics", e)
            }
        }
        binding.songName.text = song.title
        binding.songName.isSelected = true
        binding.songArtist.text = song.artist.name
        Glide.with(this).load(song.artist.pictureBig).circleCrop().into(binding.coverArt)

        fav = FavoriteLibrary(requireContext())
        fav.checkFavSong(tracks.data[currentSongIndex].title) { isFavorite ->
            if (isFavorite) {
                binding.heartBtn.setImageResource(R.drawable.icon_heart_on)
            } else {
                binding.heartBtn.setImageResource(R.drawable.icon_heart)
            }
        }


//        history = History(requireContext())
//        history.addHistorySong(song.title, song.artist.name.split(","), song.artist.pictureBig, song.duration, song.preview)

        binding.seekBar.max = 30000
        binding.seekBar.progress = 0

        binding.durationPlayed.text = formatDuration(0)
        binding.durationTotal.text = formatDuration(30000)

        val curBackgroundPreLyrics = binding.previewLayout.background as GradientDrawable
        currentPreLyricsColor = getRandomColor()
        curBackgroundPreLyrics.setColor(currentPreLyricsColor)

        mediaPlayer.reset()
        mediaPlayer.setDataSource(song.preview)
        mediaPlayer.prepare()

        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            startSeekBarUpdate()
            startRotationAnimator()
            binding.playPause.setImageResource(R.drawable.icon_pause)
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
                binding.seekBar.progress = currentPosition
                binding.durationPlayed.text = formatDuration(currentPosition)
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

    private fun playKaraokeFromPath(path: String) {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
