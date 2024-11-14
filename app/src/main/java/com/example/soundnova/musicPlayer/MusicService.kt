package com.example.soundnova.musicPlayer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.soundnova.R
import com.example.soundnova.musicPlayer.MusicPlayerActivity.Companion.listSongs
import com.example.soundnova.musicPlayer.MusicPlayerActivity.Companion.loading
import java.io.IOException
import java.util.*

class MusicService : Service(), MediaPlayer.OnCompletionListener {

    private val NOTIFICATION_CHANNEL_ID_SERVICE: String = "123"
    private val NOTIFICATION_CHANNEL_ID_INFO: String = "456"
    companion object {
        const val MUSIC_LAST_PLAYED = "Last_Played"
        const val MUSIC_FILE = "Stored_Music"
        const val ARTIST_NAME = "Artist Name"
        const val SONG_NAME = "Song Name"
    }

    private val mBinder = MyBinder()
    private var mediaPlayer: MediaPlayer? = null
    private var uri: Uri? = null
    private var position = -1
    private var actionPlaying: ActionPlaying? = null
    private var mediaSessionCompat: MediaSessionCompat? = null

    override fun onCreate() {
        super.onCreate()
        mediaSessionCompat = MediaSessionCompat(baseContext, "my audio")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID_SERVICE,
                    "App Service", NotificationManager.IMPORTANCE_DEFAULT
                )
            )
            nm.createNotificationChannel(
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID_INFO,
                    "Download Info", NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.e("Bind", "method")
        return mBinder
    }

    inner class MyBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) return START_STICKY
        val myPos = intent.getIntExtra("servicePosition", -1)
        val actionName = intent.getStringExtra("ActionName")

        if (myPos != -1) {
            playMedia(myPos)
        }

        actionName?.let {
            if (actionPlaying != null) {
                when (it) {
                    "playPause" -> {
                        try {
                            playPauseBtnClicked()
                        } catch (_: Exception) {
                        }
                    }
                    "next" -> {
                        try {
                            nextBtnClicked()
                        } catch (_: IOException) {
                        }
                    }
                    "previous" -> {
                        try {
                            prevBtnClicked()
                        } catch (_: IOException) {
                        }
                    }
                }
            }
        }

        return START_STICKY
    }

    private fun playMedia(startPosition: Int) {
        if (position != startPosition) {
            position = startPosition
            mediaPlayer?.let {
                it.stop()
                it.release()
            }
            createMediaPlayer(position)
            mediaPlayer?.start()
        }
    }

    fun start() {
        mediaPlayer?.start()
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying == true
    }

    fun stop() {
        mediaPlayer?.stop()
    }

    fun release() {
        mediaPlayer?.release()
    }

    fun getDuration(): Int {
        return mediaPlayer?.duration ?: 0
    }

    fun seekTo(pos: Int) {
        mediaPlayer?.seekTo(pos)
    }

    fun getCurrentPosition(): Int {
        return if (mediaPlayer == null || loading) {
            0
        } else {
            mediaPlayer?.currentPosition ?: 0
        }
    }

    fun getCurrentUri(): Uri? {
        return uri
    }

    fun createMediaPlayer(pos: Int) {
        position = pos
        uri = if (position != -1) {
            Uri.parse(listSongs[position].songLink)
        } else {
            Uri.parse("https://firebasestorage.googleapis.com/v0/b" +
                    "/serverside-7a675.appspot.com/o/songs%2F1713442432213.mp3?alt=media&token=e754aab0-36fd-4a90-8546-e97be79dc9ed")
        }

        val editor: SharedPreferences.Editor =
            getSharedPreferences(MUSIC_LAST_PLAYED, MODE_PRIVATE).edit()
        editor.putString(MUSIC_FILE, uri.toString())
        editor.putString(ARTIST_NAME, listSongs[position].artist)
        editor.putString(SONG_NAME, listSongs[position].songTitle)
        editor.apply()

        mediaPlayer = MediaPlayer.create(baseContext, uri)
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun onCompleted() {
        mediaPlayer?.setOnCompletionListener(this)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        try {
            actionPlaying?.endClicked()
            mediaPlayer?.let {
                createMediaPlayer(MusicPlayerActivity.position)
                start()
                onCompleted()
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun setCallBack(actionPlaying: ActionPlaying) {
        this.actionPlaying = actionPlaying
    }

    @Throws(IOException::class)
    fun showNotification(playPauseBtn: Int) {
        val prevIntent = PendingIntent.getBroadcast(
            this, 0, Intent(this, NotificationReceiver::class.java).setAction("ACTION_PREVIOUS"),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val pauseIntent = PendingIntent.getBroadcast(
            this, 0, Intent(this, NotificationReceiver::class.java).setAction("ACTION_PLAY"),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val nextIntent = PendingIntent.getBroadcast(
            this, 0, Intent(this, NotificationReceiver::class.java).setAction("ACTION_NEXT"),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        var picture: ByteArray? = null
        picture = Util.getAlbumArt(listSongs[position].songLink, MediaMetadataRetriever())

        val thumb: Bitmap = if (picture != null) {
            BitmapFactory.decodeByteArray(picture, 0, picture.size)
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background)
        }

        val NOTIFICATION_CHANNEL_ID = "com.example.simpleapp"
        val channelName = "My Background Service"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE
            )
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(chan)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_play)
            .setLargeIcon(thumb)
            .setContentTitle(listSongs[position].songTitle)
            .setContentText(listSongs[position].artist)
            .addAction(R.drawable.icon_previous_skip, "Previous", prevIntent)
            .addAction(R.drawable.icon_play, "Pause", pauseIntent)
            .addAction(R.drawable.icon_next_skip, "Next", nextIntent)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSessionCompat?.sessionToken)) // set media session
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setOnlyAlertOnce(true)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // set visibility


        val notification = notificationBuilder.build()
        startForeground(2, notification)
    }

    @Throws(IOException::class)
    fun nextBtnClicked() {
        actionPlaying?.nextBtnClicked()
    }

    @Throws(IOException::class)
    fun prevBtnClicked() {
        actionPlaying?.prevBtnClicked()
    }

    @Throws(IOException::class)
    fun playPauseBtnClicked() {
        actionPlaying?.playPauseBtnClicked()
    }
}