package com.example.soundnova.musicPlayer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MusicFilesDao {
    @Query("SELECT * FROM music_files")
    fun getAllSongs(): List<MusicFiles>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(songs: List<MusicFiles>)
}