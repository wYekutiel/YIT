package com.yekutiel.kutiweissyit.requestManager

import com.yekutiel.kutiweissyit.consts.Urls.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RequestManager {

    var imagesService: GetImagesService? = null

    init {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        imagesService = retrofit.create(GetImagesService::class.java)
    }

    companion object {
        private var instance: RequestManager? = null

        fun getInstance(): RequestManager? {
            if (instance == null) {
                instance = RequestManager()
            }
            return instance
        }
    }
}