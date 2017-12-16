package com.ola.playstudios.api

import com.ola.playstudios.constants.ApiConstants
import com.ola.playstudios.data.SongData
import io.reactivex.Single
import retrofit2.http.GET



/**
 * Created by pankaj on 16/12/17.
 */
interface PlayStudioService {

    @GET(ApiConstants.GEL_SONGS_LIST)
    fun getSongsList(): Single<List<SongData>>
}