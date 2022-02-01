package com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService

import retrofit2.Call
import retrofit2.http.*

interface MainrecRetrofitInterface {
    @GET("/recommend/user/{deviceNum}")
    fun MainRec(@Path("deviceNum",encoded = true) devicenum: String): Call<MainrecommandResponse>
}