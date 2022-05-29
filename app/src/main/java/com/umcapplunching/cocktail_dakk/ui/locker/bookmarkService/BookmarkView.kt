package com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService

interface getIsLikeView {
    fun ongetIsLikeLoading()
    fun ongetIsLikeSuccess(getislikebody: List<BookmarkBody>)
    fun ongetIsLikeFailure(code:Int, message:String)
}
