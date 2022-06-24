package com.umcapplunching.cocktail_dakk.ui.main.mainrecommand.MainrecService

import android.util.Log
import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import com.umcapplunching.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainrecService {
    private lateinit var mainrecView: MainrecView
    fun setmainrecView(mainrecView: MainrecView){
        this.mainrecView = mainrecView
    }

    fun mainRec(){
        val mainRecService = getReposit().create(MainrecRetrofitInterface::class.java)

        mainrecView.onMainrecLoading()
        mainRecService.MainRec().enqueue(object : Callback<ResponseWrapper<Mainrec>> {
            override fun onResponse(call: Call<ResponseWrapper<Mainrec>>, response: Response<ResponseWrapper<Mainrec>>) {
                if (response.code() == 401){
                    mainrecView.onSignUpFailure(5000,"토큰 만료")
                }
                else {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> mainrecView.onMainrecSuccess(resp.responseBody)
                        2004 -> {
                            mainrecView.onSignUpFailure(resp.code, resp.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<Mainrec>>, t: Throwable) {
                mainrecView.onSignUpFailure(400,"네트워크 오류가 발생했습니다.")
            }
        })
    }

}