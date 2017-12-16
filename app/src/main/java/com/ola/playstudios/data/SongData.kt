package com.ola.playstudios.data

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by pankaj on 16/12/17.
 */
data class SongData(@JsonProperty("song") val song: String, @JsonProperty("url") val url: String, @JsonProperty("artists") val artists: String, @JsonProperty("cover_image") val coverImage: String)