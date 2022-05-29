package com.umcapplunching.cocktail_dakk.ui.main.mainrecommand.MainrecService

import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import retrofit2.Call
import retrofit2.http.*

interface MainrecRetrofitInterface {

    @GET("cocktaildakk/v1/recommend/user/")
    fun MainRec(): Call<ResponseWrapper<Mainrec>>
}