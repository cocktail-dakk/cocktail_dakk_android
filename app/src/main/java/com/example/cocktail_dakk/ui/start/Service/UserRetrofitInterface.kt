package com.example.cocktail_dakk.ui.start.Service

import com.example.cocktail_dakk.ui.menu_detail.detailService.detailResponse
import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitInterface {
    @POST("users/info")
    fun signup(
        @Body userRequest: UserRequest,
        @Header("Auth") jwt : String): Call<UserResponce>

    @GET("users/status")
    fun isfavorok(
        @Header("Auth") jwt : String): Call<isfavorokResponse>

    @GET("users/device-num")
    fun autologin(@Query("deviceNum", encoded = true) deviceNum: String): Call<AutoLoginResponse>

    @GET("/users/login")
    fun googlelogin(): Call<LoginResponse>

    @GET("/users/info")
    fun getuserinfo(@Header("Auth") jwt : String): Call<getUserinfoResponse>
}