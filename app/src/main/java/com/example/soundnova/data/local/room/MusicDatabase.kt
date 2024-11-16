package com.example.soundnova.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.soundnova.Song

@Database(entities = [Song::class], version = 5, exportSchema = false)
@TypeConverters(Convert::class)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun getMusicDao(): MusicDAO
}