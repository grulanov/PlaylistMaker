package com.practicum.playlistmaker.data.repositories

import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.data.localDataProviders.AppThemeLocalDataProvider
import com.practicum.playlistmaker.domain.api.AppThemeRepository

class AppThemeRepositoryImpl(
    private val localDataProvider: AppThemeLocalDataProvider
): AppThemeRepository {
    override var isDarkTheme: Boolean
        get() = localDataProvider.isDarkTheme ?: false
        set(value) {
            switchTheme(value)
        }

    override fun setUpInitialTheme(isAppLaunchThemeDark: Boolean) {
        val isDarkThemeEnabled = localDataProvider.isDarkTheme ?: isAppLaunchThemeDark
        switchTheme(isDarkThemeEnabled)
    }

    private fun switchTheme(isDarkThemeEnabled: Boolean) {
        localDataProvider.isDarkTheme = isDarkThemeEnabled
    }
}