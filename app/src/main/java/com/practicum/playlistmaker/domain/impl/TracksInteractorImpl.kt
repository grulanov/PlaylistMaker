package com.practicum.playlistmaker.domain.impl

import com.practicum.playlistmaker.domain.api.TracksInteractor
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.domain.models.Track
import java.util.concurrent.Executors

class TracksInteractorImpl(
    private val repository: TracksRepository
): TracksInteractor {
    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks(query: String, callback: (Result<List<Track>>) -> Unit) {
        executor.execute {
            callback(repository.searchTracks(query))
        }
    }
}