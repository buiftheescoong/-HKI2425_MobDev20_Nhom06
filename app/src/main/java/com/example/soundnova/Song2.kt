package com.example.soundnova

data class Song2(
    val idUser: String? = null,
    val title: String? = null,
    val artist: List<String> = emptyList(),
    val image: String? = null,
    val audioUrl: String? = null,
)
