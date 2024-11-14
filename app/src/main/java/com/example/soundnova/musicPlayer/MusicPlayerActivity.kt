package com.example.soundnova.musicPlayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.soundnova.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Random

class MusicPlayerActivity : AppCompatActivity(), ActionPlaying, ServiceConnection {

    companion object {
        var listSongs: ArrayList<MusicFiles> = ArrayList()
        var position: Int = -1
        var loading: Boolean = false
        var shuffleBoolean: Boolean = false
        var repeatBoolean: Boolean = false
        var uri: Uri? = null
        var sender: String? = null
    }

    var musicService: MusicService? = null
    val handler = Handler(Looper.getMainLooper())
    private var playThread: Thread? = null
    private var prevThread: Thread? = null
    private var nextThread: Thread? = null

    private lateinit var backBtn : ImageView
    private lateinit var moreBtn : ImageView
    private lateinit var coverArt : ImageView
    private lateinit var songName : TextView
    private lateinit var heartBtn : ImageView
    private lateinit var songArtist : TextView
    private lateinit var durationPlayed: TextView
    private lateinit var durationTotal: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var shuffleBtn: ImageView
    private lateinit var prevBtn: ImageView
    private lateinit var playPauseBtn: ImageButton
    private lateinit var nextBtn: ImageView
    private lateinit var repeatBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)

        backBtn = findViewById(R.id.back_btn)
        moreBtn = findViewById(R.id.more_btn)
        coverArt = findViewById(R.id.cover_art)
        songName = findViewById(R.id.song_name)
        heartBtn = findViewById(R.id.heart_btn)
        songArtist = findViewById(R.id.song_artist)
        durationPlayed = findViewById(R.id.durationPlayed)
        durationTotal = findViewById(R.id.durationTotal)
        seekBar = findViewById(R.id.seekBar)
        shuffleBtn = findViewById(R.id.id_shuffle)
        prevBtn = findViewById(R.id.id_prev)
        playPauseBtn = findViewById(R.id.play_pause)
        nextBtn = findViewById(R.id.id_next)
        repeatBtn = findViewById(R.id.id_repeat)

        try {
            getIntentMethod()
        } catch (e: IOException) {
            throw java.lang.RuntimeException(e)
        }

        //Seek Bar
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (musicService != null && fromUser) {
                    musicService!!.seekTo(progress * 1000)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        this.runOnUiThread(object : Runnable {
            override fun run() {
                musicService?.let {
                    val mCurrentPosition = it.getCurrentPosition() / 1000
                    seekBar.progress = mCurrentPosition
                    durationPlayed.text = Util.formattedTime(mCurrentPosition)
                }
                handler.postDelayed(this, 1000)
            }
        })

        shuffleBtn.setOnClickListener {
            shuffleBoolean = !shuffleBoolean
            val shuffleIcon = if (shuffleBoolean) {
                R.drawable.icon_shuffle_on
            } else {
                R.drawable.icon_shuffle_off
            }
            shuffleBtn.setImageResource(shuffleIcon)
        }

        repeatBtn.setOnClickListener {
            repeatBoolean = !repeatBoolean
            val repeatIcon = if (repeatBoolean) {
                R.drawable.icon_repeat_on
            } else {
                R.drawable.icon_repeat
            }
            repeatBtn.setImageResource(repeatIcon)
        }
        backBtn.setOnClickListener {
            finish()
        }


    }

    override fun onResume() {
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, this, BIND_AUTO_CREATE)
        playThreadBtn()
        nextThreadBtn()
        prevThreadBtn()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
