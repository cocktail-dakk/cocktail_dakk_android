package com.umcapplunching.cocktail_dakk.ui.mypage.mypageService

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH

interface MypagerRetrofitInterface {


//    fun mypagemodify(@Field("deviceNum",encoded = true) deviceNum: String,
//                     @Field("nickname",encoded = true) nickname: String,
//                     @Field("alcoholLevel",encoded = true) alcoholLevel: Int,
//                     @Field("favouritesKeywords",encoded = true) favouritesKeywords: String,
//                     @Field("favouritesDrinks",encoded = true) favouritesDrinks: String) : Call<MypageResponse>

    @PATCH("/users/modify")
    fun mypagemodify(@Body mypageRequest: MypageRequest) : Call<MypageResponse>


}
