package com.umcapplunching.cocktail_dakk.ui.main.keyword.todayrec.KeywordrecService

import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface KeywordrecRetrofitInterface {
    @GET("cocktaildakk/v1/recommend/today")// https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/mainRec
    fun todayRec(): Call<ResponseWrapper<List<TodayrecResult>>>

    @GET("cocktaildakk/v1/recommend/keyword/")
    fun keywordRec(): Call<ResponseWrapper<List<KeywordrecResult>>>

}