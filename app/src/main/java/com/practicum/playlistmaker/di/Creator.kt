package com.practicum.playlistmaker.di

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.media.MediaPlayer
import com.practicum.playlistmaker.data.localDataProviders.AppThemeLocalDataProvider
import com.practicum.playlistmaker.data.localDataProviders.AppThemeLocalDataProviderImpl
import com.practicum.playlistmaker.data.localDataProviders.SearchHistoryLocalDataProvider
import com.practicum.playlistmaker.data.localDataProviders.SearchHistoryLocalDataProviderImpl
import com.practicum.playlistmaker.data.remoteDataProviders.TracksRemoteDataProvider
import com.practicum.playlistmaker.data.remoteDataProviders.TracksRemoteDataProviderImpl
import com.practicum.playlistmaker.data.repositories.AppThemeRepositoryImpl
import com.practicum.playlistmaker.data.repositories.PlayerRepositoryImpl
import com.practicum.playlistmaker.data.repositories.SearchHistoryRepositoryImpl
import com.practicum.playlistmaker.data.repositories.TracksRepositoryImpl
import com.practicum.playlistmaker.domain.api.AppThemeInteractor
import com.practicum.playlistmaker.domain.api.AppThemeRepository
import com.practicum.playlistmaker.domain.api.PlayerInteractor
import com.practicum.playlistmaker.domain.api.PlayerRepository
import com.practicum.playlistmaker.domain.api.SearchHistoryInteractor
import com.practicum.playlistmaker.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.domain.api.TracksInteractor
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.domain.impl.AppThemeInteractorImpl
import com.practicum.playlistmaker.domain.impl.PlayerInteractorImpl
import com.practicum.playlistmaker.domain.impl.SearchHistoryInteractorImpl
import com.practicum.playlistmaker.domain.impl.TracksInteractorImpl

@SuppressLint("StaticFieldLeak")
object Creator {
    private const val PLAYLIST_MAKER_PREFERENCES = "playlist_maker_preferences"

    lateinit var context: Context

    private fun createSharedPrefs(): SharedPreferences {
        return context.getSharedPreferences(PLAYLIST_MAKER_PREFERENCES, MODE_PRIVATE)
    }

    private fun createAppThemeLocalDataProvider(): AppThemeLocalDataProvider {
        return AppThemeLocalDataProviderImpl(createSharedPrefs())
    }

    private fun createSearchHistoryLocalDataProvider(): SearchHistoryLocalDataProvider {
        return SearchHistoryLocalDataProviderImpl(createSharedPrefs())
    }

    private fun createTracksRemoteDataProvider(): TracksRemoteDataProvider {
        return TracksRemoteDataProviderImpl()
    }

    private fun createAppThemeRepository(): AppThemeRepository {
        return AppThemeRepositoryImpl(createAppThemeLocalDataProvider())
    }

    private fun createSearchHistoryRepository(): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(createSearchHistoryLocalDataProvider())
    }

    private fun createTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(createTracksRemoteDataProvider())
    }

    private fun createPlayerRepository(): PlayerRepository {
        return PlayerRepositoryImpl(MediaPlayer())
    }

    val appThemeInteractor: AppThemeInteractor by lazy {
        AppThemeInteractorImpl(createAppThemeRepository())
    }

    fun createSearchHistoryInteractor(): SearchHistoryInteractor {
        return SearchHistoryInteractorImpl(createSearchHistoryRepository())
    }

    fun createTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(createTracksRepository())
    }

    fun createPlayerInteractor(): PlayerInteractor {
        return PlayerInteractorImpl(createPlayerRepository())
    }
}