package com.umcapplunching.cocktail_dakk.ui.start.Service

import android.util.Log
import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
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
        Service.tokenfresh(refreshtoken).enqueue(object : Callback<ResponseWrapper<Tokenrespbody>> {
            override fun onResponse(
                call: Call<ResponseWrapper<Tokenrespbody>>,
                response: Response<ResponseWrapper<Tokenrespbody>>
            ) {
                if (response.code() == 502){
                    tokenResfreshView.onTokenRefreshFailure(response.code(),"네트워크 오류 발생")
                }
                else {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> tokenResfreshView.onTokenRefreshSuccess(resp.responseBody)
                        else -> {
                            tokenResfreshView.onTokenRefreshFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<Tokenrespbody>>, t: Throwable) {
                tokenResfreshView.onTokenRefreshFailure(400, "네트워크 오류 발생")
            }
        })
    }


    fun TokenSignin(idtoken: TokenSigninRequest) {
        val Service = getRepositforLogin().create(UserRetrofitInterface::class.java)
        Service.tokensignin(idtoken).enqueue(object : Callback<ResponseWrapper<Tokenrespbody>> {
            override fun onResponse(
                call: Call<ResponseWrapper<Tokenrespbody>>,
                response: Response<ResponseWrapper<Tokenrespbody>>
            ) {
                if (response.code() == 502){
                    tokenSigninView.onTokenSigninFailure(response.code(),"네트워크 오류 발생")
                }
                else {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> tokenSigninView.onTokenSigninSuccess(resp.responseBody)
                        else -> {
                            tokenSigninView.onTokenSigninFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<Tokenrespbody>>, t: Throwable) {
                tokenSigninView.onTokenSigninFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun getUserinfo(accesstoken: String) {
        val getUserinfoService = getRepositforLogin().create(UserRetrofitInterface::class.java)
        getUserinfoService.getuserinfo(accesstoken).enqueue(object : Callback<ResponseWrapper<Userinfo>> {
            override fun onResponse(
                call: Call<ResponseWrapper<Userinfo>>,
                response: Response<ResponseWrapper<Userinfo>>
            ) {
                if (response.code() == 502){
                    getuserinfoView.onGetUinfoFailure(response.code(),"네트워크 오류 발생")
                }
                else {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> getuserinfoView.onGetUinfoSuccess(resp.responseBody)
                        else -> {
                            getuserinfoView.onGetUinfoFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<Userinfo>>, t: Throwable) {
                getuserinfoView.onGetUinfoFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun signup(userRequest: UserRequest,accesstoken : String) {
        val signupService = getRepositforLogin().create(UserRetrofitInterface::class.java)
        signupService.signup(userRequest,accesstoken).enqueue(object : Callback<ResponseWrapper<Userbody>> {
            override fun onResponse(call: Call<ResponseWrapper<Userbody>>, response: Response<ResponseWrapper<Userbody>>) {
                //404일때 예외처리도 해야할 듯
                val resp = response.body()!!
                when (resp.code){
                    1000 -> signupView.onSignupSuccess(resp.responseBody)
                    else -> {
                        signupView.onSignupFailure(resp.code,resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<Userbody>>, t: Throwable) {
                signupView.onSignupFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun isfavorok(accesstoken : String) {
        val isfavorokService = getRepositforLogin().create(UserRetrofitInterface::class.java)
        isfavorokService.isfavorok(accesstoken).enqueue(object : Callback<ResponseWrapper<Isfavorok>> {
            override fun onResponse(
                call: Call<ResponseWrapper<Isfavorok>>,
                response: Response<ResponseWrapper<Isfavorok>>
            ) {
                if (response.body()!!.responseBody.doInit){
                    iSfavorokView.onFavorFailure(response.body()!!.code,response.body()!!.message)
                }
                else{
                    iSfavorokView.onFavorSuccess(response.body()!!.responseBody)
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<Isfavorok>>, t: Throwable) {
                iSfavorokView.onFavorFailure(400, "네트워크 오류 발생")
            }
        })
    }

}