package com.example.soundnova

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
//    val id: Long,
    val name: String,
    val artist: String,
    val imageUrl: String,
//    val previewUrl: String,
//    val genre: String?
) : Parcelable
