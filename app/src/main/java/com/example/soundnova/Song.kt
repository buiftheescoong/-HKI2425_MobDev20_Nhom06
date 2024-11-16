package com.example.soundnova

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music_table")
data class Song(
    @PrimaryKey
    val name: String = "",
    val artists: List<String> = emptyList(),
    val imageUrl: String = "",
    val duration: Int = 0,
    val musicUrl: String = "",
    val genre: String = "",
    val lyrics: String = ""
)
