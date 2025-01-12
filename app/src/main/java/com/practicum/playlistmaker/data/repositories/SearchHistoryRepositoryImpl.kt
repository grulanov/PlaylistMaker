package com.practicum.playlistmaker.data.repositories

import com.practicum.playlistmaker.domain.models.Track
import com.practicum.playlistmaker.data.localDataProviders.SearchHistoryLocalDataProvider
import com.practicum.playlistmaker.domain.api.SearchHistoryRepository

class SearchHistoryRepositoryImpl(
    private val localDataProvider: SearchHistoryLocalDataProvider
): SearchHistoryRepository {
    override val tracksSearchHistory: List<Track>
        get() = localDataProvider.tracksSearchHistory

    override fun didSelectTrack(track: Track) {
        val newTrackSearchHistory = tracksSearchHistory.toMutableList()
        newTrackSearchHistory.removeAll { it.trackId == track.trackId }
        newTrackSearchHistory.add(0, track)
        localDataProvider.tracksSearchHistory = newTrackSearchHistory.take(10)
    }

    override fun clearHistory() {
        localDataProvider.tracksSearchHistory = emptyList()
    }
}