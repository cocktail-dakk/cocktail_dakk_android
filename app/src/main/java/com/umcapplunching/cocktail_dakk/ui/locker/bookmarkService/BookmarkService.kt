package com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService

import android.util.Log
import com.umcapplunching.cocktail_dakk.ui.search.searchService.FilterpagingView
import com.umcapplunching.cocktail_dakk.ui.search.searchService.IsLikeResponse
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchRetrofitInterface
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchView
import com.umcapplunching.cocktail_dakk.ui.start.Service.getUserinfoResponse
import com.umcapplunching.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkService {

    private lateinit var getislikeView: getIsLikeView

    fun setgetisLikeView(getislikeView: getIsLikeView) {
        this.getislikeView = getislikeView
    }

    fun getisLikeCocktail(accesstoken : String) {
        val Service = getReposit().create(BookmarkRetrofitInterface::class.java)
        getislikeView.ongetIsLikeLoading()
        Service.getIsLikeCocktail(accesstoken).enqueue(object : Callback<BookmarkResponse> {
            override fun onResponse(
                call: Call<BookmarkResponse>,
                response: Response<BookmarkResponse>
            ){
                if (response.code() == 401){
                    getislikeView.ongetIsLikeFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    Log.d("getIsLikeAPI", resp.toString())
                    when (resp.code) {
                        1000 -> getislikeView.ongetIsLikeSuccess(resp.result)
                        else -> {
                            getislikeView.ongetIsLikeFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                getislikeView.ongetIsLikeFailure(400, "네트워크 오류 발생")
            }
        })
    }
}