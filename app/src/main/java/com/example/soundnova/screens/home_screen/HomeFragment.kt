package com.example.soundnova.screens.home_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soundnova.screens.adapters.OnItemClickListener
import com.example.soundnova.R
import com.example.soundnova.screens.adapters.SongAdapter
import com.example.soundnova.databinding.HomeActivityBinding
import com.example.soundnova.models.Tracks
import com.example.soundnova.service.DeezerApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope


class HomeFragment : Fragment() {
    private lateinit var binding: HomeActivityBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: SongAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = HomeActivityBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = HomeActivityBinding.bind(view)

//        binding.recyclerViewTabsSongs.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        lifecycleScope.launch {
            val tracks = DeezerApiHelper.fetchPopularTracks()
            Log.d("HomeFragment", "Fetched popular tracks: $tracks")
            binding.recyclerViewRecommendSongs.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = SongAdapter(tracks, object : OnItemClickListener {
                override fun onItemClick(position: Int, tracks: Tracks) {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_musicPlayerFragment,
                        Bundle().apply {
                            putParcelable("tracks", tracks)
                            putInt("position", position)
                        }
                    )
                }
            })
            binding.recyclerViewRecommendSongs.adapter = adapter
        }
        // Collect tracks from the ViewModel
//        viewLifecycleOwner.lifecycleScope.launch {
//            // Ensure the flow is collected when the lifecycle is in STARTED state
//            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                // Collect tracks from the ViewModel
//                viewModel.tracks.collect { tracks ->
//                    if (tracks != null) {
//                        // If tracks are available, set up the adapter
//                        adapter = SongAdapter(tracks, object : OnItemClickListener {
//                            override fun onItemClick(position: Int, tracks: Tracks) {
//                                findNavController().navigate(
//                                    R.id.action_homeFragment_to_musicPlayerFragment,
//                                    Bundle().apply {
//                                        putParcelable("tracks", tracks)
//                                        putInt("position", position)
//                                    }
//                                )
//                            }
//                        })
//                        binding.recyclerViewTabsSongs.adapter = adapter
//                    } else {
//                        // If tracks are null, show a loading state or placeholder
//                        Log.w("HomeFragment", "Tracks list is still loading or not available.")
//                    }
//                }
//            }
//        }

        // Trigger fetching the popular tracks
        viewModel.fetchPopularTracks()

    }}

