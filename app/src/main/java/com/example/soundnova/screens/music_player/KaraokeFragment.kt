package com.example.soundnova.screens.music_player

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.soundnova.R
import com.example.soundnova.databinding.KaraokeBinding
import com.example.soundnova.databinding.PlayerActivityBinding
import com.example.soundnova.databinding.RecordBinding
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import android.media.MediaExtractor
import android.media.MediaFormat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import java.nio.ByteBuffer

class KaraokeFragment : Fragment() {

    private lateinit var binding: KaraokeBinding
    private val viewModel: MusicPlayerViewModel by activityViewModels()
    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = KaraokeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = KaraokeBinding.bind(view)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentSongIndex.collect { index ->
                    if (index != -1) {
                        val song = viewModel.tracks.value.data[index]

                    }
                }
            }
        }
        // Request permissions
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100
            )
        }

        outputFile = "${requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC)}/recording.3gp"

        // Set up button listeners
        binding.btnMuteMic.setOnClickListener {
            startRecording()
        }

        binding.btnStop.setOnClickListener {
            stopRecording()
        }
    }

    private fun startRecording() {
        try {
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(outputFile)
                prepare()
                start()
            }
            binding.btnMuteMic.isEnabled = false
            binding.btnStop.isEnabled = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null

        // Lưu file vào thư mục công khai
        saveRecordingToPublicStorage()

        binding.btnMuteMic.isEnabled = true
        binding.btnStop.isEnabled = false

        try {
           // val currentSong = viewModel.tracks.value.data[viewModel.currentSongIndex.value]
            // Đường dẫn bài hát gốc và file ghi âm
            val songFilePath = outputFile // Cập nhật đường dẫn chính xác
            val recordingFilePath = outputFile

            // Chuyển đổi file sang dữ liệu PCM
            val songPcm = extractPCMData(songFilePath)
            val recordingPcm = extractPCMData(recordingFilePath)

            if (songPcm != null && recordingPcm != null) {
                // Phân tích cao độ
                val songPitch = calculatePitch(songPcm, 44100)
                val recordingPitch = calculatePitch(recordingPcm, 44100)

                // So sánh cao độ và chấm điểm
                val similarity = comparePitch(listOf(songPitch), listOf(recordingPitch))
                val score = calculateScore(similarity)
                binding.score.text = score.toString()

                // Hiển thị điểm trong giao diện
               // binding.tvScore.text = "Score: $score" // TextView trong `KaraokeBinding`
            } else {
                //binding.tvScore.text = "Error: Could not process audio files"
            }
        } catch (e: Exception) {
            e.printStackTrace()
           // binding.tvScore.text = "Error: ${e.message}"
        }
    }


    private fun saveRecordingToPublicStorage() {
        val sourceFile = File(outputFile)

        // Android 10 trở lên: Sử dụng MediaStore
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            val resolver = requireContext().contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.Audio.Media.DISPLAY_NAME, "recording_${System.currentTimeMillis()}.3gp")
                put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp")
                put(MediaStore.Audio.Media.RELATIVE_PATH, Environment.DIRECTORY_MUSIC)
            }

            val audioUri = resolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues)

            if (audioUri != null) {
                resolver.openOutputStream(audioUri)?.use { outputStream ->
                    sourceFile.inputStream().copyTo(outputStream)
                }
            }
        } else {
            // Android 9 trở xuống: Lưu trực tiếp vào thư mục công khai
            val publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
            if (!publicDir.exists()) publicDir.mkdirs()

            val destFile = File(publicDir, "recording_${System.currentTimeMillis()}.3gp")
            sourceFile.copyTo(destFile, overwrite = true)
        }
    }
    fun extractPCMData(audioFilePath: String): ByteArray? {
        val extractor = MediaExtractor()
        extractor.setDataSource(audioFilePath)
        for (i in 0 until extractor.trackCount) {
            val format = extractor.getTrackFormat(i)
            if (format.getString(MediaFormat.KEY_MIME)?.startsWith("audio/") == true) {
                extractor.selectTrack(i)
                val pcmData = mutableListOf<Byte>()
                val buffer = ByteBuffer.allocate(1024)
                while (true) {
                    val sampleSize = extractor.readSampleData(buffer, 0)
                    if (sampleSize < 0) break
                    pcmData.addAll(buffer.array().take(sampleSize))
                    extractor.advance()
                }
                extractor.release()
                return pcmData.toByteArray()
            }
        }
        extractor.release()
        return null
    }

    fun calculatePitch(pcmData: ByteArray, sampleRate: Int): Double {
        val normalized = pcmData.map { it / 128.0 }.toDoubleArray()
        val maxLag = sampleRate / 50 // Giới hạn để phát hiện âm thanh tần số thấp
        val minLag = sampleRate / 500 // Giới hạn âm thanh tần số cao

        var maxCorrelation = 0.0
        var bestLag = -1

        for (lag in minLag..maxLag) {
            var correlation = 0.0
            for (i in lag until normalized.size) {
                correlation += normalized[i] * normalized[i - lag]
            }
            if (correlation > maxCorrelation) {
                maxCorrelation = correlation
                bestLag = lag
            }
        }

        return if (bestLag > 0) sampleRate.toDouble() / bestLag else -1.0
    }

    fun comparePitch(songPitch: List<Double>, userPitch: List<Double>): Double {
        val minSize = minOf(songPitch.size, userPitch.size)
        var matchCount = 0
        for (i in 0 until minSize) {
            if (kotlin.math.abs(songPitch[i] - userPitch[i]) < 50) { // Sai số tối đa 50 Hz
                matchCount++
            }
        }
        return (matchCount.toDouble() / minSize) * 100
    }

    fun calculateScore(pitchSimilarity: Double): Int {
        return pitchSimilarity.toInt()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mediaRecorder?.release()
        mediaRecorder = null
    }
}
