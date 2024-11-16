package com.practicum.playlistmaker.logic.repositories

import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.logic.localDataProviders.AppThemeLocalDataProvider

interface AppThemeRepository {
    var isDarkTheme: Boolean

    companion object {
        fun create(): AppThemeRepository {
            return AppThemeRepositoryImpl(AppThemeLocalDataProvider.create())
        }

        fun setup() {
            create()
        }
    }
}

class AppThemeRepositoryImpl(
    private val localDataProvider: AppThemeLocalDataProvider
): AppThemeRepository {
    override var isDarkTheme: Boolean
        get() = localDataProvider.isDarkTheme ?: isAppCurrentThemeDark
        set(value) {
            switchTheme(value)
        }

    private val isAppCurrentThemeDark: Boolean
        get() = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

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