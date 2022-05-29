package com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService

import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import retrofit2.Call
import retrofit2.http.*

interface DetailRetrofitInterface {

    @GET("/cocktaildakk/v1/cocktails/details")
    fun detail(@Query("id",encoded = true) id : Int): Call<ResponseWrapper<detail_Cocktail>>

    @POST("/cocktaildakk/v1/cocktails/rating")
    fun rating(@Body detailrequest : DetailRequest): Call<ResponseWrapper<ratingResponse>>

    @POST("cocktaildakk/v1/cocktails/like/{cocktailId}")
    fun isLikeCocktail(@Path("cocktailId")cocktailid : Int) : Call<ResponseWrapper<String?>>

    @DELETE("cocktaildakk/v1/cocktails/like/{cocktailId}")
    fun disLikeCocktail(@Path("cocktailId")cocktailid : Int) : Call<ResponseWrapper<String?>>


}
