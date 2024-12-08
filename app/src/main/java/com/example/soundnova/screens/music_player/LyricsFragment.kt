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

class LyricsFragment : Fragment() {

    private lateinit var binding: LyricsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = LyricsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val binding = LyricsFragmentBinding.bind(view)

//        binding.lyricsContent.text = currentSongLyrics

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack(R.id.musicPlayerFragment, false)
        }
    }
}
