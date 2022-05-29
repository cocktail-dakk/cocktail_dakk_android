package com.umcapplunching.cocktail_dakk.ui.main.keyword.todayrec.KeywordrecService

import com.umcapplunching.cocktail_dakk.ui.search.searchService.Keyword
import com.google.gson.annotations.SerializedName

//data class TodayrecommandResponse(
//    @SerializedName("code")val code: Int,
//    @SerializedName("isSuccess")val isSuccess: Boolean,
//    @SerializedName("message")val message: String,
//    @SerializedName("result")val result: List<TodayrecResult>
//)

data class TodayrecResult(
    @SerializedName("cocktailInfoId")val cocktailInfoId: Int,
    @SerializedName("cocktailKeywords")val cocktailKeywords: List<Keyword>,
    @SerializedName("englishName")val englishName: String,
    @SerializedName("koreanName")val koreanName: String,
    @SerializedName("recommendImageURL") var recommendImageURL: String,
)

//data class Keyword(
//    @SerializedName("keywordId") var keywordId: Int,
//    @SerializedName("keywordName") var keywordName: String
//)

//data class KeywordrecResponse(
//    @SerializedName("code")val code: Int,
//    @SerializedName("isSuccess")val isSuccess: Boolean,
//    @SerializedName("message")val message: String,
//    @SerializedName("result")val result: List<KeywordrecResult>
//)
data class KeywordrecResult(
    @SerializedName("description")val description: String,
    @SerializedName("recommendationRes")val recommendationRes: List<RecommendationRe>,
    @SerializedName("tag")val tag: String
)

data class RecommendationRe(
    @SerializedName("cocktailInfoId")val cocktailInfoId: Int,
    @SerializedName("cocktailKeywords")val cocktailKeywords: List<Keyword>,
    @SerializedName("englishName")val englishName: String,
    @SerializedName("koreanName")val koreanName: String,
    @SerializedName("ratingAvg")val ratingAvg: Double,
    @SerializedName("smallNukkiImageURL")val smallNukkiImageURL: String
)