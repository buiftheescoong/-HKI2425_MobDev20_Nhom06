package com.example.soundnova

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.soundnova.models.Tracks

class SongAdapter(private val songs: Tracks, private val fragmentManager: FragmentManager) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private lateinit var history : History
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
        val song = songs.data[position]
        holder.songName.text = song.title
        val songArtistsOfString = song.artist.name
        holder.songArtists.text = songArtistsOfString
        Glide.with(holder.itemView.context).load(song.artist.pictureBig).into(holder.songImage)

        history = History(holder.itemView.context)

        holder.itemView.setOnClickListener {
            val musicPlayerFragment = MusicPlayerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("tracks", songs)
                    putInt("index", position)
                }
            }

            fragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_up,
                    R.anim.slide_out_down,
                    R.anim.slide_in_up,
                    R.anim.slide_out_down
                )
                .replace(R.id.main_container, musicPlayerFragment)
                .addToBackStack(null)
                .commit()

            history.addHistorySong(song.title, song.artist.name.split(","), song.artist.pictureBig, song.duration, song.preview)
            Log.d("TAG", "2")
        }
    }

    override fun getItemCount(): Int = songs.data.size
}