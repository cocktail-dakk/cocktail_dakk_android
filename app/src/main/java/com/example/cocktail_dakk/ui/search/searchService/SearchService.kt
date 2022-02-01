package com.example.cocktail_dakk.ui.search.searchService

import android.util.Log
import com.example.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService {

    private lateinit var searchView: SearchView

    fun setsearchView(searchView: SearchView) {
        this.searchView = searchView
    }

    fun search(inputstr : String) {
//        val retrofit = Retrofit.Builder().baseUrl("http://ec2-3-38-87-27.ap-northeast-2.compute.amazonaws.com:8080").addConverterFactory(
//            GsonConverterFactory.create()).build()
//        val todayRecService = retrofit.create(TodayrecRetrofitInterface::class.java)
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
//            override fun onResponse(
//                call: Call<TodayrecommandResponse>,
//                response: Response<TodayrecommandResponse>
//            ) {
//                val resp = response.body()!!
//                when (resp.code) {
//                    1000 -> todayrecView.onMainrecSuccess(resp.result)
//                    else -> {
//                        todayrecView.onSignUpFailure(resp.code, resp.message)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<TodayrecommandResponse>, t: Throwable) {
//                todayrecView.onSignUpFailure(400, "네트워크 오류가 발생했습니다.")
//            }
//        })
    }
}
