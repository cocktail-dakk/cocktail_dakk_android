package com.umcapplunching.cocktail_dakk.ui.mypage.mypageService

import com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService.ratingResponse

interface MypageView{
    fun onMypageLoading()
    fun onMypageSuccess(mypagebody : MypageBody)
    fun onMypageFailure(code:Int, message:String)
}


