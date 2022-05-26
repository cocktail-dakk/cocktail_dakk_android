package com.umcapplunching.cocktail_dakk.ui.search.searchService


interface SearchView {
    fun onSearchLoading()
    fun onSearchSuccess(searchresult: SearchResult)
    fun onSearchFailure(code:Int, message:String)

    fun onPagingLoading()
    fun onPagingSuccess(searchresult: SearchResult)
    fun onPagingFailure(code:Int, message:String)

    fun onFilterLoading()
    fun onFilterSuccess(searchresult: SearchResult)
    fun onFilterFailure(code:Int, message:String)
}

interface IslikeView {
    fun onIsLikeLoading()
    fun onIsLikeSuccess(isLikeResponse: IsLikeResponse)
    fun onIsLikeFailure(code:Int, message:String)
}
