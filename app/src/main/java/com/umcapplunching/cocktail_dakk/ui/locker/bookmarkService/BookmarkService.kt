package com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService

import android.util.Log
import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import com.umcapplunching.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkService {

    private lateinit var getislikeView: getIsLikeView

    fun setgetisLikeView(getislikeView: getIsLikeView) {
        this.getislikeView = getislikeView
    }

    fun getisLikeCocktail() {
        val Service = getReposit().create(BookmarkRetrofitInterface::class.java)
        getislikeView.ongetIsLikeLoading()
        Service.getIsLikeCocktail().enqueue(object : Callback<ResponseWrapper<List<BookmarkBody>>> {
            override fun onResponse(
                call: Call<ResponseWrapper<List<BookmarkBody>>>,
                response: Response<ResponseWrapper<List<BookmarkBody>>>
            ){
                if (response.code() == 401){
                    getislikeView.ongetIsLikeFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> getislikeView.ongetIsLikeSuccess(resp.responseBody)
                        else -> {
                            getislikeView.ongetIsLikeFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<List<BookmarkBody>>>, t: Throwable) {
                getislikeView.ongetIsLikeFailure(400, "네트워크 오류 발생")
            }
        })
    }
}