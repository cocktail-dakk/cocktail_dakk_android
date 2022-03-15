package com.umcapplunching.cocktail_dakk.ui.search.searchService

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcapplunching.cocktail_dakk.utils.getReposit
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService : ViewModel(){

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
        filterView.onFilterLoading()
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
                        1000 -> filterView.onFilterSuccess(resp.searchresult)
                        else -> {
                            filterView.onFilterFailure(resp.code, resp.message)
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
        filterpagingView.onFilterpagingLoading()
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
                        1000 -> filterpagingView.onFilterpagingSuccess(resp.searchresult)
                        else -> {
                            filterpagingView.onFilterpagingFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<SearchResponce>, t: Throwable) {
                searchView.onSearchFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun search(jwt : String,inputstr : String) = viewModelScope.launch {
        val searchService = getReposit().create(SearchRetrofitInterface::class.java)
        searchView.onSearchLoading()
        try {
            val searchResponce = searchService.searchCoroutine(jwt,inputstr)
            if (searchResponce.code == 401){
                searchView.onSearchFailure(5000,"토큰 만료")
            }else{
                searchView.onSearchSuccess(searchResponce.searchresult)
            }
        }catch (e : Exception) {
            searchView.onSearchFailure(400,"인터넷 연결을 확인해 주세요.")
        }

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
//
//            override fun onFailure(call: Call<SearchResponce>, t: Throwable) {
//                searchView.onSearchFailure(400, "네트워크 오류 발생")
//            }
//
//        })
    }

    fun paging(jwt : String,page : Int,inputstr: String) = viewModelScope.launch{
        val searchService = getReposit().create(SearchRetrofitInterface::class.java)
        pagingView.onPagingLoading()
        try {
            val searchResponce = searchService.pagingCoroutine(jwt,page,inputstr)
            if (searchResponce.code == 401){
                searchView.onSearchFailure(5000,"토큰 만료")
            }else{
                pagingView.onPagingSuccess(searchResponce.searchresult)
            }
        }catch (e : Exception) {
            pagingView.onPagingFailure(400,"인터넷 연결을 확인해 주세요.")
        }

//        val searchService = getReposit().create(SearchRetrofitInterface::class.java)
//        pagingView.onPagingLoading()
//        searchService.paging(jwt ,page,inputstr).enqueue(object : Callback<SearchResponce>{
//            override fun onResponse(
//                call: Call<SearchResponce>,
//                response: Response<SearchResponce>
//            ) {
//                if (response.code() == 401){
//                    searchView.onSearchFailure(5000,"토큰 만료")
//                }
//                else {
//                    val resp = response.body()!!
//                    Log.d("Search_Paging_API", resp.toString())
//                    when (resp.code) {
//                        1000 -> pagingView.onPagingSuccess(resp.searchresult)
//                        else -> {
//                            pagingView.onPagingFailure(resp.code, resp.message)
//                        }
//                    }
//                }
//            }
//            override fun onFailure(call: Call<SearchResponce>, t: Throwable) {
//                pagingView.onPagingFailure(400, "네트워크 오류 발생")
//            }
//        })
    }
}
