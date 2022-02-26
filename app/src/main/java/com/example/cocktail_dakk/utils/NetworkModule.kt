package com.example.cocktail_dakk.utils

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ViewUtils
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.ui.start.Service.ResponseWrapper
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import okhttp3.MediaType.*

//const val BASE_URL = "http://13.125.121.202"
//val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(
//    GsonConverterFactory.create()).build()

var tempURL = "http://220.72.112.76:8080/"
var mainURL = "https://www.cocktaildakk.shop"
val gson = Gson()

//object APIClient {
//    fun getApiClient(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("https://www.cocktaildakk.shop")
//            .client(provideOkHttpClient(AppInterceptor()))
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//    }
//
//    private fun provideOkHttpClient(
//        interceptor: AppInterceptor
//    ): OkHttpClient = OkHttpClient.Builder()
//        .run {
//            addInterceptor(interceptor)
//            build()
//        }
//
//}

fun getReposit(): Retrofit {
//    val client = OkHttpClient.Builder()
//        .addNetworkInterceptor(commonNetworkInterceptor)
//        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(mainURL) //베이스 URL 넣기
//        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    return retrofit
}


//private val commonNetworkInterceptor = object : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
//        val newRequest = chain.request().newBuilder()
//            .addHeader("auth", accesstoken)
//            .build()
//        Log.d("InterceptRequest", newRequest.toString())
//        proceed(newRequest)
//        var response = proceed(newRequest)
//        proceed(newRequest)
//        val response = chain.proceed(newRequest)
//
//        val rawJson = response.body()!!.string()
//        val type = object : TypeToken<ResponseWrapper<*>>() {}.type
//        val res = try {
//            val r = gson.fromJson<ResponseWrapper<*>>(rawJson, type) ?: throw JsonSyntaxException("Parse Fail")
//            if(!r.isSuccess)
//                ResponseWrapper<Any>(-999, false, "Server Logic Fail : ${r.message}", null)
//            else
//                r
//        } catch (e: JsonSyntaxException) {
//            ResponseWrapper<Any>(-999, false, "json parsing fail : $e", null)
//        } catch (t: Throwable) {
//            ResponseWrapper<Any>(-999, false, "unknown error : $t", null)
//        }
//        val dataJson = gson.toJson(res.result)
//        Log.d("InterceptResult",dataJson.toString())
//        return response.newBuilder().body(ResponseBody.create(parse("application/json"),dataJson)).build()

//        response.newBuilder()
//            .message(res.message)
//            .body(ResponseBody.create(parse("application/json"),dataJson))
//            .build()
//    }
//}


var serverClientId = R.string.server_client_id

var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestIdToken("1063572294782-1c5rlpj4cc0euk87cgorjg21snggm6ck.apps.googleusercontent.com")
    .requestEmail()
    .build()

