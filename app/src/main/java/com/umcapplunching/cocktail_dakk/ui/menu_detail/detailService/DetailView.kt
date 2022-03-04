package com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService


interface DetailView{
    fun onDetailLoading()
    fun onDetailSuccess(result: detail_Cocktail)
    fun onDetailFailure(code:Int, message:String)
}

interface RatingView{
    fun onRatingLoading()
    fun onRatingSuccess(result: ratingResponse)
    fun onRatingFailure(code:Int, message:String)
}


