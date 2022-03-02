package com.umcapplunching.cocktail_dakk.ui.mypage.mypageService

import com.google.gson.annotations.SerializedName

data class MypageRequest(
    @SerializedName("nickname")val nickname: String,
    @SerializedName("alcoholLevel")val alcoholLevel: Int,
    @SerializedName("favouritesKeywords")val favouritesKeywords: String,
    @SerializedName("favouritesDrinks")val favouritesDrinks: String
)