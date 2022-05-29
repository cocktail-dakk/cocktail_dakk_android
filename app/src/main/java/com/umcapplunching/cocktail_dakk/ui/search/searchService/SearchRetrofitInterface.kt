package com.umcapplunching.cocktail_dakk.ui.search.searchService

import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import retrofit2.Call
import retrofit2.http.*

interface SearchRetrofitInterface {

    @GET("cocktaildakk/v1/search/cocktail")
    fun search(@Query("inputStr") inputtext: String): Call<ResponseWrapper<SearchResult>>

//    @GET("cocktaildakk/v1/search/cocktail")
//    suspend fun searchcoroutine(@Query("inputStr") inputtext: String): SearchResponce

    @GET("cocktaildakk/v1/search/cocktail")
    suspend fun searchcoroutine(@Query("inputStr") inputtext: String): ResponseWrapper<SearchResult>

    @GET("cocktaildakk/v1/search/cocktail")
    fun paging(@Query("page") paging : Int,
               @Query("inputStr") inputtext: String): Call<ResponseWrapper<SearchResult>>

    @GET("cocktaildakk/v1/search/cocktail/filter")
    suspend fun filter(@Query("page") paging : Int,
                       @Query("keywordName") keywordlist: List<String>,
                       @Query("minAlcoholLevel") mindosu: Int,
                       @Query("maxAlcoholLevel") maxdosu: Int,
                       @Query("drinkName") drinklist: List<String>): ResponseWrapper<SearchResult>

    @GET("cocktaildakk/v1/search/cocktail/filter")
    fun filter_paging( @Query("page") paging : Int,
                       @Query("keywordName") keywordlist: List<String>,
                       @Query("minAlcoholLevel") mindosu: Int,
                       @Query("maxAlcoholLevel") maxdosu: Int,
                       @Query("drinkName") drinklist: List<String>): Call<ResponseWrapper<SearchResult>>



}