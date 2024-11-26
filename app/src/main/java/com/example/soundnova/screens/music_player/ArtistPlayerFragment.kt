package com.example.soundnova.screens.music_player

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.soundnova.R
import com.example.soundnova.databinding.ArtistListBinding
import com.example.soundnova.models.Artists
import com.example.soundnova.models.Tracks
import com.example.soundnova.screens.adapters.OnItemClickTrackListener
import com.example.soundnova.screens.adapters.SongAdapter
import com.example.soundnova.service.DeezerApiHelper
import kotlinx.coroutines.launch

class ArtistPlayerFragment : Fragment() {

    private lateinit var binding: ArtistListBinding
    private lateinit var tracks: Tracks
    private lateinit var artists: Artists
    private var currentArtistIndex = 0
    private lateinit var adapterSong: SongAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ArtistListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = ArtistListBinding.bind(view)

        try {
            artists = arguments?.getParcelable<Artists>("artists")!!
            currentArtistIndex = arguments?.getInt("position") ?: 0
            playArtist(currentArtistIndex)
        } catch (e: Exception) {
            Log.e("ArtistPlayerFragment", "Error retrieving tracks", e)
        }
    }

    private fun playArtist(index: Int) {
        val artist = artists.data[index]
        Glide.with(this).load(artist.pictureBig).circleCrop().into(binding.imageArtistCover)
        binding.textArtist.text = artist.name

        lifecycleScope.launch {
            val tracks = DeezerApiHelper.fetchPopularTracks()
            binding.recyclerViewSongs.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            if (tracks != null) {
                adapterSong = SongAdapter(tracks, object : OnItemClickTrackListener {
                    override fun onItemClick(position: Int, tracks: Tracks) {
                        findNavController().navigate(
                            R.id.action_artistPlayerFragment_to_musicPlayerFragment,
                            Bundle().apply {
                                putParcelable("tracks", tracks)
                                putInt("position", position)
                            }
                        )
                    }
                }, 1)
            }
            binding.recyclerViewSongs.adapter = adapterSong
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}
