package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.models.PlayerState

interface PlayerRepository {
    interface PlayerRepositoryListener {
        fun playerStateDidChange(state: PlayerState)
    }

    val state: PlayerState
    val currentPosition: Int

    fun addListener(listener: PlayerRepositoryListener)
    fun removeListener(listener: PlayerRepositoryListener)

    fun setDataSource(dataSource: String)

    fun start()
    fun pause()
    fun release()
}