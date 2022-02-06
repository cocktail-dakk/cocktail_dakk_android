package com.example.cocktail_dakk.ui.menu_detail.detailService

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailRetrofitInterface {
    @GET("/cocktails/details")
    fun detail(@Query("id",encoded = true) id : Int): Call<detailResponse>
}
