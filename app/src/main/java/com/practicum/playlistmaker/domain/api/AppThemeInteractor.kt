package com.practicum.playlistmaker.domain.api

interface AppThemeInteractor {
    fun getDarkThemeState(callback: (Boolean) -> Unit)
    fun setDarkThemeState(isDarkTheme: Boolean)
}