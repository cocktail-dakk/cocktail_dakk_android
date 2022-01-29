package com.example.cocktail_dakk.ui.main.keyword.todayrec.Dailyrecservice

import com.google.gson.annotations.SerializedName

data class TodayrecommandResponse(
    @SerializedName("code")val code: Int,
    @SerializedName("isSuccess")val isSuccess: Boolean,
    @SerializedName("message")val message: String,
    @SerializedName("result")val result: List<TodayrecResult>
)

data class TodayrecResult(
    @SerializedName("cocktailInfoId")val cocktailInfoId: Int,
    @SerializedName("cocktailKeywords")val cocktailKeywords: List<CocktailKeyword>,
    @SerializedName("englishName")val englishName: String,
    @SerializedName("koreanName")val koreanName: String,
    @SerializedName("recommendImageURL")val recommendImageURL: String
)

data class CocktailKeyword(
    @SerializedName("keywordId") var keywordId: Int,
    @SerializedName("keywordName") var keywordName: String
)