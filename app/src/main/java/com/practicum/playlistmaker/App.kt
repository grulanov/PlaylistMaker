package com.practicum.playlistmaker

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.di.Creator
import com.practicum.playlistmaker.domain.api.AppThemeInteractor

class App: Application() {
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate() {
        super.onCreate()

        Creator.context = applicationContext
        setupAppTheme()
    }

    private fun setupAppTheme() {
        val appThemeInteractor = Creator.appThemeInteractor

        appThemeInteractor.addListener(object : AppThemeInteractor.AppThemeInteractorListener {
            override fun onAppThemeChange(isDarkTheme: Boolean) {
                handler.post {
                    AppCompatDelegate.setDefaultNightMode(
                        if (isDarkTheme) {
                            AppCompatDelegate.MODE_NIGHT_YES
                        } else {
                            AppCompatDelegate.MODE_NIGHT_NO
                        }
                    )
                }
            }
        })

        val uiModeManager = applicationContext.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        val isAppLaunchThemeDark = uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES
        appThemeInteractor.setUpInitialTheme(isAppLaunchThemeDark)
    }
}