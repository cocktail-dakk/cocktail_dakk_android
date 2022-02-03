package com.example.cocktail_dakk.ui.search.searchService


interface SearchView {
    fun onSearchLoading()
    fun onSearchSuccess(searchresult: SearchResult)
    fun onSearchFailure(code:Int, message:String)
}

interface PagingView {
    fun onPagingLoading()
    fun onPagingSuccess(searchresult: SearchResult)
    fun onPagingFailure(code:Int, message:String)
}