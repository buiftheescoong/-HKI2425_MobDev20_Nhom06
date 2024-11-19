package com.example.soundnova

data class SongData(
    val idUser: String? = null,
    val title: String? = null,
    val artist: List<String> = emptyList(),
    val image: String? = null,
    val duration: String? = null,
    val album: String? = null,
    val audioUrl: String? = null,
    val genre: String? = null,
)
