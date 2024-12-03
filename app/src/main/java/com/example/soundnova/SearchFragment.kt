package com.example.soundnova

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soundnova.databinding.SearchTestBinding
import com.example.soundnova.models.Tracks
import com.example.soundnova.screens.adapters.OnItemClickTrackListener
import com.example.soundnova.screens.adapters.SongAdapter
import com.example.soundnova.service.DeezerSearchApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {
    private var _binding: SearchTestBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SongAdapter
    private var tracks: Tracks = Tracks()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = SearchTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = SearchTestBinding.bind(view)

        binding.searchText.requestFocus()
        binding.searchText.isFocusable = true
        binding.searchText.isFocusableInTouchMode = true
        binding.searchText.requestFocus()

        binding.recyclerviewsearch.layoutManager = LinearLayoutManager(requireContext())
        adapter = SongAdapter(tracks, object : OnItemClickTrackListener {
            override fun onItemClick(position: Int, tracks: Tracks) {
                val track = tracks.data[position]
                Toast.makeText(
                    requireContext(),
                    "Clicked: ${track.title} by ${track.artist?.name}",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(
                    R.id.action_search_to_music,
                    Bundle().apply {
                        putParcelable("tracks", tracks)
                        putInt("position", position)
                    }
                )
            }
        }, viewType = 1)
        binding.recyclerviewsearch.adapter = adapter

        binding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                if (query.isNotEmpty()) {
                    fetchTracks(query) // Cập nhật RecyclerView theo thời gian thực
                } else {
                    // Xóa danh sách khi không có từ khóa
                    tracks.data.clear()
                    adapter.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
    private fun fetchTracks(query: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val result = DeezerSearchApiHelper.fetchTracks(query)
                withContext(Dispatchers.Main) {
                    tracks.data = result.data.toMutableList()
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error fetching tracks: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
