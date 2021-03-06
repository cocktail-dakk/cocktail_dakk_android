package com.umcapplunching.cocktail_dakk.ui.mypage.mypageService

import android.util.Log
import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService.*
import com.umcapplunching.cocktail_dakk.utils.getReposit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field

class MypageService {

    private lateinit var mypageview: MypageView

    fun setmypageView(mypageview: MypageView) {
        this.mypageview = mypageview
    }

    fun mypagemodify(mypagerequest: MypageRequest) {
        val mypagerService = getReposit().create(MypagerRetrofitInterface::class.java)
        mypagerService.mypagemodify(mypagerequest).enqueue(object : Callback<ResponseWrapper<MypageBody>> {
            override fun onResponse(
                call: Call<ResponseWrapper<MypageBody>>,
                response: Response<ResponseWrapper<MypageBody>>
            ) {
                if (response.code() == 401){
                    mypageview.onMypageFailure(5000,"토큰 만료")
                }
                    else {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> mypageview.onMypageSuccess(resp.responseBody)
                        else -> {
                            mypageview.onMypageFailure(resp.code, resp.message)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseWrapper<MypageBody>>, t: Throwable) {
                mypageview.onMypageFailure(400, "네트워크 오류 발생")
            }
        })
    }
}