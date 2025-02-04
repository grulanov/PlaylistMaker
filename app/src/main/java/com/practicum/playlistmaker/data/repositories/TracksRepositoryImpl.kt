package com.practicum.playlistmaker.data.repositories

import com.practicum.playlistmaker.domain.models.Track
import com.practicum.playlistmaker.data.dto.SearchTracksResponseDto
import com.practicum.playlistmaker.data.remoteDataProviders.TracksRemoteDataProvider
import com.practicum.playlistmaker.domain.api.TracksRepository
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class TracksRepositoryImpl(
    private val remoteDataProvider: TracksRemoteDataProvider
): TracksRepository {
    override fun searchTracks(query: String): Result<List<Track>> {
        val result = remoteDataProvider.searchTracks(query)
        return result.map { it.mapToTracksList() }
    }
}

private fun SearchTracksResponseDto.mapToTracksList(): List<Track> {
    val timeDateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())

    return this.results.map {
        Track(
            it.trackId,
            it.trackName,
            it.artistName,
            it.collectionName,
            ZonedDateTime.parse(it.releaseDate, DateTimeFormatter.ISO_DATE_TIME).year,
            it.primaryGenreName,
            it.country,
            timeDateFormat.format(it.trackTimeMillis),
            it.artworkUrl100,
            it.previewUrl
        )
    }
}