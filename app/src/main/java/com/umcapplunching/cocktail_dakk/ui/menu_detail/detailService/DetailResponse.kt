package com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService

import com.umcapplunching.cocktail_dakk.ui.search.searchService.Keyword
import com.google.gson.annotations.SerializedName
import com.umcapplunching.cocktail_dakk.ui.search.searchService.BaseGiju

// Wrapper로 다묶음 필요 X
//data class detailResponse(
//    @SerializedName("code")val code: Int,
//    @SerializedName("isSuccess")val isSuccess: Boolean,
//    @SerializedName("message")val message: String,
//    @SerializedName("result")val result: detail_Cocktail
//)
//data class DetailRatingResponse(
//    @SerializedName("code")val code: Int,
//    @SerializedName("isSuccess")val isSuccess: Boolean,
//    @SerializedName("message")val message: String,
//    @SerializedName("result")val ratingrsponse: ratingResponse
//)
//data class IsLikeResponse(
//    @SerializedName("code")val code: Int,
//    @SerializedName("isSuccess")val isSuccess: Boolean,
//    @SerializedName("message")val message: String,
//    @SerializedName("result")val result: String?
//)

data class CocktailMixingMethod(
    @SerializedName("mixingMethodId")val mixingMethodId: Int,
    @SerializedName("mixingMethodName")val mixingMethodName: String
)

data class detail_Cocktail(
    @SerializedName("alcoholLevel")val alcoholLevel: Int,
    @SerializedName("cocktailInfoId")val cocktailInfoId: Int,
    @SerializedName("cocktailKeyword")val cocktailKeyword: List<Keyword>,
    @SerializedName("cocktailMixingMethod")val cocktailMixingMethod: List<CocktailMixingMethod>,
    @SerializedName("description")val description: String,
    @SerializedName("englishName")val englishName: String,
    @SerializedName("ingredient")val ingredient: String,
    @SerializedName("koreanName")val koreanName: String,
    @SerializedName("nukkiImgUrl")val nukkiImgUrl: String,
    @SerializedName("ratingAvg")val ratingAvg: Double,
    @SerializedName("todayImgUrl")val todayImgUrl: String,
    @SerializedName("cocktailDrink")val cocktailDrink : List<BaseGiju>
)


data class ratingResponse(
    @SerializedName("cocktailId")val cocktailId: Int,
    @SerializedName("ratingId")val ratingId: Int,
    @SerializedName("userId")val userId: Int
)