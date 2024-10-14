package com.practicum.playlistmaker.Logic.RemoteDataProviders.TracksRemoteDataProvider

import com.practicum.playlistmaker.Logic.DomainModels.Track
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

interface TracksDtoMapper {
    fun map(from: SearchTracksResponseDto): List<Track>
}

class TracksDtoMapperImpl(
    private val dateFormat: DateFormat = SimpleDateFormat("mm:ss", Locale.getDefault() )
): TracksDtoMapper {
    override fun map(from: SearchTracksResponseDto): List<Track> {
        return from.results.map {
            Track(
                it.trackName,
                it.artistName,
                dateFormat.format(it.trackTimeMillis),
                it.artworkUrl100
            )
        }
    }
}