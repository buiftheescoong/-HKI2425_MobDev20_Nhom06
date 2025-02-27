package com.example.soundnova.service

import com.example.soundnova.models.AlbumsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DeezerAlbumApi {
    @GET("artist/{artist_id}/top?limit=50")
    suspend fun getAlbums(@Path("artist_id") artistID: String): Response<AlbumsResponse>
}
object DeezerAlbumApiHelper {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.deezer.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(DeezerAlbumApi::class.java)

    @Throws(RuntimeException::class)
    suspend fun fetchAlbums(artistID: String): AlbumsResponse {
        val response = api.getAlbums(artistID)
        if (response.isSuccessful) {
            return response.body() ?: throw RuntimeException("No data")
        } else {
            throw RuntimeException("API call failed: ${response.errorBody()?.string()}")
        }
    }
}