package com.example.soundnova

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val name: String,
    val artist: String,
    val imageUrl: String
) : Parcelable
