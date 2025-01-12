package com.practicum.playlistmaker.data.remoteDataProviders

import com.practicum.playlistmaker.data.common.RequestException
import com.practicum.playlistmaker.data.dto.SearchTracksResponseDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

private interface TracksRemoteApiService {
    @GET("/search?entity=song")
    fun searchTracks(
        @Query("term") text: String
    ): Call<SearchTracksResponseDto>
}

interface TracksRemoteDataProvider {
    fun searchTracks(query: String): Result<SearchTracksResponseDto>
}

class TracksRemoteDataProviderImpl: TracksRemoteDataProvider {
    private val itunesBaseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val itunesService = retrofit.create(TracksRemoteApiService::class.java)

    override fun searchTracks(query: String): Result<SearchTracksResponseDto> {
        try {
            val response = itunesService.searchTracks(query).execute()

            val resultModel = response.body()
            if (response.isSuccessful && resultModel != null) {
                return Result.success(resultModel)
            } else {
                return Result.failure(RequestException.RequestError(response.code(), response.errorBody().toString()))
            }
        } catch (e: IOException) {
            return Result.failure(RequestException.NetworkError(e))
        } catch (e: RuntimeException) {
            return Result.failure(RequestException.DecodingError(e))
        }
    }
}