package com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService


interface DetailView{
    fun onDetailLoading()
    fun onDetailSuccess(result: detail_Cocktail)
    fun onDetailFailure(code:Int, message:String)

    fun onRatingSuccess(result: ratingResponse)
    fun onRatingFailure(code:Int, message:String)

    fun onIsLikeSuccess(isLike : Boolean)
    fun onIsLikeFailure(code:Int, message:String)

}



