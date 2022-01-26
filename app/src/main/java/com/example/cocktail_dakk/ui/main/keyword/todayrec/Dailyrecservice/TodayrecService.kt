package com.example.cocktail_dakk.ui.main.keyword.todayrec.Dailyrecservice

import android.util.Log
import com.example.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodayrecService {
    private lateinit var todayrecView: TodayrecView

    fun settodayrecView(todayrecView: TodayrecView){
        this.todayrecView = todayrecView
    }


    fun todayRec(){
//        val retrofit = Retrofit.Builder().baseUrl("http://ec2-3-38-87-27.ap-northeast-2.compute.amazonaws.com:8080").addConverterFactory(
//            GsonConverterFactory.create()).build()
//        val todayRecService = retrofit.create(TodayrecRetrofitInterface::class.java)
        val todayRecService = getReposit().create(TodayrecRetrofitInterface::class.java)
        todayrecView.onMainrecLoading()
        todayRecService.todayRec().enqueue(object : Callback<TodayrecommandResponse> {
            override fun onResponse(
                call: Call<TodayrecommandResponse>,
                response: Response<TodayrecommandResponse>
            ) {
                val resp = response.body()!!
                when(resp.code){
                    1000 -> todayrecView.onMainrecSuccess(resp.result)
                    else -> {
                        todayrecView.onSignUpFailure(resp.code, resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<TodayrecommandResponse>, t: Throwable) {
                todayrecView.onSignUpFailure(400,"네트워크 오류가 발생했습니다.")
            }
        })
    }
}