package com.example.soundnova.service

import com.example.soundnova.models.Artists
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DeezerArtistApi {
    @GET("genre/{genre_id}/artists")
    suspend fun getArtists(@Path("genre_id") genreId: String): Response<Artists>

}
object DeezerArtistApiHelper {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.deezer.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(DeezerArtistApi::class.java)

    @Throws(RuntimeException::class)
    suspend fun fetchArtists(genreId: String):Artists{
        val response = api.getArtists(genreId)
        if (response.isSuccessful) {
            return response.body() ?: throw RuntimeException("No data")
        } else {
            throw RuntimeException("API call failed: ${response.errorBody()?.string()}")
        }
    }
}