//        dialog?.dismiss()
        unbindService(this)
    }

    private fun prevThreadBtn() {
        prevThread = Thread {
            run {
                prevBtn.setOnClickListener {
                    try {
                        prevBtnClicked()
                    } catch (e: IOException) {
                        throw RuntimeException(e)
                    }
                }
            }
        }
        prevThread!!.start()
    }

    private fun mediaThread(i: Int) {
        musicService?.stop()
        musicService?.release()
        runOnUiThread {
            if (musicService != null) {
                seekBar.progress = 0
            }
        }
        if (i == 0) {
            musicService?.createMediaPlayer(position)
            musicService?.onCompleted()
        }
        try {
            musicService?.showNotification(R.drawable.icon_pause)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        if (i == 0) {
            musicService?.start()
        }
        seekBar.max = musicService?.getDuration()?.div(1000)!!
        loading = false
    }

    @Throws(IOException::class)
    override fun prevBtnClicked() {
        loading = true
        playPauseBtn.setImageResource(R.drawable.icon_pause)

        if (shuffleBoolean && !repeatBoolean) {
            position = getRandom(listSongs.size - 1)
        } else if (!shuffleBoolean && !repeatBoolean) {
            position = if (position - 1 < 0) listSongs.size - 1 else position - 1
        }

        uri = Uri.parse(listSongs[position].songLink)
//        metaData(uri, MediaMetadataRetriever())
        songName.text = listSongs[position].songTitle
        songArtist.text = listSongs[position].artist

        val myRunnable = Runnable {
            mediaThread(0)
        }
        val thread = Thread(myRunnable)
        thread.start()
    }

    private fun nextThreadBtn() {
        nextThread = Thread {
            nextBtn.setOnClickListener {
                try {
                    nextBtnClicked()
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }
        }
        nextThread!!.start()
    }

    @Throws(IOException::class)
    override fun nextBtnClicked() {
        loading = true
        playPauseBtn.setImageResource(R.drawable.icon_pause)

        position = when {
            shuffleBoolean && !repeatBoolean -> getRandom(listSongs.size - 1)
            else -> (position + 1) % listSongs.size
        }

        uri = Uri.parse(listSongs[position].songLink)
//        metaData(uri, MediaMetadataRetriever())
        songName.text = listSongs[position].songTitle
        songArtist.text = listSongs[position].artist

        val myRunnable = Runnable {
            mediaThread(0)
        }
        val thread = Thread(myRunnable)
        thread.start()
    }

    private fun getRandom(i: Int): Int {
        val random = Random()
        return random.nextInt(i + 1)
    }

    private fun playThreadBtn() {
        playThread = Thread {
            playPauseBtn.setOnClickListener {
                try {
                    playPauseBtnClicked()
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }
        }
        playThread?.start()
    }

    @Throws(IOException::class)
    override fun playPauseBtnClicked() {
        if (musicService?.isPlaying() == true) {
            playPauseBtn.setImageResource(R.drawable.icon_play)
            musicService?.showNotification(R.drawable.icon_play)
//            NowPlayingFragment.playPauseBtn.setImageResource(R.drawable.icon_play)
            musicService?.pause()
            seekBar.max = musicService!!.getDuration().div(1000) ?: 0
            runOnUiThread {
                musicService?.let {
                    val mCurrentPosition = it.getCurrentPosition() / 1000
                    seekBar.progress = mCurrentPosition
                }
            }
        } else {
            musicService?.showNotification(R.drawable.icon_pause)
            playPauseBtn.setImageResource(R.drawable.icon_pause)
//            NowPlayingFragment.playPauseBtn.setImageResource(R.drawable.ic_pause)
            musicService?.start()
            seekBar.max = musicService!!.getDuration().div(1000) ?: 0
            runOnUiThread {
                musicService?.let {
                    val mCurrentPosition = it.getCurrentPosition() / 1000
                    seekBar.progress = mCurrentPosition
                }
            }
        }
    }

    @Throws(IOException::class)
    override fun endClicked() {
        loading = true
        playPauseBtn.setImageResource(R.drawable.icon_pause)

        position = when {
            shuffleBoolean && !repeatBoolean -> getRandom(listSongs.size - 1)
            else -> (position + 1) % listSongs.size
        }

        uri = Uri.parse(listSongs[position].songLink)
//        metaData(uri, MediaMetadataRetriever())
        songName.text = listSongs[position].songTitle
        songArtist.text = listSongs[position].artist

        val myRunnable = Runnable {
            mediaThread(1)
        }
        val thread = Thread(myRunnable)
        thread.start()
    }

    @Throws(IOException::class)
    fun getIntentMethod() {
        position = intent.getIntExtra("position", -1)
        Log.d("PlayerActivity", "Received position: $position")
        sender = intent.getStringExtra("sender")

//        listSongs = when(sender) {
//            "albumDetails" -> AlbumDetailsAdapter.albumFiles
//            "local" -> LocalMusicAdapter.mFiles
//            "fire" -> MusicAdapter.mFiles
//            "rcmDetails" -> RecommendDetails.albumSongs
//            else -> null
//        }

        val db = MusicConnectDatabase.getDatabase(this)
        lifecycleScope.launch(Dispatchers.IO) {
             listSongs = db.musicFilesDao().getAllSongs() as ArrayList<MusicFiles>

            // Kiểm tra xem danh sách có dữ liệu không
            if (listSongs.isNotEmpty()) {
                if (position >= 0 && position < listSongs.size) {
                    withContext(Dispatchers.Main) {
                        playPauseBtn.setImageResource(R.drawable.icon_pause)
                        uri = Uri.parse(listSongs[position].songLink)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Log.e("MusicPlayerActivity", "Invalid position: $position")
                        Toast.makeText(this@MusicPlayerActivity, "Invalid position", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Log.e("MusicPlayerActivity", "No songs found")
                    Toast.makeText(this@MusicPlayerActivity, "No songs available", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val i = Intent(this, MusicService::class.java).apply {
            putExtra("servicePosition", position)
        }
        startService(i)
    }

//    private fun metaData(uri: Uri, retriever: MediaMetadataRetriever) {
//        retriever.setDataSource(uri.toString())
//        val duration_total = (listSongs[position].songDuration.toInt()) / 1000
//        durationTotal.text = Util.formattedTime(duration_total)
//
//        val art = retriever.embeddedPicture
//        val bitmap: Bitmap
//        if (art != null) {
//            bitmap = BitmapFactory.decodeByteArray(art, 0, art.size)
//            imageAnimation(this, coverArt, bitmap)
//
//            Palette.from(bitmap).generate { palette ->
//                val swatch = palette?.dominantSwatch
//                if (swatch != null) {
//                    val gradient = findViewById<ImageView>(R.id.imageViewGradient)
//                    val mContainer = findViewById<ConstraintLayout>(R.id.mContainer)
//                    gradient.setBackgroundResource(R.drawable.gradient_bg)
//                    mContainer.setBackgroundResource(R.drawable.main_bg)
//
//                    val gradientDrawable = GradientDrawable(
//                        GradientDrawable.Orientation.BOTTOM_TOP,
//                        intArrayOf(swatch.rgb, 0x00000000)
//                    )
//                    gradient.background = gradientDrawable
//
//                    val gradientDrawableBg = GradientDrawable(
//                        GradientDrawable.Orientation.BOTTOM_TOP,
//                        intArrayOf(swatch.rgb, swatch.rgb)
//                    )
//                    mContainer.background = gradientDrawableBg
//
//                    song_name.setTextColor(swatch.titleTextColor)
//                    artist_name.setTextColor(swatch.bodyTextColor)
//                } else {
//                    val gradient = findViewById<ImageView>(R.id.imageViewGradient)
//                    val mContainer = findViewById<ConstraintLayout>(R.id.mContainer)
//                    gradient.setBackgroundResource(R.drawable.gradient_bg)
//                    mContainer.setBackgroundResource(R.drawable.main_bg)
//
//                    val gradientDrawable = GradientDrawable(
//                        GradientDrawable.Orientation.BOTTOM_TOP,
//                        intArrayOf(0xff000000.toInt(), 0x00000000)
//                    )
//                    gradient.background = gradientDrawable
//
//                    val gradientDrawableBg = GradientDrawable(
//                        GradientDrawable.Orientation.BOTTOM_TOP,
//                        intArrayOf(0xff000000.toInt(), 0xff000000.toInt())
//                    )
//                    mContainer.background = gradientDrawableBg
//
//                    song_name.setTextColor(Color.WHITE)
//                    artist_name.setTextColor(Color.GRAY)
//                }
//            }
//        } else {
//            bitmap = BitmapFactory.decodeResource(resources, R.drawable.pic)
//            imageAnimation(this, cover_art, bitmap)
//
//            val gradient = findViewById<ImageView>(R.id.imageViewGradient)
//            val mContainer = findViewById<ConstraintLayout>(R.id.mContainer)
//            gradient.setBackgroundResource(R.drawable.gradient_bg)
//            mContainer.setBackgroundResource(R.drawable.main_bg)
//            song_name.setTextColor(Color.WHITE)
//            artist_name.setTextColor(Color.GRAY)
//        }
//    }

    fun imageAnimation(context: Context, imageView: ImageView, bitmap: Bitmap) {
        val animIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
        val animOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out)

        animOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                Glide.with(context).load(bitmap).into(imageView)

                animIn.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {}

                    override fun onAnimationEnd(animation: Animation?) {}

                    override fun onAnimationRepeat(animation: Animation?) {}
                })

                imageView.startAnimation(animIn)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        imageView.startAnimation(animOut)
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val myBinder = service as MusicService.MyBinder
        musicService = myBinder.getService()
        musicService!!.setCallBack(this)
        seekBar.max = musicService!!.getDuration() / 1000
//        metaData(uri, MediaMetadataRetriever())
        songName.text = listSongs[position].songTitle
        songArtist.text = listSongs[position].artist
        musicService!!.onCompleted()
        try {
            musicService!!.showNotification(R.drawable.icon_pause)
        } catch (_: IOException) {

        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }
}