package com.example.cocktail_dakk.ui.mypage.mypageService

import android.util.Log
import com.example.cocktail_dakk.ui.menu_detail.detailService.*
import com.example.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field

class MypageService {

    private lateinit var mypageview: MypageView

    fun setmypageView(mypageview: MypageView) {
        this.mypageview = mypageview
    }

    fun mypagemodify(accesstoken : String,mypagerequest: MypageRequest) {
        val mypagerService = getReposit().create(MypagerRetrofitInterface::class.java)

        mypageview.onMypageLoading()

        mypagerService.mypagemodify(accesstoken,mypagerequest).enqueue(object : Callback<MypageResponse> {
            override fun onResponse(
                call: Call<MypageResponse>,
                response: Response<MypageResponse>
            ) {
                if (response.code() == 401){
                    mypageview.onMypageFailure(5000,"토큰 만료")
                }
                    else {
                    val resp = response.body()!!
                    Log.d("InfoChange", resp.toString())
                    when (resp.code) {
                        1000 -> mypageview.onMypageSuccess(resp.mypagebody)
                        else -> {
                            mypageview.onMypageFailure(resp.code, resp.message)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MypageResponse>, t: Throwable) {
                mypageview.onMypageFailure(400, "네트워크 오류 발생")
            }
        })
    }
}