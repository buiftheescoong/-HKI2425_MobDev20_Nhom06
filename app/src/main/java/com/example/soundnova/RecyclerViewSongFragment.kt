package com.example.soundnova

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soundnova.models.Tracks

class RecyclerViewSongFragment(private val tracks: Tracks) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recycle_view_songs_fragment, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewSongs)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = SongAdapter(tracks, parentFragmentManager)

        return view
    }
}