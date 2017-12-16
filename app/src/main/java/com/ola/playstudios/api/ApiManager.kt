package com.ola.playstudios.api

import com.ola.playstudios.constants.ApiConstants
import com.ola.playstudios.data.SongData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory


/**
 * Created by pankaj on 16/12/17.
 */
class ApiManager {
    companion object {

        fun getSongsList(): Single<List<SongData>> {
            return getRetrofitService().getSongsList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        private fun getRetrofitService(): PlayStudioService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build()
            return retrofit.create(PlayStudioService::class.java)
        }
    }
}