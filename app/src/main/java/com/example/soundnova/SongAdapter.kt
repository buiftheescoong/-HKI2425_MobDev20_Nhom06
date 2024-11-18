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
import com.example.soundnova.data.local.room.Convert
import com.example.soundnova.models.Tracks

class SongAdapter(private val songs: Tracks) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    inner class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val songName: TextView = view.findViewById(R.id.textSongName)
        val songArtists: TextView = view.findViewById(R.id.textSongArtist)
        val songImage: ImageView = view.findViewById(R.id.imageSong)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        Log.d("TAG", "1")
        val convert = Convert()
        val song = songs.data.get(position)
        holder.songName.text = song.title
        val songArtistsOfString = song.artist.name
        holder.songArtists.text = songArtistsOfString
        Glide.with(holder.itemView.context).load(song.artist.pictureBig).into(holder.songImage)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MusicPlayerActivity::class.java).apply {
//                putExtra("songName", song.name)
//                putExtra("artistName", song.artist)
//                putExtra("songImage", song.imageUrl)
//                putExtra("song", song)
//                putExtra("songUrl", song.previewUrl)

                putExtra("tracks", songs)
//                putExtra("track", song)
                putExtra("index", position)
            }
            holder.itemView.context.startActivity(intent)
            Log.d("TAG", "2")
        }
    }

    override fun getItemCount(): Int = songs.data.size
}