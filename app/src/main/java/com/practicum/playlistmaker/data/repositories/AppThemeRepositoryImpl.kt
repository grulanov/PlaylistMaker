package com.practicum.playlistmaker.data.repositories

import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.data.localDataProviders.AppThemeLocalDataProvider
import com.practicum.playlistmaker.domain.api.AppThemeRepository

class AppThemeRepositoryImpl(
    private val localDataProvider: AppThemeLocalDataProvider,
    private val isAppLaunchThemeDark: Boolean?
): AppThemeRepository {
    override var isDarkTheme: Boolean
        get() = localDataProvider.isDarkTheme ?: isAppLaunchThemeDark ?: false
        set(value) {
            switchTheme(value)
        }

    init {
        switchTheme(isDarkTheme)
    }

    private fun switchTheme(isDarkThemeEnabled: Boolean) {
        localDataProvider.isDarkTheme = isDarkThemeEnabled

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}