package com.example.soundnova.file

import android.content.Context
import android.media.MediaPlayer
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit


//fun sendSongUrlToServer(songUrl: String, serverUrl: String, onComplete: (String?) -> Unit) {
//    val client = OkHttpClient()
//
//    try {
//        // Tạo body JSON gửi kèm URL bài hát
//        val jsonBody = JSONObject().apply {
//            put("url", songUrl)
//        }
//
//        val body = RequestBody.create(
//            "application/json; charset=utf-8".toMediaTypeOrNull(),
//            jsonBody.toString()
//        )
//
//        val request = Request.Builder()
//            .url(serverUrl)
//            .post(body)
//            .build()
//
//        // Gửi request
//        Thread {
//            try {
//                val response = client.newCall(request).execute()
//
//                // Kiểm tra mã phản hồi HTTP
//                if (response.isSuccessful) {
//                    val responseBody = response.body?.string()
//                    onComplete(responseBody)
//                } else {
//                    // Nếu HTTP response không thành công
//                    onComplete("Error: ${response.message}")
//                }
//            } catch (e: IOException) {
//                // Xử lý lỗi kết nối mạng hoặc vấn đề với HTTP request
//                e.printStackTrace()
//                onComplete("Network error: ${e.message}")
//            } catch (e: Exception) {
//                // Xử lý các ngoại lệ khác (như lỗi đọc dữ liệu, lỗi không xác định...)
//                e.printStackTrace()
//                onComplete("Request failed: ${e.message}")
//            }
//        }.start()
//    } catch (e: Exception) {
//        // Xử lý ngoại lệ khi tạo JSON hoặc RequestBody
//        e.printStackTrace()
//        onComplete("Error in creating request: ${e.message}")
//    }
//}
//
//fun downloadKaraokeTrack(karaokeUrl: String, filePath: String, onComplete: (Boolean) -> Unit) {
//    val client = OkHttpClient()
//    val request = Request.Builder().url(karaokeUrl).build()
//
//    Thread {
//        try {
//            val response = client.newCall(request).execute()
//            if (response.isSuccessful) {
//                val inputStream: InputStream = response.body!!.byteStream()
//                val outputStream = FileOutputStream(File(filePath))
//
//                inputStream.copyTo(outputStream)
//                outputStream.flush()
//                outputStream.close()
//                inputStream.close()
//
//                onComplete(true)
//            } else {
//                onComplete(false)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            onComplete(false)
//        }
//    }.start()
//}
//
//fun playKaraokeTrack(filePath: String) {
//    val mediaPlayer = MediaPlayer()
//    try {
//        mediaPlayer.setDataSource(filePath)
//        mediaPlayer.prepare()
//        mediaPlayer.start()
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}


suspend fun sendSongUrlToServer(id :Long, songUrl: String, serverUrl: String, context: Context): String? {
    val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()


    val requestBody = mapOf("url" to songUrl)
    val jsonBody = Gson().toJson(requestBody)

    val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), jsonBody)

    val request = Request.Builder()
        .url(serverUrl)
        .post(body)
        .build()

    return withContext(Dispatchers.IO) {
        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val karaokeTrackBytes = response.body?.bytes()
                if (karaokeTrackBytes != null) {
                    val file = File(context.getExternalFilesDir(null), "karaoke_${id}.wav")
                    FileOutputStream(file).use { it.write(karaokeTrackBytes) }
                    file.absolutePath
                } else null
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}


suspend fun playKaraokeTrack(karaokeTrack: ByteArray) {
    val mediaPlayer = MediaPlayer()
    try {
        val tempFile = File.createTempFile("karaoke_track", ".wav")
        tempFile.deleteOnExit()

        val outputStream = FileOutputStream(tempFile)
        outputStream.write(karaokeTrack)
        outputStream.close()

        mediaPlayer.setDataSource(tempFile.absolutePath)
        mediaPlayer.prepare()
        mediaPlayer.start()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}




