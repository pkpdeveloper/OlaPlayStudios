package com.ola.playstudios.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by pankaj on 16/12/17.
 */
@Entity(tableName = "song_data")
data class SongData(
        @JsonProperty("id") @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        @JsonProperty("song") @ColumnInfo(name = "song") val song: String,
        @JsonProperty("url") @ColumnInfo(name = "url") val url: String,
        @JsonProperty("artists") @ColumnInfo(name = "artists") val artists: String,
        @JsonProperty("cover_image") @ColumnInfo(name = "cover_image") val coverImage: String,
        @JsonProperty("timestamp") @ColumnInfo(name = "timestamp") var timestamp: Long) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readLong(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readLong()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeLong(id)
                parcel.writeString(song)
                parcel.writeString(url)
                parcel.writeString(artists)
                parcel.writeString(coverImage)
                parcel.writeLong(timestamp)
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