package com.example.cocktail_dakk.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

//const val BASE_URL = "http://13.125.121.202"
//val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(
//    GsonConverterFactory.create()).build()

// 정식 주소 : https://www.cocktaildakk.shop
// 임시 주소 : http://220.72.112.76:8080/

var tempURL = "http://220.72.112.76:8080/"
var mainURL = "https://www.cocktaildakk.shop"

fun getReposit() : Retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl(tempURL) //베이스 URL 넣기
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit
}