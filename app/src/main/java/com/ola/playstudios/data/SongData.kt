package com.ola.playstudios.data

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by pankaj on 16/12/17.
 */
data class SongData(@JsonProperty("song") val song: String, @JsonProperty("url") val url: String, @JsonProperty("artists") val artists: String, @JsonProperty("cover_image") val coverImage: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(song)
        parcel.writeString(url)
        parcel.writeString(artists)
        parcel.writeString(coverImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongData> {
        override fun createFromParcel(parcel: Parcel): SongData {
            return SongData(parcel)
        }

        override fun newArray(size: Int): Array<SongData?> {
            return arrayOfNulls(size)
        }
    }
}