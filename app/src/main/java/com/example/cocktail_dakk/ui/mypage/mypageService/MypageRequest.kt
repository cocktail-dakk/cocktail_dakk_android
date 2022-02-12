package com.example.cocktail_dakk.ui.mypage.mypageService

import com.google.gson.annotations.SerializedName

data class MypageRequest(
    @SerializedName("deviceNum")val deviceNum: String,
    @SerializedName("nickname")val nickname: String,
    @SerializedName("alcoholLevel")val alcoholLevel: Int,
    @SerializedName("favouritesKeywords")val favouritesKeywords: String,
    @SerializedName("favouritesDrinks")val favouritesDrinks: String
)