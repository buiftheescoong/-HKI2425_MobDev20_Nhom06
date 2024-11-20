package com.example.soundnova.screens.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.soundnova.R
import com.example.soundnova.databinding.ItemSongBinding
import com.example.soundnova.models.TrackData
import com.example.soundnova.models.Tracks

class SongAdapter(
    private val tracks: Tracks,
    private val onItemClick: (Tracks, Int) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder(private val binding: ItemSongBinding,
                         private val onItemClick: (Tracks, Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        private val songName: TextView = binding.textSongName
        private val songArtist: TextView = binding.textSongArtist
        private val songImage: ImageView = binding.imageSong
        fun bind(track: TrackData, tracks: Tracks, position: Int) {
            songName.text = track.title
            songArtist.text = track.artist.name
            Glide.with(itemView.context)
                .load(track.artist.pictureBig)
                .into(songImage)
            binding.root.setOnClickListener {
                onItemClick(tracks, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding, onItemClick)
    }
    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(tracks.data[position], tracks, position)
    }
    override fun getItemCount() = tracks.data.size
}
