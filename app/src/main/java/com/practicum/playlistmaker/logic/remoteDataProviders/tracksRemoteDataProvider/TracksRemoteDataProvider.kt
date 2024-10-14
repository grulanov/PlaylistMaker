package com.practicum.playlistmaker.logic.remoteDataProviders.tracksRemoteDataProvider

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TracksRemoteDataProvider {
    @GET("/search?entity=song")
    fun searchTracks(
        @Query("term") text: String
    ): Call<SearchTracksResponseDto>
}