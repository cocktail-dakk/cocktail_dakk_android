package com.umcapplunching.cocktail_dakk.ui.main.mainrecommand.MainrecService

interface MainrecView {
    fun onMainrecLoading()
    fun onMainrecSuccess(mainreclist : Mainrec)
    fun onMainrecFailure(code : Int, message : String)

}
