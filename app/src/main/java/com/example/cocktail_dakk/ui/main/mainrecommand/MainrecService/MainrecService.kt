package com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService

import android.annotation.SuppressLint
import android.util.Log
import com.example.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainrecService {
    private lateinit var mainrecView: MainrecView

    //이걸 왜하지?
    fun setmainrecView(mainrecView: MainrecView){
        this.mainrecView = mainrecView
    }


    fun mainRec(){
//        val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(GsonConverterFactory.create()).build()
//        val authService = retrofit.create(AuthRetrofitInterface::class.java)
        val mainRecService = getReposit().create(MainrecRetrofitInterface::class.java)

        mainrecView.onMainrecLoading()

        mainRecService.MainRec().enqueue(object : Callback<MainrecResponse> {
            @SuppressLint("LongLogTag")
            override fun onResponse(call: Call<MainrecResponse>, response: Response<MainrecResponse>) {
                Log.d("MainRec/API-RESPONCE",response.toString())

                val resp = response.body()!!
                Log.d("MainRec/API-RESPONCE",resp.toString())
                when(resp.code){
                    1000 -> mainrecView.onMainrecSuccess()
                    2016,2017 -> {
                        mainrecView.onSignUpFailure(resp.code, resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<MainrecResponse>, t: Throwable) {
                Log.d("MainRec/API-ERROR",t.message.toString())
                mainrecView.onSignUpFailure(400,"네트워크 오류가 발생했습니다.")
            }
        })
    }

//
//    fun login(user: User) {
////        val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(GsonConverterFactory.create()).build()
////        val authService = retrofit.create(AuthRetrofitInterface::class.java)
//        val authService = getReposit().create(AuthRetrofitInterface::class.java)
//        loginView.onLoginLoading()
//
//        authService.login(user).enqueue(object : Callback<AuthResponse> {
//            @SuppressLint("LongLogTag")
//            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
//                Log.d("LOGINACT/API-RESPONCE",response.toString())
//
//                val resp = response.body()!!
//                Log.d("LOGINACT/API-RESPONCE-FLO",resp.toString())
//                when(resp.code){
//                    1000 -> loginView.onLoginSuccess(resp.result!!)
//                    else -> {
//                        loginView.onLoginFailure(resp.code, resp.message)
//                    }
//
//                }
//            }
//            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//                Log.d("LOGINACT/API-ERROR",t.message.toString())
//                loginView.onLoginFailure(400,"네트워크 오류가 발생했습니다.")
//            }
//        })
//    }
//
//    fun autologin(jwt: String) {
//
//        val authService = getReposit().create(AuthRetrofitInterface::class.java)
//        signUpView.onSignUpLoading()
//
//        authService.autologin(jwt).enqueue(object : Callback<AuthResponse> {
//            @SuppressLint("LongLogTag")
//            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
//                Log.d("AUTOLOGIN/API-RESPONCE",response.toString())
//
//                val resp = response.body()!!
//                Log.d("AUTOLOGIN/API-RESPONCE-FLO",resp.toString())
//                when(resp.code){
//                    1000 -> signUpView.onSignUpSuccess()
//                    else -> {
//                        signUpView.onSignUpFailure(resp.code, resp.message)
//                    }
//
//                }
//            }
//            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//                Log.d("AUTOLOGIN/API-ERROR",t.message.toString())
//                signUpView.onSignUpFailure(400,"네트워크 오류가 발생했습니다.")
//            }
//        })
//    }
}