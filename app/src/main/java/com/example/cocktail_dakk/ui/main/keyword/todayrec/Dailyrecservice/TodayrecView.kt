package com.example.cocktail_dakk.ui.main.keyword.todayrec.Dailyrecservice

interface TodayrecView {
    fun onMainrecLoading()
    fun onMainrecSuccess(message : String)
    fun onSignUpFailure()
}