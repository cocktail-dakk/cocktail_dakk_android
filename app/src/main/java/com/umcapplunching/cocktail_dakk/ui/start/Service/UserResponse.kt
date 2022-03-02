package com.umcapplunching.cocktail_dakk.ui.start.Service

import android.os.Parcelable
import com.umcapplunching.cocktail_dakk.ui.search.searchService.BaseGiju
import com.umcapplunching.cocktail_dakk.ui.search.searchService.Keyword
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue



//@Parcelize
//data class ResponseWrapper<T>(
//    @SerializedName("status")
//    val status: Int,
//    @SerializedName("success")
//    val success: Boolean,
//    @SerializedName("message")
//    val message: String,
//    @SerializedName("data")
//    val data: @RawValue T? = null
//) : Parcelable

data class UserResponce(
    @SerializedName("code")val code: Int,
    @SerializedName("isSuccess")val isSuccess: Boolean,
    @SerializedName("message")val message: String,
    @SerializedName("result")val userbody: Userbody
)

data class Userbody(
    @SerializedName("id")val id: Int,
    @SerializedName("email")val email: String,
    @SerializedName("nickname")val nickname: String,
    @SerializedName("age")val age: Int,
    @SerializedName("sex")val sex: String,
    @SerializedName("alcoholLevel")val alcoholLevel: Int,
    @SerializedName("userKeywords")val userKeywords: List<Keyword>,
    @SerializedName("userDrinks")val userDrinks: List<BaseGiju>
)

data class AutoLoginResponse(
    @SerializedName("code")val code: Int,
    @SerializedName("isSuccess")val isSuccess: Boolean,
    @SerializedName("message")val message: String,
    @SerializedName("result")val autologinbody: Autologinbody,
)

data class Autologinbody(
    @SerializedName("age")val age: Int,
    @SerializedName("alcoholLevel")val alcoholLevel: Int,
    @SerializedName("deviceNum")val deviceNum: String,
    @SerializedName("id")val id: Int,
    @SerializedName("nickname")val nickname: String,
    @SerializedName("sex")val sex: String,
    @SerializedName("userDrinks")val userDrinks: List<BaseGiju>,
    @SerializedName("userKeywords")val userKeywords: List<Keyword>,
)

data class LoginResponse(
    @SerializedName("token")val token: String,
    @SerializedName("refreshToken")val refreshToken: String,
)

data class isfavorokResponse(
    @SerializedName("code")val code: Int,
    @SerializedName("isSuccess")val isSuccess: Boolean,
    @SerializedName("message")val message: String,
    @SerializedName("result")val isfavorok: Isfavorok
)

data class Isfavorok(
    @SerializedName("email")val email: String,
    @SerializedName("doInit")val doInit: Boolean
)

data class getUserinfoResponse(
    @SerializedName("code")val code: Int,
    @SerializedName("isSuccess")val isSuccess: Boolean,
    @SerializedName("message")val message: String,
    @SerializedName("result")val userinfo: Userinfo
)

data class Userinfo(
    @SerializedName("age")val age: Int,
    @SerializedName("alcoholLevel")val alcoholLevel: Int,
    @SerializedName("deviceNum")val deviceNum: String,
    @SerializedName("id")val id: Int,
    @SerializedName("nickname")val nickname: String,
    @SerializedName("sex")val sex: String,
    @SerializedName("userDrinks")val userDrinks: List<BaseGiju>,
    @SerializedName("userKeywords")val userKeywords: List<Keyword>
)

data class TokenResponse(
    @SerializedName("code")val code: Int,
    @SerializedName("isSuccess")val isSuccess: Boolean,
    @SerializedName("message")val message: String,
    @SerializedName("result")val result: Tokenrespbody
)

data class Tokenrespbody(
    @SerializedName("refreshToken")val refreshToken: String,
    @SerializedName("token")val token: String
)

