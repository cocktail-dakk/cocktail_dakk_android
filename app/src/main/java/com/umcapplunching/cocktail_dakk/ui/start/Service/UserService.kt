package com.umcapplunching.cocktail_dakk.ui.start.Service

import android.util.Log
import com.umcapplunching.cocktail_dakk.ui.search.searchService.PagingView
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchResponce
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchRetrofitInterface
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchView
import com.umcapplunching.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService {
    private lateinit var signupView: SignupView
    private lateinit var autologeView: AutoLoginView
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

    fun setautologinView(autologeView: AutoLoginView) {
        this.autologeView = autologeView
    }

    fun setiSfavorokViewView(iSfavorokView: iSFavorokView) {
        this.iSfavorokView = iSfavorokView
    }

    fun setUserinfoView(getuserinfoView: getUserInfoView) {
        this.getuserinfoView = getuserinfoView
    }

    fun TokenRefresh(refreshtoken: String) {
        val Service = getReposit().create(UserRetrofitInterface::class.java)
        tokenResfreshView.onTokenRefreshLoading()
        Service.tokenfresh(refreshtoken).enqueue(object : Callback<TokenResponse> {
            override fun onResponse(
                call: Call<TokenResponse>,
                response: Response<TokenResponse>
            ) {
                Log.d("TokenFreshApi_Success",response.toString())
                val resp = response.body()!!
                when (resp.code){
                    1000 -> tokenResfreshView.onTokenRefreshSuccess(resp.result)
                    else -> {
                        tokenResfreshView.onTokenRefreshFailure(resp.code,resp.message)
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
        val Service = getReposit().create(UserRetrofitInterface::class.java)
        tokenSigninView.onTokenSigninLoading()
        Service.tokensignin(idtoken).enqueue(object : Callback<TokenResponse> {
            override fun onResponse(
                call: Call<TokenResponse>,
                response: Response<TokenResponse>
            ) {
                Log.d("TokenSigninAPi_sucess",response.toString())
                val resp = response.body()!!
                when (resp.code){
                    1000 -> tokenSigninView.onTokenSigninSuccess(resp.result)
                    else -> {
                        tokenSigninView.onTokenSigninFailure(resp.code,resp.message)
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
        val getUserinfoService = getReposit().create(UserRetrofitInterface::class.java)
        getuserinfoView.onGetUinfoLoading()
        getUserinfoService.getuserinfo(accesstoken).enqueue(object : Callback<getUserinfoResponse> {
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

    fun signup(userRequest: UserRequest,accesstoken : String) {
        val signupService = getReposit().create(UserRetrofitInterface::class.java)
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
        val isfavorokService = getReposit().create(UserRetrofitInterface::class.java)
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
                    iSfavorokView.onFavorSuccess(response.body()!!.isfavorok!!)
                }
            }
            override fun onFailure(call: Call<isfavorokResponse>, t: Throwable) {
                Log.d("IsFavorok-API",call.toString())
                iSfavorokView.onFavorFailure(400, "네트워크 오류 발생")
            }
        })
    }

}