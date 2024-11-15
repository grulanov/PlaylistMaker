package com.practicum.playlistmaker.logic.localDataProviders

import android.content.SharedPreferences
import com.practicum.playlistmaker.App

interface AppThemeLocalDataProvider {
    var isDarkTheme: Boolean?
}

class AppThemeLocalDataProviderImpl(
    private val sharedPrefs: SharedPreferences
): AppThemeLocalDataProvider {
    companion object {
        const val DARK_THEME_SETTING_KEY = "dark_theme_setting_key"

        fun create(): AppThemeLocalDataProvider {
            return AppThemeLocalDataProviderImpl(App.sharedPrefs)
        }
    }

    override var isDarkTheme: Boolean?
        get() {
            return if (sharedPrefs.contains(DARK_THEME_SETTING_KEY)) {
                sharedPrefs.getBoolean(DARK_THEME_SETTING_KEY, false)
            } else {
                null
            }
        }
        set(value) {
            sharedPrefs
                .edit()
                .putBoolean(DARK_THEME_SETTING_KEY, value ?: false)
                .apply()
        }
}