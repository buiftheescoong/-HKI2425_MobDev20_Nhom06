package com.example.soundnova.data.local.dataSource

import com.example.soundnova.Song
import com.example.soundnova.data.local.room.MusicDAO
import javax.inject.Inject

class RoomMusicDataSource @Inject constructor(private val musicDao: MusicDAO) {

    fun getAllSongsFlow(query: String = "") = musicDao.getAllSongsFlow(query)

    suspend fun getAllSongs() = musicDao.getAllSongs()

    fun getSongByGenre(genre: String) = musicDao.getSongByGenre(genre)

    suspend fun insertSong(songs: List<Song>) = musicDao.insertSong(songs)

    suspend fun deleteAllSongs() = musicDao.deleteAllSongs()

    suspend fun deleteSong(song: Song) = musicDao.deleteAllSongs(song)
    
}