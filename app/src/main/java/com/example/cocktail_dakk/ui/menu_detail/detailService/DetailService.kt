package com.example.cocktail_dakk.ui.menu_detail.detailService

import android.util.Log
import com.example.cocktail_dakk.ui.search.searchService.PagingView
import com.example.cocktail_dakk.ui.search.searchService.SearchResponce
import com.example.cocktail_dakk.ui.search.searchService.SearchRetrofitInterface
import com.example.cocktail_dakk.ui.search.searchService.SearchView
import com.example.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailService {
    private lateinit var detailView: DetailView
    private lateinit var ratingView: RatingView

    fun setdetailView(detailView: DetailView) {
        this.detailView = detailView
    }
    fun setratingView(ratingView: RatingView) {
        this.ratingView = ratingView
    }

    fun rating(detailrequest : DetailRequest) {
        val ratingService = getReposit().create(DetailRetrofitInterface::class.java)
        ratingView.onRatingLoading()
        ratingService.rating(detailrequest).enqueue(object : Callback<DetailRatingResponse> {
            override fun onResponse(
                call: Call<DetailRatingResponse>,
                response: Response<DetailRatingResponse>
            ) {
                val resp = response.body()!!
                when (resp.code){
                    1000 -> ratingView.onRatingSuccess(resp.ratingrsponse)
                    else -> {
                        ratingView.onRatingFailure(resp.code,resp.message)
                    }
                }            }

            override fun onFailure(call: Call<DetailRatingResponse>, t: Throwable) {
                ratingView.onRatingFailure(400, "네트워크 오류 발생")
            }
        })
    }

    fun detail(jwt : String,id : Int) {
        val detailService = getReposit().create(DetailRetrofitInterface::class.java)
        detailView.onDetailLoading()

        detailService.detail(jwt,id).enqueue(object : Callback<detailResponse> {
            override fun onResponse(
                call: Call<detailResponse>,
                response: Response<detailResponse>
            ) {
                val resp = response.body()!!
                Log.d("DetailService",resp.toString())
                when (resp.code){
                    1000 -> detailView.onDetailSuccess(resp.result)
                    else -> {
                        detailView.onDetailFailure(resp.code,resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<detailResponse>, t: Throwable) {
                detailView.onDetailFailure(400, "네트워크 오류 발생")
            }

        })
    }
}
