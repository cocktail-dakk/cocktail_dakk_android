package com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService

interface MainrecView {
    fun onMainrecLoading()
    fun onMainrecSuccess(mainreclist : List<MainRec>)
    fun onSignUpFailure(code : Int, message : String)

}
