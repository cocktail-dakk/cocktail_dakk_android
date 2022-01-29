package com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService

import retrofit2.Call
import retrofit2.http.*

interface MainrecRetrofitInterface {
//    @POST("/users")
//    fun signUp(@Body user: User): Call<AuthResponse>
//    //함수(바디에보낼것) : 결과로 받을 것

    @GET("/recommend/user/{deviceNum}")// https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/mainRec
    fun MainRec(@Path("deviceNum",encoded = true) devicenum: String): Call<MainrecommandResponse>

//    @GET("/users/auto-login")
//    fun autologin(@Header("X-ACCESS-TOKEN") jwt: String?): Call<AuthResponse>

}