package com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcapplunching.cocktail_dakk.utils.getReposit
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailService : ViewModel(){
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

    fun detail(accesstoken : String,id : Int) = viewModelScope.launch {
        val detailService = getReposit().create(DetailRetrofitInterface::class.java)
        detailView.onDetailLoading()
        try {
            val detailResponse = detailService.detailCoroutin(accesstoken,id)
            if (detailResponse.code == 401){
                detailView.onDetailFailure(5000,"토큰 만료")
            }else{
                detailView.onDetailSuccess(detailResponse.result)
            }
        }catch (e : Exception) {
            detailView.onDetailFailure(400,"인터넷 연결을 확인해 주세요")
        }
//        detailService.detail(accesstoken,id).enqueue(object : Callback<detailResponse> {
//            override fun onResponse(
//                call: Call<detailResponse>,
//                response: Response<detailResponse>
//            ) {
//                if (response.code() == 401){
//                    detailView.onDetailFailure(5000,"토큰 만료")
//                }
//                else {
//                    val resp = response.body()!!
//                    Log.d("DetailService", resp.toString())
//                    when (resp.code) {
//                        1000 -> detailView.onDetailSuccess(resp.result)
//                        else -> {
//                            detailView.onDetailFailure(resp.code, resp.message)
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<detailResponse>, t: Throwable) {
//                detailView.onDetailFailure(400, "네트워크 오류 발생")
//            }
//
//        })
    }
}
