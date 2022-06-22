package com.umcapplunching.cocktail_dakk.ui.search

import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchResult
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchRetrofitInterface
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class SearchRepository constructor(private val searchRetrofitInterface: SearchRetrofitInterface) {
    suspend fun search(searchStr: String) = searchRetrofitInterface.searchcoroutine(searchStr)

    fun paging(paging: Int, inputStr: String) = searchRetrofitInterface.paging(paging, inputStr)

    suspend fun filter(
        paging: Int,
        keywordlist: List<String>,
        mindosu: Int,
        maxdosu: Int,
        drinklist: List<String>
    ) = searchRetrofitInterface.filter(paging, keywordlist, mindosu, maxdosu, drinklist)

    fun filter_paging(
        paging: Int,
        keywordlist: List<String>,
        mindosu: Int,
        maxdosu: Int,
        drinklist: List<String>
    ) = searchRetrofitInterface.filter_paging(paging, keywordlist, mindosu, maxdosu, drinklist)


}