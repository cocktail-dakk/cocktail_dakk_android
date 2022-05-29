package com.umcapplunching.cocktail_dakk.data.entities

// 앱 내에서 사용되는 유저정보
data class UserInfo_forApp(
    val age: Int,
    var alcoholLevel: Int,
    val nickname: String,
    val sex: String,
    val userDrinks: String,
    val userKeywords: String,
)
