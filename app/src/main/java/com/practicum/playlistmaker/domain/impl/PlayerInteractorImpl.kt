package com.practicum.playlistmaker.domain.impl

import com.practicum.playlistmaker.domain.api.PlayerInteractor
import com.practicum.playlistmaker.domain.api.PlayerInteractor.PlayerInteractorListener
import com.practicum.playlistmaker.domain.api.PlayerRepository
import com.practicum.playlistmaker.domain.models.PlayerState

class PlayerInteractorImpl(
    private val playerRepository: PlayerRepository
): PlayerInteractor {
    private val listeners = mutableListOf<PlayerInteractorListener>()

    override val state: PlayerState
        get() = playerRepository.state

    override val currentPosition: Int
        get() = playerRepository.currentPosition

    init {
        playerRepository.addListener(object : PlayerRepository.PlayerRepositoryListener {
            override fun onPlayerStateChange(state: PlayerState) {
                listeners.forEach { it.onPlayerStateChange(state) }
            }
        })
    }

    override fun addListener(listener: PlayerInteractorListener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: PlayerInteractorListener) {
        listeners.remove(listener)
    }

    override fun setDataSource(dataSource: String) {
        playerRepository.setDataSource(dataSource)
    }

    override fun start() {
        playerRepository.start()
    }

    override fun pause() {
        playerRepository.pause()
    }

    override fun release() {
        playerRepository.release()
    }
}