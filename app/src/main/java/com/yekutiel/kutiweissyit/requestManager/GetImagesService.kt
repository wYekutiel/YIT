package com.yekutiel.kutiweissyit.requestManager

import com.yekutiel.kutiweissyit.consts.Urls.API_KEY
import com.yekutiel.kutiweissyit.consts.Urls.IMAGES_PER_PAGE
import com.yekutiel.kutiweissyit.models.ImagesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetImagesService {

    @GET("?key=$API_KEY&per_page=$IMAGES_PER_PAGE")
    fun getImages(
        @Query("q") searchText: String?,
        @Query("page") page: Int
    ): Call<ImagesResponse>

}