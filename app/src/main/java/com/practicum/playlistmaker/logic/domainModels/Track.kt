package com.practicum.playlistmaker.logic.domainModels

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Track(
    val trackId: Int,
    val trackName: String?,
    val artistName: String?,
    val collectionName: String?,
    val releaseYear: Int?,
    val primaryGenreName: String?,
    val country: String?,
    val trackTime: String?,
    val artworkUrl100: String?
): Parcelable {
    val artworkUrl500: String?
        get() = artworkUrl100?.replaceAfterLast('/',"512x512bb.jpg")
}