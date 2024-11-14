package com.example.soundnova.musicPlayer

import android.media.MediaMetadataRetriever
import com.example.soundnova.MainActivity
import java.io.IOException

object Util {
    @Throws(IOException::class)
    fun getAlbumArt(uri: String, retriever: MediaMetadataRetriever): ByteArray? {
        if (uri == "") return null
        retriever.setDataSource(uri)
        val art = retriever.embeddedPicture
        retriever.release()
        return art
    }

    fun stringToSong(find: String): MusicFiles? {
        for (song in MainActivity.musicFiles) {
            if (find.contains(song.songTitle)) {
                return song
            }
        }
        return null
    }

    fun formattedTime(mCurrentPosition: Int): String {
        var totalout = ""
        var totalNew = ""
        val seconds = (mCurrentPosition % 60).toString()
        val minutes = (mCurrentPosition / 60).toString()
        totalout = "$minutes:$seconds"
        totalNew = "$minutes:0$seconds"
        return if (seconds.length == 1) {
            totalNew
        } else totalout
    }
}