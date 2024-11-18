package com.example.soundnova.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ArtistsResponse(
    @SerializedName("data")
    val data: List<Artist>,

    val categoryName: String
)

@Parcelize
data class Artist(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("picture_big")
    val pictureBig: String,
) : Parcelable