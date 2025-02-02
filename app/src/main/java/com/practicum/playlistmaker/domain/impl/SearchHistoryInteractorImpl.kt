package com.practicum.playlistmaker.domain.impl

import com.practicum.playlistmaker.domain.api.SearchHistoryInteractor
import com.practicum.playlistmaker.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.domain.models.Track
import java.util.concurrent.Executors

class SearchHistoryInteractorImpl(
    private val repository: SearchHistoryRepository
): SearchHistoryInteractor {
    private val executor = Executors.newCachedThreadPool()

    override fun getTracksSearchHistory(callback: (List<Track>) -> Unit) {
        executor.execute {
            callback(repository.tracksSearchHistory)
        }
    }

    override fun didSelectTrack(track: Track) {
        executor.execute {
            repository.didSelectTrack(track)
        }
    }

    override fun clearHistory() {
        executor.execute {
            repository.clearHistory()
        }
    }
}