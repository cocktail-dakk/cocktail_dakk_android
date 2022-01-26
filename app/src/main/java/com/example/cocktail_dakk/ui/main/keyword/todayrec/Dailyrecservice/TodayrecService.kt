package com.example.cocktail_dakk.ui.main.keyword.todayrec.Dailyrecservice

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodayrecService {
    private lateinit var todayrecView: TodayrecView

    //이걸 왜하지?
    fun settodayrecView(todayrecView: TodayrecView){
        this.todayrecView = todayrecView
    }


    fun todayRec(){
        val retrofit = Retrofit.Builder().baseUrl("http://ec2-3-38-87-27.ap-northeast-2.compute.amazonaws.com:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val todayRecService = retrofit.create(TodayrecRetrofitInterface::class.java)
//        val mainRecService = getReposit().create(MainrecRetrofitInterface::class.java)
        todayrecView.onMainrecLoading()
        todayRecService.todayRec().enqueue(object : Callback<TodayrecResponseList> {
            override fun onResponse(
                call: Call<TodayrecResponseList>,
                response: Response<TodayrecResponseList>
            ) {
                val resp = response.body()!!
                Log.d("test", resp.toString())
                todayrecView.onMainrecSuccess(resp.toString())
            }
            override fun onFailure(call: Call<TodayrecResponseList>, t: Throwable) {
                Log.d("test", call.toString())
                todayrecView.onSignUpFailure()
            }
        })
    }
}