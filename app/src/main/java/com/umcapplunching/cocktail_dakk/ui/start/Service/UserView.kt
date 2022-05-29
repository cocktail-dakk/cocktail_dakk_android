package com.umcapplunching.cocktail_dakk.ui.start.Service

interface SignupView {
    fun onSignupSuccess(userbody: Userbody)
    fun onSignupFailure(code:Int, message:String)
}

interface iSFavorokView {
    fun onFavorSuccess(isfavorok: Isfavorok)
    fun onFavorFailure(code:Int, message:String)
}

interface getUserInfoView {
//    fun onGetUinfoLoading()
    fun onGetUinfoSuccess(userinfo: Userinfo)
    fun onGetUinfoFailure(code:Int, message:String)
}

interface TokenSigninView {
    fun onTokenSigninSuccess(tokenSigninbody: Tokenrespbody)
    fun onTokenSigninFailure(code:Int, message:String)
}

interface TokenResfreshView {
    fun onTokenRefreshSuccess(tokenSigninbody: Tokenrespbody)
    fun onTokenRefreshFailure(code:Int, message:String)
}



