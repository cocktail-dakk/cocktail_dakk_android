package com.umcapplunching.cocktail_dakk.ui.mypage.mypageService

interface MypageView{
    fun onMypageSuccess(mypagebody : MypageBody)
    fun onMypageFailure(code:Int, message:String)
}


