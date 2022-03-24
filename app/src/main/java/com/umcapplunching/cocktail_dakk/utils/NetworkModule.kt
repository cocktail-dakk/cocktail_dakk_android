package com.umcapplunching.cocktail_dakk.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson

var tempURL = "http://220.72.112.76:8080/"
var mainURL = "https://www.cocktaildakk.shop"
val gson = Gson()

var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestIdToken("1063572294782-1c5rlpj4cc0euk87cgorjg21snggm6ck.apps.googleusercontent.com")
    .requestEmail()
    .build()

fun getReposit(): Retrofit {

    //인터셉터 클라이언트
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


