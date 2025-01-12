package com.practicum.playlistmaker.domain.impl

import android.os.Handler
import android.os.Looper
import com.practicum.playlistmaker.domain.api.AppThemeInteractor
import com.practicum.playlistmaker.domain.api.AppThemeRepository
import java.util.concurrent.Executors

class AppThemeInteractorImpl(
    private val repository: AppThemeRepository
): AppThemeInteractor {
    private val executor = Executors.newCachedThreadPool()
    private val handler = Handler(Looper.getMainLooper())

    override fun getDarkThemeState(callback: (Boolean) -> Unit) {
        executor.execute {
            handler.post {
                callback(repository.isDarkTheme)
            }
        }
    }

    override fun setDarkThemeState(isDarkTheme: Boolean) {
        executor.execute {
            repository.isDarkTheme = isDarkTheme
        }
    }
}