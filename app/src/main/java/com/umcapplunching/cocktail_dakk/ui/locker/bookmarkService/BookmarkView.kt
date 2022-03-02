package com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService

import com.umcapplunching.cocktail_dakk.ui.search.searchService.IsLikeResponse

interface getIsLikeView {
    fun ongetIsLikeLoading()
    fun ongetIsLikeSuccess(getislikebody: List<BookmarkBody>)
    fun ongetIsLikeFailure(code:Int, message:String)
}
