package com.example.cocktail_dakk.ui.main.keyword.todayrec.Dailyrecservice

import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface TodayrecRetrofitInterface {
    //    @POST("/users")
    //    fun signUp(@Body user: User): Call<AuthResponse>
    //    //함수(바디에보낼것) : 결과로 받을 것
        @GET("/recommend/today")// https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/mainRec
        fun todayRec(): Call<TodayrecommandResponse>

}