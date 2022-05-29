package com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService

import android.util.Log
import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchRetrofitInterface
import com.umcapplunching.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailService {
    private lateinit var detailView: DetailView

    fun setdetailView(detailView: DetailView) {
        this.detailView = detailView
    }

    fun rating(detailrequest : DetailRequest) {
        val ratingService = getReposit().create(DetailRetrofitInterface::class.java)
        ratingService.rating(detailrequest).enqueue(object : Callback<ResponseWrapper<ratingResponse>> {
            override fun onResponse(
                call: Call<ResponseWrapper<ratingResponse>>,
                response: Response<ResponseWrapper<ratingResponse>>
            ) {
                val resp = response.body()!!
                when (resp.code){
                    1000 -> detailView.onRatingSuccess(resp.responseBody)
                    else -> {
                        detailView.onRatingFailure(resp.code,resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<ratingResponse>>, t: Throwable) {
                detailView.onRatingFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun detail(id : Int) {
        val detailService = getReposit().create(DetailRetrofitInterface::class.java)
        detailView.onDetailLoading()

        detailService.detail(id).enqueue(object : Callback<ResponseWrapper<detail_Cocktail>> {
            override fun onResponse(
                call: Call<ResponseWrapper<detail_Cocktail>>,
                response: Response<ResponseWrapper<detail_Cocktail>>
            ) {
                if (response.code() == 401){
                    detailView.onDetailFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    Log.d("DetailService", resp.toString())
                    when (resp.code) {
                        1000 -> detailView.onDetailSuccess(resp.responseBody)
                        else -> {
                            detailView.onDetailFailure(resp.code, resp.message)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseWrapper<detail_Cocktail>>, t: Throwable) {
                detailView.onDetailFailure(400, "네트워크 오류 발생")
            }

        })
    }


    fun DisLike(cocktailid: Int) {
        val Service = getReposit().create(DetailRetrofitInterface::class.java)
        Service.disLikeCocktail(cocktailid).enqueue(object : Callback<ResponseWrapper<String?>> {
            override fun onResponse(
                call: Call<ResponseWrapper<String?>>,
                response: Response<ResponseWrapper<String?>>,
            ) {
                if (response.code() == 401) {
                    detailView.onIsLikeFailure(5000, "토큰 만료")
                } else {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> detailView.onIsLikeSuccess(false)
                        else -> {
                            detailView.onIsLikeFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<String?>>, t: Throwable) {
                detailView.onIsLikeFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun IsLike(cocktailid: Int) {
        val Service = getReposit().create(DetailRetrofitInterface::class.java)
        Service.isLikeCocktail(cocktailid).enqueue(object : Callback<ResponseWrapper<String?>> {
            override fun onResponse(
                call: Call<ResponseWrapper<String?>>,
                response: Response<ResponseWrapper<String?>>,
            ) {
                if (response.code() == 401) {
                    detailView.onIsLikeFailure(5000, "토큰 만료")
                } else {
                    val resp = response.body()!!
                    Log.d("IsLikeAPI", resp.toString())
                    when (resp.code) {
                        1000 -> detailView.onIsLikeSuccess(true)
                        else -> {
                            detailView.onIsLikeFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<String?>>, t: Throwable) {
                detailView.onIsLikeFailure(400, "네트워크 오류 발생")
            }
        })
    }
}
