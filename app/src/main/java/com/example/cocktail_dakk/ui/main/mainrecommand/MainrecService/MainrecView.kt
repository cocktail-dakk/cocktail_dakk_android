package com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService

interface MainrecView {
    fun onMainrecLoading()
    fun onMainrecSuccess()
    fun onSignUpFailure(code : Int, message : String)

}
//interface SignUpView{
//    fun onSignUpLoading()
//    fun onSignUpSuccess()
//    fun onSignUpFailure(code : Int, message : String)
//}
