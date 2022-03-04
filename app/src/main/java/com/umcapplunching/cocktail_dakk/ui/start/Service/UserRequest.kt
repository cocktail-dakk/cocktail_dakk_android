package com.umcapplunching.cocktail_dakk.ui.start.Service

import com.google.gson.annotations.SerializedName

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