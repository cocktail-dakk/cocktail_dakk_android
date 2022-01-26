package com.example.cocktail_dakk.ui.main.keyword.todayrec.Dailyrecservice

import com.google.gson.annotations.SerializedName

data class cocktailKeywords(
    @SerializedName("keywordId")val keywordId : Int,
    @SerializedName("keywordName")val keywordName : String,
)

data class TodayrecResponse(
    @SerializedName("cocktailInfoId")val cocktailInfoId: Int,
    @SerializedName("englishName")val englishName: String,
    @SerializedName("koreanName")val koreanName: String,
    @SerializedName("cocktailKeywords")val cocktailKeywords: ArrayList<cocktailKeywords>,
    @SerializedName("recommendImageURL")val recommendImageURL : String,

    )


data class TodayrecResponseList(
    @SerializedName("cocktaillist")val cocktaillist: ArrayList<TodayrecResponse>,
    )