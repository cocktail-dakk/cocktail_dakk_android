package com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MainrecRetrofitInterface {
//    @POST("/users")
//    fun signUp(@Body user: User): Call<AuthResponse>
//    //함수(바디에보낼것) : 결과로 받을 것

    @POST("/mainRec")// https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/mainRec
    fun MainRec(): Call<MainrecResponse>

//    @GET("/users/auto-login")
//    fun autologin(@Header("X-ACCESS-TOKEN") jwt: String?): Call<AuthResponse>

}