package com.practicum.playlistmaker.data.dto

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
        val artworkUrl100: String?,
        val previewUrl: String
    )
}