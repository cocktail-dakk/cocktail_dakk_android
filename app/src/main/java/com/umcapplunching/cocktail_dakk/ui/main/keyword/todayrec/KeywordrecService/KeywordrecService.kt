package com.umcapplunching.cocktail_dakk.ui.main.keyword.todayrec.KeywordrecService

import android.util.Log
import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import com.umcapplunching.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KeywordrecService {
    private lateinit var todayrecView: TodayrecView
    private lateinit var keywordrecView: KeywordrecView

    fun settodayrecView(todayrecView: TodayrecView){
        this.todayrecView = todayrecView
    }
    fun setkeywordrecView(keywordrecView: KeywordrecView){
        this.keywordrecView = keywordrecView
    }

    fun keywordRec(){
        val keywordrecRecService = getReposit().create(KeywordrecRetrofitInterface::class.java)
        keywordrecView.onKeywordrecLoading()
        keywordrecRecService.keywordRec().enqueue(object : Callback<ResponseWrapper<List<KeywordrecResult>>>{
            override fun onResponse(
                call: Call<ResponseWrapper<List<KeywordrecResult>>>,
                response: Response<ResponseWrapper<List<KeywordrecResult>>>
            ) {
                if (response.code() == 401){
                    keywordrecView.onKeywordrecFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    Log.d("keywordrec_API", resp.toString())
                    when (resp.code) {
                        1000 -> {
                            keywordrecView.onKeywordrecSuccess(resp.responseBody)
                        }
                        else -> {
                            keywordrecView.onKeywordrecFailure(resp.code, resp.message)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseWrapper<List<KeywordrecResult>>>, t: Throwable) {
                keywordrecView.onKeywordrecFailure(400,"네트워킹 오류발생")
            }

        })

    }

    fun todayRec(){
        val todayRecService = getReposit().create(KeywordrecRetrofitInterface::class.java)
        todayrecView.onTodayrecLoading()
        todayRecService.todayRec().enqueue(object : Callback<ResponseWrapper<List<TodayrecResult>>> {
            override fun onResponse(
                call: Call<ResponseWrapper<List<TodayrecResult>>>,
                response: Response<ResponseWrapper<List<TodayrecResult>>>
            ) {
                if (response.code() == 401){
                    keywordrecView.onKeywordrecFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> {
                            for (i in 0..resp.responseBody.size - 1) {
                                // 이미지 없을 때
                                if (resp.responseBody[i].recommendImageURL == null) {
                                    resp.responseBody[i].recommendImageURL =
                                        "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/today/BlueStar.webp"
                                }
                            }
                            todayrecView.onTodayrecSuccess(resp.responseBody)
                        }
                        else -> {
                            todayrecView.onTodayrecFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<List<TodayrecResult>>>, t: Throwable) {
                todayrecView.onTodayrecFailure(400,"네트워크 오류가 발생했습니다.")
            }
        })
    }
}