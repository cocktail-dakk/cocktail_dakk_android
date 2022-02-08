package com.example.cocktail_dakk.ui.start.Service

import android.util.Log
import com.example.cocktail_dakk.ui.search.searchService.PagingView
import com.example.cocktail_dakk.ui.search.searchService.SearchResponce
import com.example.cocktail_dakk.ui.search.searchService.SearchRetrofitInterface
import com.example.cocktail_dakk.ui.search.searchService.SearchView
import com.example.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService {
    private lateinit var signupView: SignupView
    private lateinit var autologeView: AutoLoginView

    fun setsignupView(signupView: SignupView) {
        this.signupView = signupView
    }

    fun setautologinView(autologeView: AutoLoginView) {
        this.autologeView = autologeView
    }

    fun autologin(devicenum: String) {
        val autologinService = getReposit().create(UserRetrofitInterface::class.java)
        autologeView.onLoginLoading()
        autologinService.autologin(devicenum).enqueue(object : Callback<AutoLoginResponse> {
            override fun onResponse(
                call: Call<AutoLoginResponse>,
                response: Response<AutoLoginResponse>
            ) {
                val resp = response.body()!!
                Log.d("Autologin_api",resp.toString())
                when (resp.code){
                    1000 -> autologeView.onLoginSuccess(resp.autologinbody)
                    else -> {
                        autologeView.onLoginFailure(resp.code,resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<AutoLoginResponse>, t: Throwable) {
                autologeView.onLoginFailure(400, "네트워크 오류 발생")
            }

        })
    }

    fun signup(userRequest: UserRequest) {
        val signupService = getReposit().create(UserRetrofitInterface::class.java)
        signupView.onSignupLoading()
        signupService.signup(userRequest).enqueue(object : Callback<UserResponce> {
            override fun onResponse(call: Call<UserResponce>, response: Response<UserResponce>) {
                //404일때 예외처리도 해야할 듯
                val resp = response.body()!!
                when (resp.code){
                    1000 -> signupView.onSignupSuccess(resp.userbody)
                    else -> {
                        signupView.onSignupFailure(resp.code,resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<UserResponce>, t: Throwable) {
                signupView.onSignupFailure(400, "네트워크 오류 발생")
            }

        })
    }

}