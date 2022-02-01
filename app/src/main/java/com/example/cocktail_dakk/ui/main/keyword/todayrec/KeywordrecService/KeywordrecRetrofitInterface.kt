package com.example.cocktail_dakk.ui.main.keyword.todayrec.KeywordrecService

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface KeywordrecRetrofitInterface {
    @GET("/recommend/today")// https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/mainRec
    fun todayRec(): Call<TodayrecommandResponse>

    @GET("/recommend/keyword/{deviceNum}")
    fun keywordRec(@Path("deviceNum",encoded = true) devicenum : String): Call<KeywordrecResponse>

}