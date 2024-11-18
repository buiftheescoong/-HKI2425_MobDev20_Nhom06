package com.example.soundnova.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val pictureUrl: String,
)
