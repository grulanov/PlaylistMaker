package com.practicum.playlistmaker.data.repositories

import android.media.MediaPlayer
import com.practicum.playlistmaker.domain.api.PlayerRepository
import com.practicum.playlistmaker.domain.api.PlayerRepository.PlayerRepositoryListener
import com.practicum.playlistmaker.domain.models.PlayerState

class PlayerRepositoryImpl(
    private val mediaPlayer: MediaPlayer
): PlayerRepository {
    private val listeners = mutableListOf<PlayerRepositoryListener>()

    override var state: PlayerState = PlayerState.IDLE
        private set(value) {
            field = value
            listeners.forEach { it.playerStateDidChange(value) }
        }

    override val currentPosition: Int
        get() = mediaPlayer.currentPosition

    override fun addListener(listener: PlayerRepositoryListener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: PlayerRepositoryListener) {
        listeners.remove(listener)
    }

    override fun setDataSource(dataSource: String) {
        mediaPlayer.setOnPreparedListener {
            state = PlayerState.PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            state = PlayerState.PREPARED
        }

        mediaPlayer.setDataSource(dataSource)
        mediaPlayer.prepareAsync()
    }

    override fun start() {
        mediaPlayer.start()
        state = PlayerState.PLAYING
    }

    override fun pause() {
        mediaPlayer.pause()
        state = PlayerState.PAUSED
    }

    override fun release() {
        mediaPlayer.release()
    }
}