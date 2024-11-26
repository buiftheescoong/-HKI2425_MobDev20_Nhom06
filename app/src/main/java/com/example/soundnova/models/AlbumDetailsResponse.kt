package com.example.soundnova.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumDetailsResponse(
    @SerializedName("tracks")
    val tracks: Tracks,

    @SerializedName("release_date")
    val releaseDate: String,

    var albumName: String,

    var albumPicture: String
) : Parcelable

@Parcelize
data class Tracks(
    @SerializedName("data")
    val data: List<TrackData>
) : Parcelable

@Parcelize
data class TrackData(
    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("duration")
    val duration: Int,

    @SerializedName("artist")
    var artist: Artist,

    @SerializedName("album")
    var album: Album,

    @SerializedName("preview")
    val preview: String,
    var isLiked: Boolean = false,
) : Parcelable
