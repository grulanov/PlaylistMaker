package com.practicum.playlistmaker.logic.remoteDataProviders.tracksRemoteDataProvider

import com.practicum.playlistmaker.logic.domainModels.Track
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class SearchTracksResponseDto(
    val results: List<SearchTrackDto>
) {
    data class SearchTrackDto(
        val trackId: Int,
        val trackName: String?,
        val artistName: String?,
        val collectionName: String?,
        val releaseDate: String?,
        val primaryGenreName: String?,
        val country: String?,
        val trackTimeMillis: Long?,
        val artworkUrl100: String?
    )
}

fun SearchTracksResponseDto.mapToTracksList(): List<Track> {
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
            it.artworkUrl100
        )
    }
}