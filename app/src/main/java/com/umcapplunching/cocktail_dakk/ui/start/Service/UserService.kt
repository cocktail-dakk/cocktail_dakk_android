package com.umcapplunching.cocktail_dakk.ui.start.Service

import android.util.Log
import com.umcapplunching.cocktail_dakk.utils.getReposit
import com.umcapplunching.cocktail_dakk.utils.getRepositforLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService {
    private lateinit var signupView: SignupView
    private lateinit var iSfavorokView: iSFavorokView
    private lateinit var getuserinfoView: getUserInfoView
    private lateinit var tokenSigninView: TokenSigninView
    private lateinit var tokenResfreshView: TokenResfreshView

    fun settokenRefreshView(tokenResfreshView: TokenResfreshView) {
        this.tokenResfreshView = tokenResfreshView
    }

    fun settokenSigninView(tokenSigninView: TokenSigninView) {
        this.tokenSigninView = tokenSigninView
    }

    fun setsignupView(signupView: SignupView) {
        this.signupView = signupView
    }

    fun setiSfavorokViewView(iSfavorokView: iSFavorokView) {
        this.iSfavorokView = iSfavorokView
    }

    fun setUserinfoView(getuserinfoView: getUserInfoView) {
        this.getuserinfoView = getuserinfoView
    }

    fun TokenRefresh(refreshtoken: String) {
        val Service = getRepositforLogin().create(UserRetrofitInterface::class.java)
        tokenResfreshView.onTokenRefreshLoading()
        Service.tokenfresh(refreshtoken).enqueue(object : Callback<TokenResponse> {
            override fun onResponse(
                call: Call<TokenResponse>,
                response: Response<TokenResponse>
            ) {
                Log.d("TokenFreshApi_Success",response.toString())
                if (response.code() == 502){
                    tokenResfreshView.onTokenRefreshFailure(response.code(),"네트워크 오류 발생")
                }
                else {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> tokenResfreshView.onTokenRefreshSuccess(resp.result)
                        else -> {
                            tokenResfreshView.onTokenRefreshFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Log.d("TokenFreshApi_Fail",call.toString())
                tokenResfreshView.onTokenRefreshFailure(400, "네트워크 오류 발생")
            }
        })
    }


    fun TokenSignin(idtoken: TokenSigninRequest) {
        val Service = getRepositforLogin().create(UserRetrofitInterface::class.java)
        Service.tokensignin(idtoken).enqueue(object : Callback<TokenResponse> {
            override fun onResponse(
                call: Call<TokenResponse>,
                response: Response<TokenResponse>
            ) {
                Log.d("TokenSigninAPi_sucess",response.toString())
                if (response.code() == 502){
                    tokenSigninView.onTokenSigninFailure(response.code(),"네트워크 오류 발생")
                }
                else {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> tokenSigninView.onTokenSigninSuccess(resp.result)
                        else -> {
                            tokenSigninView.onTokenSigninFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Log.d("TokenSigninAPI_Failure",t.toString())
                tokenSigninView.onTokenSigninFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun getUserinfo(accesstoken: String) {
        val getUserinfoService = getRepositforLogin().create(UserRetrofitInterface::class.java)
        getUserinfoService.getuserinfo(accesstoken).enqueue(object : Callback<getUserinfoResponse> {
            override fun onResponse(
                call: Call<getUserinfoResponse>,
                response: Response<getUserinfoResponse>
            ) {
                if (response.code() == 502){
                    getuserinfoView.onGetUinfoFailure(response.code(),"네트워크 오류 발생")
                }
                else {
                    val resp = response.body()!!
                    Log.d("Autologin_api", resp.toString())
                    when (resp.code) {
                        1000 -> getuserinfoView.onGetUinfoSuccess(resp.userinfo)
                        else -> {
                            getuserinfoView.onGetUinfoFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<getUserinfoResponse>, t: Throwable) {
                getuserinfoView.onGetUinfoFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun signup(userRequest: UserRequest,accesstoken : String) {
        val signupService = getRepositforLogin().create(UserRetrofitInterface::class.java)
        signupView.onSignupLoading()
        signupService.signup(userRequest,accesstoken).enqueue(object : Callback<UserResponce> {
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

    fun isfavorok(accesstoken : String) {
        val isfavorokService = getRepositforLogin().create(UserRetrofitInterface::class.java)
        iSfavorokView.onFavorLoading()
        isfavorokService.isfavorok(accesstoken).enqueue(object : Callback<isfavorokResponse> {
            override fun onResponse(
                call: Call<isfavorokResponse>,
                response: Response<isfavorokResponse>
            ) {
                Log.d("IsFavorok-API",response.body().toString())
                if (response.body()!!.isfavorok.doInit){
                    iSfavorokView.onFavorFailure(response.body()!!.code,response.body()!!.message)
                }
                else{
                    iSfavorokView.onFavorSuccess(response.body()!!.isfavorok)
                }
            }
            override fun onFailure(call: Call<isfavorokResponse>, t: Throwable) {
                Log.d("IsFavorok-API",call.toString())
                iSfavorokView.onFavorFailure(400, "네트워크 오류 발생")
            }
        })
    }

}