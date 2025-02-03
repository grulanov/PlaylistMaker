package com.practicum.playlistmaker.domain.api

interface AppThemeInteractor {
    interface AppThemeInteractorListener {
        fun onAppThemeChange(isDarkTheme: Boolean)
    }

    fun setUpInitialTheme(isAppLaunchThemeDark: Boolean)

    fun addListener(listener: AppThemeInteractorListener)
    fun removeListener(listener: AppThemeInteractorListener)

    fun getDarkThemeState(callback: (Boolean) -> Unit)
    fun setDarkThemeState(isDarkTheme: Boolean)
}