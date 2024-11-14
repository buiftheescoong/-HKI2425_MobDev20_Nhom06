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
    abstract fun musicFilesDao(): MusicFilesDao

    companion object {
        @Volatile
        private var INSTANCE: MusicConnectDatabase? = null

        @OptIn(DelicateCoroutinesApi::class)
        fun getDatabase(context: Context): MusicConnectDatabase {
            return (INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MusicConnectDatabase::class.java,
                    "music_database"
                ).build()

                // Gọi populateDatabase sau khi database được tạo
                INSTANCE = instance
                GlobalScope.launch {
                    populateDatabase(INSTANCE!!)
                }
                INSTANCE
            })!!
        }

        private suspend fun populateDatabase(db: MusicConnectDatabase) {
            val sampleSongs = listOf(
                MusicFiles(
                    songsCategory = "Pop",
                    songTitle = "Shape of You",
                    artist = "Ed Sheeran",
                    songDuration = "263000",
                    songLink = "https://drive.google.com/file/d/1RK3xC6iWne5Oe5tS1m7Xhs0DfP20rjvg/view?fbclid=IwY2xjawGiX75leHRuA2FlbQIxMAABHeqDW5HUO6iUY-YOOeBRE2CTJg6KoiIov5Y0VO5-mEuMHqF3qO8g3VaLXg_aem_k7F7V0aBDRgdCTjh7KIxpg",
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
                    lyrics = null
                )
            )
            db.musicFilesDao().insertSongs(sampleSongs)
        }
    }
}