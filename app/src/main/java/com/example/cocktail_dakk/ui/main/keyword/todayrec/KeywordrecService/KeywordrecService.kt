package com.example.cocktail_dakk.ui.main.keyword.todayrec.KeywordrecService

import android.util.Log
import com.example.cocktail_dakk.utils.getReposit
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

    fun keywordRec(jwt : String){
        val keywordrecRecService = getReposit().create(KeywordrecRetrofitInterface::class.java)
        keywordrecView.onKeywordrecLoading()
        keywordrecRecService.keywordRec(jwt).enqueue(object : Callback<KeywordrecResponse>{
            override fun onResponse(
                call: Call<KeywordrecResponse>,
                response: Response<KeywordrecResponse>
            ) {
                if (response.code() == 401){
                    keywordrecView.onKeywordrecFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    Log.d("keywordrec_API", resp.toString())
                    when (resp.code) {
                        1000 -> {
                            keywordrecView.onKeywordrecSuccess(resp.result)
                        }
                        else -> {
                            keywordrecView.onKeywordrecFailure(resp.code, resp.message)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<KeywordrecResponse>, t: Throwable) {
                keywordrecView.onKeywordrecFailure(400,"네트워킹 오류발생")
            }

        })

    }

    fun todayRec(jwt : String){
        val todayRecService = getReposit().create(KeywordrecRetrofitInterface::class.java)
        todayrecView.onTodayrecLoading()
        todayRecService.todayRec(jwt).enqueue(object : Callback<TodayrecommandResponse> {
            override fun onResponse(
                call: Call<TodayrecommandResponse>,
                response: Response<TodayrecommandResponse>
            ) {
                if (response.code() == 401){
                    keywordrecView.onKeywordrecFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> {
                            for (i in 0..resp.result.size - 1) {
                                if (resp.result[i].recommendImageURL == null) {
                                    resp.result[i].recommendImageURL =
                                        "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/today/BlueStar.webp"
                                }
                            }
                            todayrecView.onTodayrecSuccess(resp.result)
                        }
                        else -> {
                            todayrecView.onTodayrecFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<TodayrecommandResponse>, t: Throwable) {
                todayrecView.onTodayrecFailure(400,"네트워크 오류가 발생했습니다.")
            }
        })
    }
}