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
    private lateinit var iSfavorokView: iSFavorokView
    private lateinit var getuserinfoView: getUserInfoView

    fun setsignupView(signupView: SignupView) {
        this.signupView = signupView
    }

    fun setautologinView(autologeView: AutoLoginView) {
        this.autologeView = autologeView
    }

    fun setiSfavorokViewView(iSfavorokView: iSFavorokView) {
        this.iSfavorokView = iSfavorokView
    }

    fun setUserinfoView(getuserinfoView: getUserInfoView) {
        this.getuserinfoView = getuserinfoView
    }

    fun getUserinfo(jwt: String) {
        val getUserinfoService = getReposit().create(UserRetrofitInterface::class.java)
        getuserinfoView.onGetUinfoLoading()
        getUserinfoService.getuserinfo(jwt).enqueue(object : Callback<getUserinfoResponse> {
            override fun onResponse(
                call: Call<getUserinfoResponse>,
                response: Response<getUserinfoResponse>
            ) {
                val resp = response.body()!!
                Log.d("Autologin_api",resp.toString())
                when (resp.code){
                    1000 -> getuserinfoView.onGetUinfoSuccess(resp.userinfo)
                    else -> {
                        getuserinfoView.onGetUinfoFailure(resp.code,resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<getUserinfoResponse>, t: Throwable) {
                getuserinfoView.onGetUinfoFailure(400, "네트워크 오류 발생")
            }
        })
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

    fun signup(userRequest: UserRequest,jwt : String) {
        val signupService = getReposit().create(UserRetrofitInterface::class.java)
        signupView.onSignupLoading()
        signupService.signup(userRequest,jwt).enqueue(object : Callback<UserResponce> {
            override fun onResponse(call: Call<UserResponce>, response: Response<UserResponce>) {
                //404일때 예외처리도 해야할 듯
                val resp = response.body()!!
                Log.d("SignUp-API",resp.toString())
                when (resp.code){
                    1000 -> signupView.onSignupSuccess(resp.userbody)
                    else -> {
                        signupView.onSignupFailure(resp.code,resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<UserResponce>, t: Throwable) {
                Log.d("SignUp-API",call.toString())
                signupView.onSignupFailure(400, "네트워크 오류 발생")
            }

        })
    }

    fun isfavorok(jwt : String) {
        val isfavorokService = getReposit().create(UserRetrofitInterface::class.java)
        iSfavorokView.onFavorLoading()
        isfavorokService.isfavorok(jwt).enqueue(object : Callback<isfavorokResponse> {
            override fun onResponse(
                call: Call<isfavorokResponse>,
                response: Response<isfavorokResponse>
            ) {
                val resp = response.body()!!
                Log.d("IsFavorok-API",resp.toString())
                when (resp.isfavorok.status){
                    "ACTIVE" -> iSfavorokView.onFavorSuccess(resp.isfavorok)
                    else -> {
                        iSfavorokView.onFavorFailure(resp.code,resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<isfavorokResponse>, t: Throwable) {
                Log.d("IsFavorok-API",call.toString())
                iSfavorokView.onFavorFailure(400, "네트워크 오류 발생")
            }

        })
    }


}