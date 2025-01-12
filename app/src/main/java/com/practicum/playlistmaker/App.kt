package com.practicum.playlistmaker

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import android.content.SharedPreferences
import com.practicum.playlistmaker.di.Creator

private const val PLAYLIST_MAKER_PREFERENCES = "playlist_maker_preferences"

class App: Application() {

    companion object {
        lateinit var sharedPrefs: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()

        sharedPrefs = getSharedPreferences(PLAYLIST_MAKER_PREFERENCES, MODE_PRIVATE)

        val uiModeManager = applicationContext.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        val isAppLaunchThemeDark = uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES
        Creator.setupAppTheme(isAppLaunchThemeDark)
    }
}