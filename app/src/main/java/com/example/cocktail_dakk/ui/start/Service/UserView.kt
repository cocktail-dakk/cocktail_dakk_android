package com.example.cocktail_dakk.ui.start.Service

import com.example.cocktail_dakk.ui.search.searchService.SearchResult

interface SignupView {
    fun onSignupLoading()
    fun onSignupSuccess(userbody: Userbody)
    fun onSignupFailure(code:Int, message:String)
}

interface AutoLoginView {
    fun onLoginLoading()
    fun onLoginSuccess(autologinbody: Autologinbody)
    fun onLoginFailure(code:Int, message:String)
}