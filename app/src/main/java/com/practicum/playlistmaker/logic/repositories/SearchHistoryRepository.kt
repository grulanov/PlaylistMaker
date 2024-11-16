package com.practicum.playlistmaker.logic.repositories

import com.practicum.playlistmaker.logic.domainModels.Track
import com.practicum.playlistmaker.logic.localDataProviders.SearchHistoryLocalDataProvider

interface SearchHistoryRepository {
    val tracksSearchHistory: List<Track>
    fun didSelectTrack(track: Track)
    fun clearHistory()

    companion object {
        fun create(): SearchHistoryRepository {
            return SearchHistoryRepositoryImpl(SearchHistoryLocalDataProvider.create())
        }
    }
}

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