package com.umcapplunching.cocktail_dakk.ui.start.Service

import com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService.detailResponse
import com.google.android.gms.auth.api.credentials.IdToken
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitInterface {
    @POST("users/info")
    fun signup(
        @Body userRequest: UserRequest,
        @Header("Auth") accesstoken : String): Call<UserResponce>

    @GET("users/status")
    fun isfavorok(
        @Header("Auth") accesstoken : String): Call<isfavorokResponse>

    @GET("users/device-num")
    fun autologin(@Query("deviceNum", encoded = true) deviceNum: String): Call<AutoLoginResponse>

    @GET("/users/info")
    fun getuserinfo(@Header("Auth") accesstoken : String): Call<getUserinfoResponse>

    @POST("/users/tokensignin")
    fun tokensignin(@Body idToken: TokenSigninRequest) : Call<TokenResponse>

    @GET("/token/refresh")
    fun tokenfresh(@Header("Refresh")refreshtoken: String) : Call<TokenResponse>

}