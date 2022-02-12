package com.example.cocktail_dakk.ui.mypage.mypageService

import com.example.cocktail_dakk.ui.menu_detail.detailService.ratingResponse

interface MypageView{
    fun onMypageLoading()
    fun onMypageSuccess(mypagebody : MypageBody)
    fun onMypageFailure(code:Int, message:String)
}


