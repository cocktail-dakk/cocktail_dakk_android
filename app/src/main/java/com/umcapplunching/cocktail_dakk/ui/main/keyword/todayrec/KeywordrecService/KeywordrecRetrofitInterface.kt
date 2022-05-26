package com.umcapplunching.cocktail_dakk.ui.main.keyword.todayrec.KeywordrecService

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface KeywordrecRetrofitInterface {
    @GET("cocktaildakk/v1/recommend/today")// https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/mainRec
    fun todayRec(): Call<TodayrecommandResponse>

    @GET("cocktaildakk/v1/recommend/keyword/")
    fun keywordRec(): Call<KeywordrecResponse>

}