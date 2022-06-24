package com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService

// 별점 보낼 때 서버로 보내는 형식 
data class DetailRequest(
    val cocktailInfoId: Int,
    val starPoint: Int
)