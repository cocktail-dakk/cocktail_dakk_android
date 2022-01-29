package com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService

import com.google.gson.annotations.SerializedName

data class MainrecommandResponse(
    @SerializedName("code")val code: Int,
    @SerializedName("isSuccess")val isSuccess: Boolean,
    @SerializedName("message")val message: String,
    @SerializedName("result")val mainrecList: List<MainRec>
)

data class MainRec(
    @SerializedName("cocktailImageURL")val cocktailImageURL: String,
    @SerializedName("cocktailInfoId")val cocktailInfoId: Int
)
