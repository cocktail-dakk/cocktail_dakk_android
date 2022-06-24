package com.umcapplunching.cocktail_dakk.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient

var tempURL = "http://220.72.112.76:8080/"
var mainURL = "https://www.cocktaildakk.shop"
val gson = Gson()

var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestIdToken("1063572294782-1c5rlpj4cc0euk87cgorjg21snggm6ck.apps.googleusercontent.com")
    .requestEmail()
    .build()

// 로그인, Splash, 설정화면에서 사용할 Retrogit 객체 가져오기 최상위 함수로 선언
fun getRepositforLogin(): Retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl(mainURL) //베이스 URL 넣기
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    return retrofit
}


// Auth Token Header에 넣은 retrofit 반환
fun getReposit(): Retrofit {
    // 인터셉터 클라이언트
    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(commonNetworkInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(mainURL) //베이스 URL 넣기
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    return retrofit
}

private val commonNetworkInterceptor = Interceptor { chain ->
    with(chain) {
        val newRequest = chain.request().newBuilder()
            .addHeader("auth", CocktailDakkApplication.AccessToken)
            .build()
        proceed(newRequest)

//        Log.d("InterceptRequest", newRequest.toString())
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
//        return@Interceptor response.newBuilder().body(ResponseBody.create(parse("application/json"),dataJson)).build()
//
//        response.newBuilder()
//            .message(res.message)
//            .body(ResponseBody.create(parse("application/json"),dataJson))
//            .build()

    }
}


