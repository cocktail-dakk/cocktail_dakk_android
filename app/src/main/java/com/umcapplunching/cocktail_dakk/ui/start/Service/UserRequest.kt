package com.umcapplunching.cocktail_dakk.ui.start.Service

import com.google.gson.annotations.SerializedName

// 서버에서 유저로 보내는 데이터 형식
data class UserRequest(
    var nickname: String ="testnickname",
    var age: Int = 20,
    var sex: String = "M",
    var alcoholLevel: Int = 0,
    var favouritesKeywords: String = "",
    var favouritesDrinks: String = "",
)

data class TokenSigninRequest(
    @SerializedName("idToken")var idtoken: String =" ",
)