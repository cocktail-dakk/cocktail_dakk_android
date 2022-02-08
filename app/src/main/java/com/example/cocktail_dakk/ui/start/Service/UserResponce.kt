package com.example.cocktail_dakk.ui.start.Service

import com.example.cocktail_dakk.ui.search.searchService.BaseGiju
import com.example.cocktail_dakk.ui.search.searchService.Keyword
import com.google.gson.annotations.SerializedName

data class UserResponce(
    @SerializedName("code")val code: Int,
    @SerializedName("isSuccess")val isSuccess: Boolean,
    @SerializedName("message")val message: String,
    @SerializedName("result")val userbody: Userbody
)

data class Userbody(
    @SerializedName("age")val age: Int,
    @SerializedName("alcoholLevel")val alcoholLevel: Int,
    @SerializedName("deviceNum")val deviceNum: String,
    @SerializedName("id")val id: Int,
    @SerializedName("nickname")val nickname: String,
    @SerializedName("sex")val sex: String,
    @SerializedName("userDrinks")val userDrinks: List<BaseGiju>,
    @SerializedName("userKeywords")val userKeywords: List<Keyword>
)
