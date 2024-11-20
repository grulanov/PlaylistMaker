package com.practicum.playlistmaker.logic.repositories

import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.logic.localDataProviders.AppThemeLocalDataProvider

interface AppThemeRepository {
    var isDarkTheme: Boolean

    companion object {
        fun create(isAppLaunchThemeDark: Boolean? = null): AppThemeRepository {
            return AppThemeRepositoryImpl(AppThemeLocalDataProvider.create(), isAppLaunchThemeDark)
        }

        fun setup(isAppLaunchThemeDark: Boolean) {
            create(isAppLaunchThemeDark)
        }
    }
}

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