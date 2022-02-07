package com.example.cocktail_dakk.ui.menu_detail.detailService


interface DetailView{
    fun onDetailLoading()
    fun onDetailSuccess(result: detail_Cocktail)
    fun onDetailFailure(code:Int, message:String)
}

