package com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService

import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import com.umcapplunching.cocktail_dakk.ui.main.keyword.todayrec.KeywordrecService.KeywordrecResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface BookmarkRetrofitInterface {
    @GET("cocktaildakk/v1/cocktails/like")
    fun getIsLikeCocktail() : Call<ResponseWrapper<List<BookmarkBody>>>
}