package com.example.cocktail_dakk.ui.search.searchService

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRetrofitInterface {

    @GET("search/cocktail")
    fun search(@Query("inputStr") inputtext: String): Call<SearchResponce>

    @GET("search/cocktail")
    fun paging(@Query("page") paging : Int,
               @Query("inputStr") inputtext: String): Call<SearchResponce>

    @GET("search/cocktail/filter")
    fun filter(@Query("page") paging : Int,
               @Query("keywordName") keywordlist: List<String>,
               @Query("minAlcoholLevel") mindosu: Int,
               @Query("maxAlcoholLevel") maxdosu: Int,
               @Query("drinkName") drinklist: List<String>): Call<SearchResponce>

    @GET("search/cocktail/filter")
    fun filter_paging(@Query("page") paging : Int,
               @Query("keywordName") keywordlist: List<String>,
               @Query("minAlcoholLevel") mindosu: Int,
               @Query("maxAlcoholLevel") maxdosu: Int,
               @Query("drinkName") drinklist: List<String>): Call<SearchResponce>

}