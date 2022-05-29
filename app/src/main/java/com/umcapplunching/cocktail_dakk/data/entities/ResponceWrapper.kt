package com.umcapplunching.cocktail_dakk.data.entities

import com.google.gson.annotations.SerializedName

// Generic 상한경계 out을 걸어서 리턴만 가능
data class ResponseWrapper<out T>(
    @SerializedName("code")val code: Int,
    @SerializedName("isSuccess")val isSuccess: Boolean,
    @SerializedName("message")val message: String,
    @SerializedName("result")val responseBody: T
)
