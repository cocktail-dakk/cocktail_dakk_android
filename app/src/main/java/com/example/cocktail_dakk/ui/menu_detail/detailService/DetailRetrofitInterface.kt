package com.example.cocktail_dakk.ui.menu_detail.detailService

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

interface DetailRetrofitInterface {

    @GET("/cocktaildakk/v1/cocktails/details")
    fun detail(@Header("auth")jwt : String,
               @Query("id",encoded = true) id : Int): Call<detailResponse>

    @POST("/cocktails/rating")
    fun rating(@Body detailrequest : DetailRequest): Call<DetailRatingResponse>

}
