//package com.example.soundnova
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.soundnova.databinding.LibraryTestBinding
//import com.example.soundnova.models.TrackData
//import com.example.soundnova.models.Tracks
//import com.example.soundnova.screens.adapters.OnItemClickListener
//import com.example.soundnova.screens.adapters.SongAdapter
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
//
//class LibraryFragment : Fragment() {
//    private val listTracksFav = mutableListOf<TrackData>() // Danh sách bài hát yêu thích
//    private lateinit var binding: LibraryTestBinding
//    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val db = Firebase.firestore
//    private lateinit var adapter: SongAdapter // Khai báo adapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View {
//        binding = LibraryTestBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val binding = LibraryTestBinding.bind(view)
//
//        // Khởi tạo adapter và gán vào RecyclerView
//        adapter = SongAdapter(Tracks(listTracksFav), object : OnItemClickListener {
//            override fun onItemClick(position: Int, tracks: Tracks) {
//                // Điều hướng hoặc xử lý khi người dùng chọn bài hát
//            }
//        })
//        binding.recyclerviewtest.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerviewtest.adapter = adapter
//
//        // Lấy danh sách bài hát yêu thích từ Firestore
//        db.collection("favorite_library")
//            .get()
//            .addOnSuccessListener { documents ->
//                val currentUser = firebaseAuth.currentUser
//                val userEmail = currentUser?.email
//
//                // Kiểm tra log xem có dữ liệu không
//                Log.d("LibraryFragment", "Fetching favorite songs for user: $userEmail")
//
//                // Thêm bài hát vào danh sách mà không xóa
//                for (document in documents) {
//                    if (document.getString("idUser") == userEmail) {
//                        val track = document.toObject(TrackData::class.java)
//                        if (track != null) {
//                            // Kiểm tra log về track
//                            Log.d("LibraryFragment", "Fetched track: ${track.title}, ${track.artist.name}")
//                            listTracksFav.add(track) // Thêm bài hát vào danh sách yêu thích
//                        } else {
//                            Log.e("LibraryFragment", "Track is null for document: ${document.id}")
//                        }
//                    }
//                }
//
//                adapter.notifyDataSetChanged() // Thông báo adapter rằng dữ liệu đã thay đổi
//            }
//            .addOnFailureListener { exception ->
//                Log.e("LibraryFragment", "Error getting documents: ", exception)
//            }
//
//    }
//}
