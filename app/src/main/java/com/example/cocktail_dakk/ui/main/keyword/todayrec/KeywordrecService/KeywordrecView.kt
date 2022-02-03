package com.example.cocktail_dakk.ui.main.keyword.todayrec.KeywordrecService

interface TodayrecView {
    fun onTodayrecLoading()
    fun onTodayrecSuccess(result: List<TodayrecResult>)
    fun onTodayrecFailure(code:Int, message:String)
}

interface KeywordrecView {
    fun onKeywordrecLoading()
    fun onKeywordrecSuccess(result: List<KeywordrecResult>)
    fun onKeywordrecFailure(code:Int, message:String)
}