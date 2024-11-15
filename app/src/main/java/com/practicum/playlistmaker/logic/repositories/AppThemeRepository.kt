package com.practicum.playlistmaker.logic.repositories

import com.practicum.playlistmaker.logic.localDataProviders.AppThemeLocalDataProvider
import com.practicum.playlistmaker.logic.localDataProviders.AppThemeLocalDataProviderImpl

interface AppThemeRepository {
    var isDarkTheme: Boolean?
}

class AppThemeRepositoryImpl(
    private val localDataProvider: AppThemeLocalDataProvider
): AppThemeRepository {
    companion object {
        fun create(): AppThemeRepository {
            return AppThemeRepositoryImpl(AppThemeLocalDataProviderImpl.create())
        }
    }

    override var isDarkTheme: Boolean?
        get() = localDataProvider.isDarkTheme
        set(value) {
            localDataProvider.isDarkTheme = value
        }
}