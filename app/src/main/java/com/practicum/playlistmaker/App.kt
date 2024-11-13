package com.practicum.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

const val PLAYLIST_MAKER_PREFERENCES = "playlist_maker_preferences"
const val DARK_THEME_SETTING_KEY = "dark_theme_setting_key"

class App: Application() {

    var isDarkTheme = false
        private set

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        sharedPrefs = getSharedPreferences(PLAYLIST_MAKER_PREFERENCES, MODE_PRIVATE)
        setupInitialDarkThemeValue()
    }

    fun switchTheme(isDarkThemeEnabled: Boolean) {
        isDarkTheme = isDarkThemeEnabled

        sharedPrefs
            .edit()
            .putBoolean(DARK_THEME_SETTING_KEY, isDarkTheme)
            .apply()

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    private fun setupInitialDarkThemeValue() {
        val isDarkTheme = if (sharedPrefs.contains(DARK_THEME_SETTING_KEY)) {
            sharedPrefs.getBoolean(DARK_THEME_SETTING_KEY, false)
        } else {
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        }
        switchTheme(isDarkTheme)
    }
}