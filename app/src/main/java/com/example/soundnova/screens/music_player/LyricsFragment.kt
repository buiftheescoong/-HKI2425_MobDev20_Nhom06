package com.example.soundnova.screens.music_player

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.soundnova.R
import com.example.soundnova.databinding.LyricsFragmentBinding
import com.example.soundnova.databinding.PlayerActivityBinding
import com.example.soundnova.models.Tracks
import com.example.soundnova.screens.music_player.MusicPlayerFragment.Companion.currentSongLyrics
import com.example.soundnova.screens.music_player.MusicPlayerFragment.Companion.heartBoolean

class LyricsFragment : Fragment() {

    private lateinit var binding: LyricsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = LyricsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lyricsContent.setText(currentSongLyrics)

        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_lyricsPlayerFragment_to_musicPlayerFragment)
        }
    }
}
