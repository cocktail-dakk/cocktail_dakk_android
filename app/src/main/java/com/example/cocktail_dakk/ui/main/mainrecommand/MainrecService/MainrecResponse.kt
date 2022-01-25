package com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService

import com.google.gson.annotations.SerializedName


//data class Auth(@SerializedName("userIdx")val userIdx: Int,
//                @SerializedName("jwt")val jwt : String
//)

data class MainrecResponse(
    @SerializedName("code")val code: Int,
    @SerializedName("message")val message: String,
    @SerializedName("index")val idx: Int,
    @SerializedName("mainrecimg")val imgRes: Int,
//    @SerializedName("message")val message: String,
//    @SerializedName("result")val result: Auth?
)
