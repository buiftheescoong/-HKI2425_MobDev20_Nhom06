//package com.example.soundnova.screens.home_screen
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.activity.result.launch
//import androidx.compose.runtime.LaunchedEffect
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.lifecycleScope
//import androidx.lifecycle.repeatOnLifecycle
//import androidx.lifecycle.viewModelScope
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.soundnova.R
//import com.example.soundnova.databinding.HomeActivityBinding
//import com.example.soundnova.models.TrackData
//import com.example.soundnova.screens.adapters.SongAdapter
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class HomeFragment : Fragment() {
//    private lateinit var binding: HomeActivityBinding
//    private val viewModel: HomeViewModel by viewModels()
//    private lateinit var adapter: SongAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View {
//        binding = HomeActivityBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
////        binding.recyclerViewTabsSongs.layoutManager =
////            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        val binding = HomeActivityBinding.bind(view)
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            try {
//                withContext(Dispatchers.IO) {
//                    viewModel.fetchPopularTracks()
//                    Log.d("HomeFragment", "Fetched popular tracks: ${viewModel.tracks}")// Đợi fetchPopularTracks hoàn thành
//                }
//
//                // Sau khi gọi xong, truy xuất lại tracks từ ViewModel
//                val tracks = viewModel.tracks
//                if (tracks != null) {
//                    adapter = SongAdapter(tracks) { selectedTracks, position ->
//                        findNavController().navigate(
//                            R.id.action_homeFragment_to_musicPlayerFragment,
//                            Bundle().apply {
//                                putParcelable("tracks", selectedTracks)
//                                putInt("position", position)
//                            }
//                        )
//                    }
//                    binding.recyclerViewTabsSongs.adapter = adapter
//                } else {
//                    // Log cảnh báo nếu danh sách tracks null
//                    Log.w("HomeFragment", "Tracks list is null. Unable to set adapter.")
//                }
//            } catch (e: Exception) {
//                // Ghi log nếu có lỗi xảy ra
//                Log.e("HomeFragment", "Error fetching tracks or setting adapter", e)
//            }
//        }
//    }
//}
