package com.practicum.playlistmaker.Logic.RemoteDataProviders.TracksRemoteDataProvider

data class SearchTracksResponseDto(
    val results: List<SearchTrackDto>
) {
    data class SearchTrackDto(
        val trackName: String?,
        val artistName: String?,
        val trackTimeMillis: Long?,
        val artworkUrl100: String?
    )
}