package com.example.soundnova

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SongAdapterVertical(private val songs: List<Song>) : RecyclerView.Adapter<SongAdapterVertical.SongViewHolder>() {

    inner class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val songName: TextView = view.findViewById(R.id.textSongName)
        val songArtists: TextView = view.findViewById(R.id.textSongArtist)
        val songImage: ImageView = view.findViewById(R.id.imageSong)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song_vertical, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.songName.text = song.name
        holder.songArtists.text = song.artists.joinToString(", ")
        Glide.with(holder.itemView.context).load(song.imageUrl).into(holder.songImage)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MusicPlayerActivity::class.java).apply {
//                putExtra("songName", song.name)
//                putExtra("artistName", song.artist)
//                putExtra("songImage", song.imageUrl)
//                putExtra("song", song)
//                putExtra("songUrl", song.previewUrl)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = songs.size
}