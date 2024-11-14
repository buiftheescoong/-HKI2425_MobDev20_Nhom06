package com.example.soundnova

import java.io.IOException
import android.util.Log
import okhttp3.*
import com.google.gson.Gson
import com.google.gson.JsonObject


object SpotifyService {
    private const val CLIENT_ID = "7008abcae0f242baa417606b2761c2dd"
    private const val CLIENT_SECRET = "b3dddc7a51c04d51bd608f6282022a3f"
    private const val TOKEN_URL = "https://accounts.spotify.com/api/token"
    private const val RECOMMENDATIONS_URL = "https://api.spotify.com/v1/recommendations"


    private var accessToken: String? = null

    fun getAccessToken(callback: (String?) -> Unit) {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("grant_type", "client_credentials")
            .build()

        val request = Request.Builder()
            .url(TOKEN_URL)
            .post(formBody)
            .header("Authorization", Credentials.basic(CLIENT_ID, CLIENT_SECRET))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("SpotifyService", "Failed to get access token", e)
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val gson = Gson()
                    val jsonObject = gson.fromJson(it, JsonObject::class.java)
                    accessToken = jsonObject.get("access_token").asString
                    callback(accessToken)
                }
            }
        })
    }


    fun fetchRecommendations(genre: String, callback: (List<Song>) -> Unit) {
        val client = OkHttpClient()
        val url = "$RECOMMENDATIONS_URL?seed_genres=$genre&limit=10"
        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $accessToken")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("SpotifyService", "Failed to fetch recommendations", e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val gson = Gson()
                    val jsonObject = gson.fromJson(it, JsonObject::class.java)
                    val tracks = jsonObject.getAsJsonArray("tracks")
                    val songs = mutableListOf<Song>()

                    for (i in 0 until tracks.size()) {
                        val track = tracks.get(i).asJsonObject
                        val name = track.get("name").asString
                        val artist = track.getAsJsonArray("artists").get(0).asJsonObject.get("name").asString
                        val imageUrl = track.getAsJsonObject("album").getAsJsonArray("images").get(0).asJsonObject.get("url").asString
                        songs.add(Song(name, artist, imageUrl))
                    }
                    callback(songs)
                }
            }        })
    }
}

fun main() {
    val spotifyService = SpotifyService
    SpotifyService.getAccessToken { accessToken ->
        if (accessToken != null) {
            SpotifyService.fetchRecommendations("pop") { songs ->
                songs.forEach {
                    println("Song: ${it.name} by ${it.artist}")
                }
            }
        } else {
            Log.e("SpotifyService", "Failed to get access token")
        }
    }
}