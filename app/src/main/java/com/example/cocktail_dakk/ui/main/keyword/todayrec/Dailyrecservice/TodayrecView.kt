package com.example.cocktail_dakk.ui.main.keyword.todayrec.Dailyrecservice

interface TodayrecView {
    fun onMainrecLoading()
    fun onMainrecSuccess(result: List<TodayrecResult>)
    fun onSignUpFailure(code:Int, message:String)
}