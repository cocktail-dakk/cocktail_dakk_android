package com.example.cocktail_dakk.utils

import android.content.Context
import android.provider.Settings.Global.getString
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktail_dakk.R
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope





//const val BASE_URL = "http://13.125.121.202"
//val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(
//    GsonConverterFactory.create()).build()

// 정식 주소 : https://www.cocktaildakk.shop
// 임시 주소 : http://220.72.112.76:8080/

var tempURL = "http://220.72.112.76:8080/"
var mainURL = "https://www.cocktaildakk.shop"
val gson = GsonBuilder().setLenient().create()

fun getReposit() : Retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl(mainURL) //베이스 URL 넣기
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    return retrofit
}

var serverClientId = R.string.server_client_id

var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestIdToken("1063572294782-1c5rlpj4cc0euk87cgorjg21snggm6ck.apps.googleusercontent.com")
    .requestEmail()
    .build()



//    .requestServerAuthCode(serverClientId.toString())

//    .requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
