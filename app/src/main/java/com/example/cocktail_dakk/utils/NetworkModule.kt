package com.example.cocktail_dakk.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

//const val BASE_URL = "http://13.125.121.202"
//val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(
//    GsonConverterFactory.create()).build()

fun getReposit() : Retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://ec2-3-38-87-27.ap-northeast-2.compute.amazonaws.com:8080") //베이스 URL 넣기
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit
}