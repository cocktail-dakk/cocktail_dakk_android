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

    fun setsignupView(signupView: SignupView) {
        this.signupView = signupView
    }

    fun signup(userRequest: UserRequest) {
        val signupService = getReposit().create(UserRetrofitInterface::class.java)
        signupView.onSignupLoading()
        signupService.signup(userRequest).enqueue(object : Callback<UserResponce> {
            override fun onResponse(call: Call<UserResponce>, response: Response<UserResponce>) {
                val resp = response.body()!!
                Log.d("SignUp_test_OnResponse",resp.userbody.toString())
                when (resp.code){
                    1000 -> signupView.onSignupSuccess(resp.userbody)
                    else -> {
                        signupView.onSignupFailure(resp.code,resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<UserResponce>, t: Throwable) {
                Log.d("SignUp_test_Failure",t.toString())
                signupView.onSignupFailure(400, "네트워크 오류 발생")
            }

        })
    }

}