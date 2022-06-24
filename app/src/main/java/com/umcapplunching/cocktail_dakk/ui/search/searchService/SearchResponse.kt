package com.umcapplunching.cocktail_dakk.ui.search.searchService

import com.google.gson.annotations.SerializedName

//data class SearchResponce(
//    @SerializedName("code")val code: Int,
//    @SerializedName("isSuccess")val isSuccess: Boolean,
//    @SerializedName("message")val message: String,
//    @SerializedName("result")val searchresult: SearchResult
//)

data class SearchResult(
    @SerializedName("content")val cocktailList: List<CocktailList>,
    @SerializedName("empty")val empty: Boolean,
    @SerializedName("first")val first: Boolean,
    @SerializedName("last")val last: Boolean,
    @SerializedName("number")val number: Int,
    @SerializedName("numberOfElements")val numberOfElements: Int,
    @SerializedName("pageable")val pageable: String,
    @SerializedName("size")val size: Int,
    @SerializedName("sort")val sort: Sort,
    @SerializedName("totalElements")val totalElements: Int,
    @SerializedName("totalPages")val totalPages: Int
)

data class CocktailList(
    @SerializedName("alcoholLevel")val alcoholLevel: Int,
    @SerializedName("cocktailInfoId")val cocktailInfoId: Int,
    @SerializedName("drinks")val drinks: List<BaseGiju>,
    @SerializedName("englishName")val englishName: String,
    @SerializedName("keywords")val keywords: List<Keyword>,
    @SerializedName("koreanName")val koreanName: String,
    @SerializedName("ratingAvg")val ratingAvg: Double,
    @SerializedName("smallNukkiImageURL")val smallNukkiImageURL: String
)

data class BaseGiju(
    @SerializedName("drinkId")val drinkId: Int,
    @SerializedName("drinkName")val drinkName: String
)

data class Keyword(
    @SerializedName("keywordId")val keywordId: Int,
    @SerializedName("keywordName")val keywordName: String
)

data class Sort(
    @SerializedName("empty")val empty: Boolean,
    @SerializedName("sorted")val sorted: Boolean,
    @SerializedName("unsorted")val unsorted: Boolean
)


