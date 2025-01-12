package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.models.Track

interface SearchHistoryInteractor {
    fun getTracksSearchHistory(callback: (List<Track>) -> Unit)
    fun didSelectTrack(track: Track)
    fun clearHistory()
}