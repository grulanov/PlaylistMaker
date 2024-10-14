package com.practicum.playlistmaker.Logic.Repositories

import com.practicum.playlistmaker.Logic.Common.RepositoryException
import com.practicum.playlistmaker.Logic.Common.RepositoryRequestCallback
import com.practicum.playlistmaker.Logic.DomainModels.Track
import com.practicum.playlistmaker.Logic.RemoteDataProviders.TracksRemoteDataProvider.SearchTracksResponseDto
import com.practicum.playlistmaker.Logic.RemoteDataProviders.TracksRemoteDataProvider.TracksDtoMapper
import com.practicum.playlistmaker.Logic.RemoteDataProviders.TracksRemoteDataProvider.TracksDtoMapperImpl
import com.practicum.playlistmaker.Logic.RemoteDataProviders.TracksRemoteDataProvider.TracksRemoteDataProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface TracksRepository {
    fun searchTracks(query: String, callback: RepositoryRequestCallback<List<Track>>)

    companion object {
        fun create(): TracksRepository {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://itunes.apple.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return TracksRepositoryImpl(retrofit.create(TracksRemoteDataProvider::class.java), TracksDtoMapperImpl())
        }
    }
}

class TracksRepositoryImpl(
    private val remoteDataProvider: TracksRemoteDataProvider,
    private val tracksDtoMapper: TracksDtoMapper
): TracksRepository {
    override fun searchTracks(query: String, callback: RepositoryRequestCallback<List<Track>>) {
        remoteDataProvider.searchTracks(query)
            .enqueue(object : Callback<SearchTracksResponseDto> {
                override fun onResponse(
                    call: Call<SearchTracksResponseDto>,
                    response: Response<SearchTracksResponseDto>
                ) {
                    val resultModel = response.body()
                    if (response.isSuccessful && resultModel != null) {
                        val tracks = tracksDtoMapper.map(resultModel)
                        callback.onResult(Result.success(tracks))
                    } else {
                        callback.onResult(Result.failure(RepositoryException.RequestError(response.code(), response.errorBody().toString())))
                    }
                }

                override fun onFailure(call: Call<SearchTracksResponseDto>, t: Throwable) {
                    callback.onResult(Result.failure(RepositoryException.NetworkError(t)))
                }
            })
    }
}