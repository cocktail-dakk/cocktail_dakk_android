package com.example.cocktail_dakk.ui.start.Service

import com.example.cocktail_dakk.ui.menu_detail.detailService.detailResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserRetrofitInterface {
    @POST("users/sign-up")
    fun signup(@Body userRequest: UserRequest): Call<UserResponce>

    @GET("users/device-num")
    fun autologin(@Query("deviceNum",encoded = true) deviceNum: String): Call<AutoLoginResponse>
}