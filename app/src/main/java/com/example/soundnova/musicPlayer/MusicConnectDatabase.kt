package com.example.soundnova.musicPlayer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [MusicFiles::class], version = 1)
abstract class MusicConnectDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicFilesDao

    companion object {
        @Volatile
        private var INSTANCE: MusicConnectDatabase? = null

        fun getDatabase(context: Context): MusicConnectDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MusicConnectDatabase::class.java,
                    "music_database"
                ).addCallback(roomCallback).build()
                INSTANCE = instance
                instance
            }
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase()
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        private fun populateDatabase() {
            val sampleSongs = listOf(
                MusicFiles(
                    songsCategory = "Pop",
                    songTitle = "Shape of You",
                    artist = "Ed Sheeran",
                    songDuration = "263000",
                    songLink = "https://example.com/shape_of_you.mp3",
                    mKey = "C",
                    lyrics = "The club isn't the best place to find a lover..."
                ),
                MusicFiles(
                    songsCategory = "Rock",
                    songTitle = "Bohemian Rhapsody",
                    artist = "Queen",
                    songDuration = "354000",
                    songLink = "https://example.com/bohemian_rhapsody.mp3",
                    mKey = "Bb",
                    lyrics = "Is this the real life? Is this just fantasy..."
                ),
                MusicFiles(
                    songsCategory = "Classical",
                    songTitle = "Für Elise",
                    artist = "Ludwig van Beethoven",
                    songDuration = "165000",
                    songLink = "https://example.com/fur_elise.mp3",
                    mKey = "A minor",
                    lyrics = null //
                )
            )

            GlobalScope.launch {
                INSTANCE?.musicDao()?.insertSongs(sampleSongs)
            }
        }
    }
}