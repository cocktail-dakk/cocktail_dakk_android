package com.example.cocktail_dakk.ui.menu_detail.detailService

import com.example.cocktail_dakk.ui.search.searchService.Keyword

data class detailResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: detail_Cocktail
)

data class CocktailMixingMethod(
    val mixingMethodId: Int,
    val mixingMethodName: String
)

data class detail_Cocktail(
    val alcoholLevel: Int,
    val cocktailInfoId: Int,
    val cocktailKeyword: List<Keyword>,
    val cocktailMixingMethod: List<CocktailMixingMethod>,
    val description: String,
    val englishName: String,
    val ingredient: String,
    val koreanName: String,
    val nukkiImgUrl: String,
    val ratingAvg: Double,
    val todayImgUrl: String
)