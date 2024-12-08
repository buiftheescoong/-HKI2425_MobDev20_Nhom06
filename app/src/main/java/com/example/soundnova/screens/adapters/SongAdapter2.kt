package com.example.soundnova.screens.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.soundnova.R
import com.example.soundnova.Song2

interface OnItemClickSong2Listener {
    fun onItemClick(position: Int, song: Song2)
}

class SongAdapter2(
    private val songs: List<Song2>,
    private val listener: OnItemClickSong2Listener,
    private val viewType: Int
) : RecyclerView.Adapter<SongAdapter2.SongViewHolder>() {

    inner class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition, songs[adapterPosition])
            }
        }

        val songName: TextView = view.findViewById(R.id.textSongName)
        val songArtists: TextView = view.findViewById(R.id.textSongArtist)
        val songImage: ImageView = view.findViewById(R.id.imageSong)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutRes = if (viewType == 1) {
            R.layout.item_song_vertical
        } else {
            R.layout.item_song
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.songName.text = song.title ?: "Unknown Title"
        holder.songArtists.text = song.artist.joinToString(", ") { it }
        Glide.with(holder.itemView.context)
            .load(song.image) // URL của ảnh bài hát
            .placeholder(R.drawable.ic_launcher_background) // Ảnh placeholder
            .into(holder.songImage)
    }

    override fun getItemCount(): Int = songs.size
}
