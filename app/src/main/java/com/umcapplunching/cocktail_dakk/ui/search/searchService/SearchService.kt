package com.umcapplunching.cocktail_dakk.ui.search.searchService

import android.util.Log
import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import com.umcapplunching.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SearchService {

    private lateinit var searchView: SearchView

    fun setsearchView(searchView: SearchView) {
        this.searchView = searchView
    }




    // 필터링
    suspend fun filter(
        page: Int,
        keywordlist: List<String>,
        mindosu: Int,
        maxdosu: Int,
        drinklist: List<String>,
    ) {
        val filterService = getReposit().create(SearchRetrofitInterface::class.java)
        searchView.onFilterLoading()
        try {
            val result = filterService.filter(page, keywordlist, mindosu, maxdosu, drinklist)
//            if (result.code== 401) {
//                searchView.onSearchFailure(5000, "토큰 만료")
//            } else {
            //            }
            Log.d("FilterAPI", result.toString())
            when (result.code) {
                1000 -> searchView.onFilterSuccess(result.responseBody)
                else -> {
                    searchView.onFilterFailure(result.code, result.message)
                }
            }
        } catch (e: Exception) {
            Log.d("FilterAPI", e.toString())
        }
    }

    // 필터 후 페이징
    fun filterpaging(
        page: Int,
        keywordlist: List<String>,
        mindosu: Int,
        maxdosu: Int,
        drinklist: List<String>,
    ) {
        val filterService = getReposit().create(SearchRetrofitInterface::class.java)
        searchView.onPagingLoading()
        filterService.filter_paging(page, keywordlist, mindosu, maxdosu, drinklist)
            .enqueue(object : Callback<ResponseWrapper<SearchResult>> {
                override fun onResponse(
                    call: Call<ResponseWrapper<SearchResult>>,
                    response: Response<ResponseWrapper<SearchResult>>,
                ) {
                    if (response.code() == 401) {
                        searchView.onSearchFailure(5000, "토큰 만료")
                    } else {
                        val resp = response.body()!!
                        Log.d("FilterAPI", resp.toString())
                        when (resp.code) {
                            1000 -> searchView.onPagingSuccess(resp.responseBody)
                            else -> {
                                searchView.onPagingFailure(resp.code, resp.message)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseWrapper<SearchResult>>, t: Throwable) {
                    searchView.onSearchFailure(400, "네트워크 오류 발생")
                }
            })

    }

    // 칵테일 검색
    suspend fun search(inputstr: String) {
        val searchService = getReposit().create(SearchRetrofitInterface::class.java)
        searchView.onSearchLoading()
        val searchresult = searchService.searchcoroutine(inputstr)
        if (searchresult.code == 1000) {
            searchView.onSearchSuccess(searchresult.responseBody)
        } else {
            searchView.onSearchFailure(5000, "토큰 만료")
        }
    }

    // 검색어 Paging
    fun paging(page: Int, inputstr: String) {
        val searchService = getReposit().create(SearchRetrofitInterface::class.java)
        searchView.onPagingLoading()
        searchService.paging(page, inputstr).enqueue(object : Callback<ResponseWrapper<SearchResult>> {
            override fun onResponse(
                call: Call<ResponseWrapper<SearchResult>>,
                response: Response<ResponseWrapper<SearchResult>>,
            ) {
                if (response.code() == 401) {
                    searchView.onSearchFailure(5000, "토큰 만료")
                } else {
                    val resp = response.body()!!
                    Log.d("Search_Paging_API", resp.toString())
                    when (resp.code) {
                        1000 -> searchView.onPagingSuccess(resp.responseBody)
                        else -> {
                            searchView.onPagingFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<SearchResult>>, t: Throwable) {
                searchView.onPagingFailure(400, "네트워크 오류 발생")
            }
        })
    }

}
