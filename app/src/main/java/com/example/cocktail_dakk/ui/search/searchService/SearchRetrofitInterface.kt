package com.example.cocktail_dakk.ui.search.searchService

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRetrofitInterface {

    //    @POST("/users")
    //    fun signUp(@Body user: User): Call<AuthResponse>
    //    //함수(바디에보낼것) : 결과로 받을 것
    @GET("search/cocktail")
    fun search(@Query("inputStr") inputtext: String): Call<SearchResponce>

}