package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.models.PlayerState

interface PlayerInteractor {
    interface PlayerInteractorListener {
        fun playerStateDidChange(state: PlayerState)
    }

    val state: PlayerState
    val currentPosition: Int

    fun addListener(listener: PlayerInteractorListener)
    fun removeListener(listener: PlayerInteractorListener)

    fun setDataSource(dataSource: String)

    fun start()
    fun pause()
    fun release()
}