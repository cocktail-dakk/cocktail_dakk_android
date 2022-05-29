package com.umcapplunching.cocktail_dakk.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

// RefreshToken은 유지시키고 AccessToken은 싱글톤으로 관리
fun getrefreshtoken(context : Context) : String{
    val spf = context.getSharedPreferences("refreshtoken", AppCompatActivity.MODE_PRIVATE)
    val rt = spf.getString("refreshtoken"," ")!!
    return rt
}


fun setrefreshtoken(context: Context, token : String){
    val spf = context.getSharedPreferences("refreshtoken", AppCompatActivity.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = spf?.edit()!!
    editor.putString("refreshtoken",token)
    editor.apply()
}
//
//fun tokenRefresh(refreshtoken: String) {
//    val Service = getRepositforLogin().create(UserRetrofitInterface::class.java)
//    Service.tokenfresh(refreshtoken).enqueue(object : Callback<TokenResponse> {
//        override fun onResponse(
//            call: Call<TokenResponse>,
//            response: Response<TokenResponse>
//        ) {
//            Log.d("TokenFreshApi_Success",response.toString())
//            if (response.code() == 502){
//
//            }
//            else {
//                val resp = response.body()!!
//                when (resp.code) {
//                    1000 -> {
//                        setrefreshtoken(CocktailDakkApplication.getInstance(), resp.result.refreshToken)
//                        CocktailDakkApplication.RefreshToken = resp.result.refreshToken
//                    }
//                    else -> {
//                    }
//                }
//            }
//        }
//
//        override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
//            Log.d("TokenFreshApi_Fail",call.toString())
//        }
//    })
//}

