package com.example.soundnova.screens.music_player

import android.Manifest
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
import com.example.soundnova.databinding.KaraokeBinding
import java.io.File
import java.io.IOException

class KaraokeFragment : Fragment() {

    private var _binding: KaraokeBinding? = null
    private val binding get() = _binding!!

    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =KaraokeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mediaRecorder?.release()
        mediaRecorder = null
    }
}
