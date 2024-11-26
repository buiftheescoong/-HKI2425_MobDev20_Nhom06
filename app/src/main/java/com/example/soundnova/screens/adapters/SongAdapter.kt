package com.example.soundnova.screens.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.soundnova.R
import com.example.soundnova.models.Tracks

interface OnItemClickListener {
    fun onItemClick(position: Int, tracks: Tracks)
}
class SongAdapter(private val songs: Tracks, private val listener: OnItemClickListener) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    inner class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition, tracks = songs)
            }
        }
        val songName: TextView = view.findViewById(R.id.textSongName)
        val songArtists: TextView = view.findViewById(R.id.textSongArtist)
        val songImage: ImageView = view.findViewById(R.id.imageSong)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs.data[position]
        holder.songName.text = song.title
        holder.songArtists.text = song.artist.name
        Glide.with(holder.itemView.context).load(song.artist.pictureBig).into(holder.songImage)

    }

    override fun getItemCount(): Int = songs.data.size
}