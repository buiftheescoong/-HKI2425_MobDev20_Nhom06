package com.example.soundnova.musicPlayer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music_files")
data class MusicFiles(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var songsCategory: String = "",
    var songTitle: String = "",
    var artist: String = "",
    var songDuration: String = "",
    var songLink: String = "",
    var mKey: String? = null,
    var lyrics: String? = null
)