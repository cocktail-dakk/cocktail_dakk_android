package com.example.cocktail_dakk.ui.main.keyword.todayrec.Dailyrecservice

interface TodayrecView {
    fun onMainrecLoading()
    fun onMainrecSuccess(result: List<Result>)
    fun onSignUpFailure(code:Int, message:String)
}