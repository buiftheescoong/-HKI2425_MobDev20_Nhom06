package com.example.soundnova.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumsResponse(
    @SerializedName("data") var data: List<Data>,
    @SerializedName("total") val total: Int,
    @SerializedName("next") val next: String
) : Parcelable

@Parcelize
data class Data(
    @SerializedName("album") val album: Album
) : Parcelable

@Parcelize
data class Albums(
    @SerializedName("data") val data: List<Album>
) : Parcelable

@Parcelize
data class Album(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("cover_big") val coverBig: String,
    @SerializedName("cover_medium") val coverMedium: String,
    @SerializedName("cover_small") val coverSmall: String,
    @SerializedName("artist") val artist: Artist?,
    //@SerializedName("tracks") val tracks: Tracks,
    var albumDetailsResponse: AlbumDetailsResponse?
) : Parcelable