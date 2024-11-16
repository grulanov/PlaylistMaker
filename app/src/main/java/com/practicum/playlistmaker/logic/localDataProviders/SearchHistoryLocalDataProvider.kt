package com.practicum.playlistmaker.logic.localDataProviders

import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmaker.App
import com.practicum.playlistmaker.logic.domainModels.Track

interface SearchHistoryLocalDataProvider {
    var tracksSearchHistory: List<Track>

    companion object {
        fun create(): SearchHistoryLocalDataProvider {
            return SearchHistoryLocalDataProviderImpl(App.sharedPrefs)
        }
    }
}

class SearchHistoryLocalDataProviderImpl(
    private val sharedPrefs: SharedPreferences
): SearchHistoryLocalDataProvider {
    companion object {
        const val TRACKS_SEARCH_HISTORY_KEY = "tracks_search_history_key"
    }

    override var tracksSearchHistory: List<Track>
        get() {
            val tracksSearchHistory = sharedPrefs.getString(TRACKS_SEARCH_HISTORY_KEY, null)
            return if (tracksSearchHistory == null) {
                emptyList()
            } else {
                createTracksFromJson(tracksSearchHistory)
            }
        }
        set(value) {
            val tracksSearchHistoryJson = createJsonFromTracks(value)
            sharedPrefs.edit()
                .putString(TRACKS_SEARCH_HISTORY_KEY, tracksSearchHistoryJson)
                .apply()
        }

    private fun createJsonFromTracks(tracks: List<Track>): String {
        return Gson().toJson(tracks)
    }

    private fun createTracksFromJson(json: String): List<Track> {
        return Gson().fromJson(json, Array<Track>::class.java).asList()
    }
}