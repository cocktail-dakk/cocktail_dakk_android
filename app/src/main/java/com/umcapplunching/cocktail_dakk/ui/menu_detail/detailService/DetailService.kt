package com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService

import android.util.Log
import com.umcapplunching.cocktail_dakk.ui.search.searchService.PagingView
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchResponce
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchRetrofitInterface
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchView
import com.umcapplunching.cocktail_dakk.utils.getReposit
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

    fun rating(accesstoken:String,detailrequest : DetailRequest) {
        val ratingService = getReposit().create(DetailRetrofitInterface::class.java)
        ratingView.onRatingLoading()
        ratingService.rating(accesstoken,detailrequest).enqueue(object : Callback<DetailRatingResponse> {
            override fun onResponse(
                call: Call<DetailRatingResponse>,
                response: Response<DetailRatingResponse>
            ) {
                Log.d("RatingAPI",response.toString())
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

    fun detail(accesstoken : String,id : Int) {
        val detailService = getReposit().create(DetailRetrofitInterface::class.java)
        detailView.onDetailLoading()

        detailService.detail(accesstoken,id).enqueue(object : Callback<detailResponse> {
            override fun onResponse(
                call: Call<detailResponse>,
                response: Response<detailResponse>
            ) {
                if (response.code() == 401){
                    detailView.onDetailFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    Log.d("DetailService", resp.toString())
                    when (resp.code) {
                        1000 -> detailView.onDetailSuccess(resp.result)
                        else -> {
                            detailView.onDetailFailure(resp.code, resp.message)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<detailResponse>, t: Throwable) {
                detailView.onDetailFailure(400, "네트워크 오류 발생")
            }

        })
    }
}
