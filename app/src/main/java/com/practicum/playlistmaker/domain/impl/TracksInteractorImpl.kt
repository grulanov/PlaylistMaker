package com.practicum.playlistmaker.domain.impl

import android.os.Handler
import android.os.Looper
import com.practicum.playlistmaker.domain.api.TracksInteractor
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.domain.models.Track
import java.util.concurrent.Executors

class TracksInteractorImpl(
    private val repository: TracksRepository
): TracksInteractor {
    private val executor = Executors.newCachedThreadPool()
    private val handler = Handler(Looper.getMainLooper())

    override fun searchTracks(query: String, callback: (Result<List<Track>>) -> Unit) {
        executor.execute {
            handler.post {
                callback(repository.searchTracks(query))
            }
        }
    }
}