package com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface BookmarkRetrofitInterface {
    @GET("cocktaildakk/v1/cocktails/like")
    fun getIsLikeCocktail(@Header("auth") accesstoken : String) : Call<BookmarkResponse>
}