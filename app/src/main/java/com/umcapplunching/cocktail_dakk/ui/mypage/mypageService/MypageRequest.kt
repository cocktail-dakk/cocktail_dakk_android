package com.umcapplunching.cocktail_dakk.ui.mypage.mypageService

import com.google.gson.annotations.SerializedName

// 수정할 때 서버에 보내는 데이터 형식
data class MypageRequest(
    @SerializedName("nickname")val nickname: String,
    @SerializedName("alcoholLevel")val alcoholLevel: Int,
    @SerializedName("favouritesKeywords")val favouritesKeywords: String,
    @SerializedName("favouritesDrinks")val favouritesDrinks: String
)