package com.umcapplunching.cocktail_dakk.ui.search.searchService

import retrofit2.Call
import retrofit2.http.*

interface SearchRetrofitInterface {

    @GET("cocktaildakk/v1/search/cocktail")
    fun search(@Header("auth") jwt : String,
               @Query("inputStr") inputtext: String): Call<SearchResponce>

    @GET("cocktaildakk/v1/search/cocktail")
    fun paging(@Header("auth") jwt : String,
               @Query("page") paging : Int,
               @Query("inputStr") inputtext: String): Call<SearchResponce>

    @GET("cocktaildakk/v1/search/cocktail/filter")
    fun filter(@Header("auth") jwt : String,
               @Query("page") paging : Int,
               @Query("keywordName") keywordlist: List<String>,
               @Query("minAlcoholLevel") mindosu: Int,
               @Query("maxAlcoholLevel") maxdosu: Int,
               @Query("drinkName") drinklist: List<String>): Call<SearchResponce>

    @GET("cocktaildakk/v1/search/cocktail/filter")
    fun filter_paging( @Header("auth") jwt : String,
                       @Query("page") paging : Int,
                       @Query("keywordName") keywordlist: List<String>,
                       @Query("minAlcoholLevel") mindosu: Int,
                       @Query("maxAlcoholLevel") maxdosu: Int,
                       @Query("drinkName") drinklist: List<String>): Call<SearchResponce>

    @POST("cocktaildakk/v1/cocktails/like/{cocktailId}")
    fun isLikeCocktail(@Header("auth")accesstoken: String,
                       @Path("cocktailId")cocktailid : Int) : Call<IsLikeResponse>

    @DELETE("cocktaildakk/v1/cocktails/like/{cocktailId}")
    fun disLikeCocktail(@Header("auth")accesstoken: String,
                       @Path("cocktailId")cocktailid : Int) : Call<IsLikeResponse>



}