package com.umcapplunching.cocktail_dakk.ui.main.mainrecommand.MainrecService

import retrofit2.Call
import retrofit2.http.*

interface MainrecRetrofitInterface {

    @GET("cocktaildakk/v1/recommend/user/")
    fun MainRec(@Header("auth") jwt: String): Call<MainrecommandResponse>

    @GET("cocktaildakk/v1/recommend/user/")
    suspend fun MainRecCoroutin(@Header("auth") jwt: String): MainrecommandResponse
}