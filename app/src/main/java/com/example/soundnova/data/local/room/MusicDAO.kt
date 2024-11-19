package com.example.soundnova.data.local.room

import androidx.room.*

@Dao
interface MusicDAO {

}

//    @Query("SELECT * FROM music_table WHERE name LIKE '%' || :query || '%' OR artists LIKE '%' || :query || '%'")
//    fun getAllSongsFlow(query: String): Flow<List<Song>>
//
//    @Query("SELECT * FROM music_table")
//    suspend fun getAllSongs(): List<Song>
//
//    @Query("SELECT * FROM music_table WHERE genre = :genre")
//    fun getSongByGenre(genre: String): Flow<List<Song>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSong(songs: List<Song>)
//
//    @Query("DELETE FROM music_table")
//    suspend fun deleteAllSongs()
//
//    @Delete
//    suspend fun deleteAllSongs(song: Song)
//}