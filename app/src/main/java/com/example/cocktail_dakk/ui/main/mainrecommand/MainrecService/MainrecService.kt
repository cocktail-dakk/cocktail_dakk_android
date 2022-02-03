package com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService

import android.util.Log
import com.example.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainrecService {
    private lateinit var mainrecView: MainrecView

    fun setmainrecView(mainrecView: MainrecView){
        this.mainrecView = mainrecView
    }


    fun mainRec(devicenum : String){
        val mainRecService = getReposit().create(MainrecRetrofitInterface::class.java)

        mainrecView.onMainrecLoading()
        mainRecService.MainRec(devicenum).enqueue(object : Callback<MainrecommandResponse> {
            override fun onResponse(call: Call<MainrecommandResponse>, response: Response<MainrecommandResponse>) {
                val resp = response.body()!!
                Log.d("Mainrec/API",resp.toString())
                when(resp.code){
                    1000 -> mainrecView.onMainrecSuccess(resp.mainrecList)
                    2004 -> {
                        mainrecView.onSignUpFailure(resp.code, resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<MainrecommandResponse>, t: Throwable) {
                Log.d("MainRec/API-ERROR",t.message.toString())
                mainrecView.onSignUpFailure(400,"네트워크 오류가 발생했습니다.")
            }
        })
    }

}