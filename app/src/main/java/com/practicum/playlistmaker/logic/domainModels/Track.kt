package com.practicum.playlistmaker.logic.domainModels

import android.os.Parcel
import android.os.Parcelable

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

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(trackId)
        parcel.writeString(trackName)
        parcel.writeString(artistName)
        parcel.writeString(collectionName)
        parcel.writeValue(releaseYear)
        parcel.writeString(primaryGenreName)
        parcel.writeString(country)
        parcel.writeString(trackTime)
        parcel.writeString(artworkUrl100)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Track> {
        override fun createFromParcel(parcel: Parcel): Track {
            return Track(parcel)
        }

        override fun newArray(size: Int): Array<Track?> {
            return arrayOfNulls(size)
        }
    }
}