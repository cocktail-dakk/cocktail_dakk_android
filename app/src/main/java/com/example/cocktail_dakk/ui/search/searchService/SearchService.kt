package com.example.cocktail_dakk.ui.search.searchService

import android.util.Log
import com.example.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query
import kotlin.math.max

class SearchService {

    private lateinit var searchView: SearchView
    private lateinit var pagingView: PagingView
    private lateinit var filterView: FilterView
    private lateinit var filterpagingView: FilterpagingView

    fun setsearchView(searchView: SearchView) {
        this.searchView = searchView
    }

    fun setpagingView(pagingView: PagingView) {
        this.pagingView = pagingView
    }

    fun setfilterView(filterView: FilterView) {
        this.filterView = filterView
    }
    fun setfilterPagingView(filterpagingView: FilterpagingView) {
        this.filterpagingView = filterpagingView
    }


    fun filter(page : Int, keywordlist: List<String>,mindosu: Int, maxdosu: Int, drinklist: List<String>) {
        val filterService = getReposit().create(SearchRetrofitInterface::class.java)
        filterView.onFilterLoading()
        filterService.filter(page,keywordlist,mindosu, maxdosu,drinklist).enqueue(object : Callback<SearchResponce>{
            override fun onResponse(
                call: Call<SearchResponce>,
                response: Response<SearchResponce>
            ) {
                val resp = response.body()!!
                Log.d("FilterAPI",resp.toString())
                when (resp.code){
                    1000 -> filterView.onFilterSuccess(resp.searchresult)
                    else -> {
                        filterView.onFilterFailure(resp.code,resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<SearchResponce>, t: Throwable) {
                searchView.onSearchFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun filterpaging(page : Int, keywordlist: List<String>,mindosu: Int, maxdosu: Int, drinklist: List<String>) {
        val filterService = getReposit().create(SearchRetrofitInterface::class.java)
        filterpagingView.onFilterpagingLoading()
        filterService.filter(page,keywordlist,mindosu, maxdosu,drinklist).enqueue(object : Callback<SearchResponce>{
            override fun onResponse(
                call: Call<SearchResponce>,
                response: Response<SearchResponce>
            ) {
                val resp = response.body()!!
                Log.d("FilterAPI",resp.toString())
                when (resp.code){
                    1000 -> filterpagingView.onFilterpagingSuccess(resp.searchresult)
                    else -> {
                        filterpagingView.onFilterpagingFailure(resp.code,resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<SearchResponce>, t: Throwable) {
                searchView.onSearchFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun search(inputstr : String) {
        val searchService = getReposit().create(SearchRetrofitInterface::class.java)
        searchView.onSearchLoading()
        searchService.search(inputstr).enqueue(object : Callback<SearchResponce>{
            override fun onResponse(
                call: Call<SearchResponce>,
                response: Response<SearchResponce>
            ) {
                val resp = response.body()!!
                Log.d("SearchTest",resp.toString())
                when (resp.code){
                    1000 -> searchView.onSearchSuccess(resp.searchresult)
                    else -> {
                        searchView.onSearchFailure(resp.code,resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponce>, t: Throwable) {
                searchView.onSearchFailure(400, "네트워크 오류 발생")
            }

        })
    }

    fun paging(page : Int,inputstr: String){
        val searchService = getReposit().create(SearchRetrofitInterface::class.java)
        pagingView.onPagingLoading()
        searchService.paging(page,inputstr).enqueue(object : Callback<SearchResponce>{
            override fun onResponse(
                call: Call<SearchResponce>,
                response: Response<SearchResponce>
            ) {
                val resp = response.body()!!
                Log.d("Search_Paging_API",resp.toString())
                when (resp.code){
                    1000 -> pagingView.onPagingSuccess(resp.searchresult)
                    else -> {
                        pagingView.onPagingFailure(resp.code,resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<SearchResponce>, t: Throwable) {
                pagingView.onPagingFailure(400, "네트워크 오류 발생")
            }
        })
    }
}
