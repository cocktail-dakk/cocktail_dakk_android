package com.example.cocktail_dakk.ui.start.Service

import com.example.cocktail_dakk.ui.search.searchService.SearchResult

interface SignupView {
    fun onSignupLoading()
    fun onSignupSuccess(userbody: Userbody)
    fun onSignupFailure(code:Int, message:String)
}