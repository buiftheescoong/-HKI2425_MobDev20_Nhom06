package com.example.soundnova

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.soundnova.models.Tracks

class LyricsFragment: Fragment() {

    private lateinit var textViewArtistName: TextView
    private lateinit var textViewSongName: TextView
    private lateinit var buttonPlayPause: ImageButton
    private lateinit var backBtn : ImageView
    private lateinit var moreBtn : ImageView
    private lateinit var heartBtn : ImageView
    private lateinit var seekBar: SeekBar
    private lateinit var shuffleBtn: ImageView
    private lateinit var prevBtn: ImageView
    private lateinit var nextBtn: ImageView
    private lateinit var repeatBtn: ImageView
    private lateinit var fullLyrics: TextView
    private lateinit var tracks: Tracks

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.lyrics_fragment, container, false)

        textViewArtistName = view.findViewById(R.id.songArtist)
        textViewSongName = view.findViewById(R.id.songName)
        buttonPlayPause = view.findViewById(R.id.play_pause)
        backBtn = view.findViewById(R.id.back_btn)
        moreBtn = view.findViewById(R.id.more_btn)
        heartBtn = view.findViewById(R.id.id_heart)
        seekBar = view.findViewById(R.id.seekBar)
        shuffleBtn = view.findViewById(R.id.id_shuffle)
        prevBtn = view.findViewById(R.id.id_prev)
        nextBtn = view.findViewById(R.id.id_next)
        repeatBtn = view.findViewById(R.id.id_repeat)
        fullLyrics = view.findViewById(R.id.lyricsContent)

        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }
}