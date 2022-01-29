package com.example.cocktail_dakk.ui.search.searchService


interface SearchView {
    fun onSearchLoading()
    fun onSearchSuccess(searchresult: SearchResult)
    fun onSearchFailure(code:Int, message:String)
}