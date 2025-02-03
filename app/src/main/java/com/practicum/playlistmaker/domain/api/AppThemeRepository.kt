package com.practicum.playlistmaker.domain.api

interface AppThemeRepository {
    var isDarkTheme: Boolean
    fun setUpInitialTheme(isAppLaunchThemeDark: Boolean)
}