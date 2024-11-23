//package com.example.soundnova.screens.music_player
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.navArgs
//import com.bumptech.glide.Glide
//import com.example.soundnova.R
//import com.example.soundnova.databinding.HomeActivityBinding
//import com.example.soundnova.databinding.PlayerActivityBinding
//import com.example.soundnova.models.Tracks
//
//class MusicPlayerFragment : Fragment() {
//    private lateinit var binding: PlayerActivityBinding
//    private val viewModel: MusicPlayerViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View {
//        binding = PlayerActivityBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        Log.d("MusicPlayerFragment", "MusicPlayerFragment created")
//
//        val binding = PlayerActivityBinding.bind(view)
//        val tracks = arguments?.getParcelable<Tracks>("tracks")
//        val position = arguments?.getInt("position") ?: 0
//        tracks?.let {
//            viewModel.setTrack(it, position)
//        }
//
//        viewModel.currentTrack.observe(viewLifecycleOwner) { track ->
//            binding.songName.text = track.title
//            binding.songArtist.text = track.artist.name
//            Glide.with(this).load(track.artist.pictureBig).into(binding.coverArt)
//        }
//
//        binding.playPause.setOnClickListener {
//            viewModel.togglePlayPause()
//        }
//
//        binding.idNext.setOnClickListener {
//            viewModel.playNext()
//        }
//
//        binding.idPrev.setOnClickListener {
//            viewModel.playPrevious()
//        }
//    }
//}
