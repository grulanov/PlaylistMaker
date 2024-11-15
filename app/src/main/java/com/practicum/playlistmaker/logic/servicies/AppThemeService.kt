package com.practicum.playlistmaker.logic.servicies

import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.logic.repositories.AppThemeRepository
import com.practicum.playlistmaker.logic.repositories.AppThemeRepositoryImpl

interface AppThemeService {
    var isDarkTheme: Boolean
}

class AppThemeServiceImpl(
    private val appThemeRepository: AppThemeRepository
): AppThemeService {
    companion object {
        fun create(): AppThemeService {
            return AppThemeServiceImpl(AppThemeRepositoryImpl.create())
        }

        fun setup() {
            create()
        }
    }

    override var isDarkTheme: Boolean
        get() = appThemeRepository.isDarkTheme ?: isAppCurrentThemeDark
        set(value) {
            switchTheme(value)
        }

    private val isAppCurrentThemeDark: Boolean
        get() = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

    init {
        switchTheme(isDarkTheme)
    }

    private fun switchTheme(isDarkThemeEnabled: Boolean) {
        appThemeRepository.isDarkTheme = isDarkThemeEnabled

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}