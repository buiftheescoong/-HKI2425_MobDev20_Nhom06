package com.example.soundnova.service

import com.example.soundnova.models.Albums
import com.example.soundnova.models.Artists
import com.example.soundnova.models.Tracks
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface DeezerApi {

    @GET("chart/0/tracks")
    suspend fun getPopularTracks() : Response<Tracks>

    @GET("chart/0/albums")
    suspend fun getPopularAlbums() : Response<Albums>

    @GET("chart/0/artists")
    suspend fun getPopularArtists() : Response<Artists>
}
object DeezerApiHelper {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.deezer.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(DeezerApi::class.java)

    @Throws(RuntimeException::class)
    suspend fun fetchPopularTracks(): Tracks {
        val response = api.getPopularTracks()
        if (response.isSuccessful) {
            return response.body() ?: throw RuntimeException("No data")
        } else {
            throw RuntimeException("API call failed: ${response.errorBody()?.string()}")
        }
    }

    @Throws(RuntimeException::class)
    suspend fun fetchPopularAlbums(): Albums {
        val response = api.getPopularAlbums()
        if (response.isSuccessful) {
            return response.body() ?: throw RuntimeException("No data")
        } else {
            throw RuntimeException("API call failed: ${response.errorBody()?.string()}")
        }
    }

    @Throws(RuntimeException::class)
    suspend fun fetchPopularArtists(): Artists {
        val response = api.getPopularArtists()
        if (response.isSuccessful) {
            return response.body() ?: throw RuntimeException("No data")
        } else {
            throw RuntimeException("API call failed: ${response.errorBody()?.string()}")
        }
    }
}

suspend fun main() {
    val deezerApiHelper = DeezerApiHelper
    println(deezerApiHelper.fetchPopularAlbums())
    println(deezerApiHelper.fetchPopularArtists())

}