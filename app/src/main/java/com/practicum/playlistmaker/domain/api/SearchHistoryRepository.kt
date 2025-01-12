package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.models.Track

interface SearchHistoryRepository {
    val tracksSearchHistory: List<Track>
    fun didSelectTrack(track: Track)
    fun clearHistory()
}