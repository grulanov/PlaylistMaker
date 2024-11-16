package com.practicum.playlistmaker.logic.domainModels

data class Track(
    val trackId: Int,
    val trackName: String?,
    val artistName: String?,
    val trackTime: String?,
    val artworkUrl100: String?
)