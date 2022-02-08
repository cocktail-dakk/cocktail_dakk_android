package com.example.cocktail_dakk.ui.start.Service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRetrofitInterface {
    @POST("users/sign-up")
    fun signup(@Body userRequest: UserRequest): Call<UserResponce>

}