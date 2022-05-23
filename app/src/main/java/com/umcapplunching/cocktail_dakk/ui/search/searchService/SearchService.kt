package com.umcapplunching.cocktail_dakk.ui.search.searchService

import android.util.Log
import com.umcapplunching.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService {

    private lateinit var searchView: SearchView
    private lateinit var pagingView: PagingView
    private lateinit var filterView: FilterView
    private lateinit var islikeView: IslikeView
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
    fun setislikeView(islikeView: IslikeView) {
        this.islikeView = islikeView
    }

    fun DisLike(jwt : String, cocktailid : Int) {
        val Service = getReposit().create(SearchRetrofitInterface::class.java)
        islikeView.onIsLikeLoading()
        Service.disLikeCocktail(jwt, cocktailid).enqueue(object : Callback<IsLikeResponse>{
            override fun onResponse(
                call: Call<IsLikeResponse>,
                response: Response<IsLikeResponse>
            ) {
                if (response.code() == 401){
                    searchView.onSearchFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    Log.d("DisLikeAPI", resp.toString())
                    when (resp.code) {
                        1000 -> islikeView.onIsLikeSuccess(resp)
                        else -> {
                            islikeView.onIsLikeFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<IsLikeResponse>, t: Throwable) {
                islikeView.onIsLikeFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun IsLike(jwt : String, cocktailid : Int) {
        val Service = getReposit().create(SearchRetrofitInterface::class.java)
        islikeView.onIsLikeLoading()
        Service.isLikeCocktail(jwt, cocktailid).enqueue(object : Callback<IsLikeResponse>{
            override fun onResponse(
                call: Call<IsLikeResponse>,
                response: Response<IsLikeResponse>
            ) {
                if (response.code() == 401){
                    searchView.onSearchFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    Log.d("IsLikeAPI", resp.toString())
                    when (resp.code) {
                        1000 -> islikeView.onIsLikeSuccess(resp)
                        else -> {
                            islikeView.onIsLikeFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<IsLikeResponse>, t: Throwable) {
                islikeView.onIsLikeFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun filter(jwt : String, page : Int, keywordlist: List<String>,mindosu: Int, maxdosu: Int, drinklist: List<String>) {
        val filterService = getReposit().create(SearchRetrofitInterface::class.java)
        searchView.onFilterLoading()
        filterService.filter(jwt,page,keywordlist,mindosu, maxdosu,drinklist).enqueue(object : Callback<SearchResponce>{
            override fun onResponse(
                call: Call<SearchResponce>,
                response: Response<SearchResponce>
            ) {
                if (response.code() == 401){
                    searchView.onSearchFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    Log.d("FilterAPI", resp.toString())
                    when (resp.code) {
                        1000 -> searchView.onFilterSuccess(resp.searchresult)
                        else -> {
                            searchView.onFilterFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<SearchResponce>, t: Throwable) {
                searchView.onSearchFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun filterpaging(jwt : String, page : Int, keywordlist: List<String>,mindosu: Int, maxdosu: Int, drinklist: List<String>) {
        val filterService = getReposit().create(SearchRetrofitInterface::class.java)
        searchView.onPagingLoading()
        filterService.filter(jwt, page,keywordlist,mindosu, maxdosu,drinklist).enqueue(object : Callback<SearchResponce>{
            override fun onResponse(
                call: Call<SearchResponce>,
                response: Response<SearchResponce>
            ) {
                if (response.code() == 401){
                    searchView.onSearchFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    Log.d("FilterAPI", resp.toString())
                    when (resp.code) {
                        1000 -> searchView.onPagingSuccess(resp.searchresult)
                        else -> {
                            searchView.onPagingFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<SearchResponce>, t: Throwable) {
                searchView.onSearchFailure(400, "네트워크 오류 발생")
            }
        })
    }

//    fun search(jwt : String,inputstr : String) {
//        val searchService = getReposit().create(SearchRetrofitInterface::class.java)
//        searchView.onSearchLoading()
//        searchService.search(jwt, inputstr).enqueue(object : Callback<SearchResponce>{
//            override fun onResponse(
//                call: Call<SearchResponce>,
//                response: Response<SearchResponce>
//            ) {
//                if (response.code() == 401){
//                    searchView.onSearchFailure(5000,"토큰 만료")
//                }
//                else {
//                Log.d("SearchTest", response.code().toString() + response.toString())
//                    val resp = response.body()!!
//                    Log.d("SearchTest", resp.toString())
//                    when (resp.code) {
//                        1000 -> searchView.onSearchSuccess(resp.searchresult)
//                        else -> {
//                            searchView.onSearchFailure(resp.code, resp.message)
//                        }
//                    }
//                }
//            }
//            override fun onFailure(call: Call<SearchResponce>, t: Throwable) {
//                searchView.onSearchFailure(400, "네트워크 오류 발생")
//            }
//
//        })
//    }

    suspend fun search(jwt : String, inputstr : String) {
        val searchService = getReposit().create(SearchRetrofitInterface::class.java)
        searchView.onSearchLoading()
        var searchresult = searchService.searchcoroutine(jwt, inputstr)
        if (searchresult.code == 1000){
            searchView.onSearchSuccess(searchresult.searchresult)
        }
        else{
            searchView.onSearchFailure(5000,"토큰 만료")
        }
    }

    fun paging(jwt : String,page : Int,inputstr: String){
        val searchService = getReposit().create(SearchRetrofitInterface::class.java)
        searchView.onPagingLoading()
        searchService.paging(jwt ,page,inputstr).enqueue(object : Callback<SearchResponce>{
            override fun onResponse(
                call: Call<SearchResponce>,
                response: Response<SearchResponce>
            ) {
                if (response.code() == 401){
                    searchView.onSearchFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    Log.d("Search_Paging_API", resp.toString())
                    when (resp.code) {
                        1000 -> searchView.onPagingSuccess(resp.searchresult)
                        else -> {
                            searchView.onPagingFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<SearchResponce>, t: Throwable) {
                pagingView.onPagingFailure(400, "네트워크 오류 발생")
            }
        })
    }
}
