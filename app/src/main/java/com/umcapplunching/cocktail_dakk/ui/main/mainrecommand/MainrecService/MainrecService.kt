package com.umcapplunching.cocktail_dakk.ui.main.mainrecommand.MainrecService

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcapplunching.cocktail_dakk.utils.getReposit
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainrecService : ViewModel() {
    private lateinit var mainrecView: MainrecView
    fun setmainrecView(mainrecView: MainrecView){
        this.mainrecView = mainrecView
    }

    fun mainRec(jwt : String) = viewModelScope.launch{
        val mainRecService = getReposit().create(MainrecRetrofitInterface::class.java)
        mainrecView.onMainrecLoading()
        try {
            val mainrecommandResponse = mainRecService.MainRecCoroutin(jwt)
            if (mainrecommandResponse.code == 401){
                mainrecView.onMainrecFailure(5000,"토큰 만료")
            }else{
                mainrecView.onMainrecSuccess(mainrecommandResponse.mainrecList)
            }
        }catch (e : Exception) {
            mainrecView.onMainrecFailure(400,"인터넷 연결을 확인해 주세요")
        }

//        mainRecService.MainRec(jwt).enqueue(object : Callback<MainrecommandResponse> {
//            override fun onResponse(call: Call<MainrecommandResponse>, response: Response<MainrecommandResponse>) {
//                Log.d("Mainrec/API",response.toString())
//                if (response.code() == 401){
//                    mainrecView.onSignUpFailure(5000,"토큰 만료")
//                }
//                else {
//                    val resp = response.body()!!
//                    Log.d("Mainrec/API", resp.toString())
//                    when (resp.code) {
//                        1000 -> mainrecView.onMainrecSuccess(resp.mainrecList)
//                        2004 -> {
//                            mainrecView.onSignUpFailure(resp.code, resp.message)
//                        }
//                    }
//                }
//            }
//            override fun onFailure(call: Call<MainrecommandResponse>, t: Throwable) {
//                Log.d("MainRec/API-ERROR",t.message.toString())
//            }
//        })
    }

}