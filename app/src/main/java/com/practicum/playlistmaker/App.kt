package com.practicum.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import com.practicum.playlistmaker.logic.repositories.AppThemeRepository

private const val PLAYLIST_MAKER_PREFERENCES = "playlist_maker_preferences"

class App: Application() {

    companion object {
        lateinit var sharedPrefs: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()

        sharedPrefs = getSharedPreferences(PLAYLIST_MAKER_PREFERENCES, MODE_PRIVATE)
        AppThemeRepository.setup()
    }
}