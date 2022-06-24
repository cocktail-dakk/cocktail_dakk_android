package com.umcapplunching.cocktail_dakk.ui.start.Service

import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitInterface {
    @POST("users/info")
    fun signup(
        @Body userRequest: UserRequest,
        @Header("Auth") accesstoken : String): Call<ResponseWrapper<Userbody>>

    @GET("users/status")
    fun isfavorok(
        @Header("Auth") accesstoken : String): Call<ResponseWrapper<Isfavorok>>

    @GET("/users/info")
    fun getuserinfo(@Header("Auth") accesstoken : String): Call<ResponseWrapper<Userinfo>>

    @POST("/users/tokensignin")
    fun tokensignin(@Body idToken: TokenSigninRequest) : Call<ResponseWrapper<Tokenrespbody>>

    @GET("/token/refresh")
    fun tokenfresh(@Header("Refresh")refreshtoken: String) : Call<ResponseWrapper<Tokenrespbody>>

}