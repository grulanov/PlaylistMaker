package com.practicum.playlistmaker.logic.remoteDataProviders.tracksRemoteDataProvider

import com.practicum.playlistmaker.logic.domainModels.Track
import java.text.SimpleDateFormat
import java.util.Locale

data class SearchTracksResponseDto(
    val results: List<SearchTrackDto>
) {
    data class SearchTrackDto(
        val trackId: Int,
        val trackName: String?,
        val artistName: String?,
        val trackTimeMillis: Long?,
        val artworkUrl100: String?
    )
}

fun SearchTracksResponseDto.mapToTracksList(): List<Track> {
    val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())

    return this.results.map {
        Track(
            it.trackId,
            it.trackName,
            it.artistName,
            dateFormat.format(it.trackTimeMillis),
            it.artworkUrl100
        )
    }
}