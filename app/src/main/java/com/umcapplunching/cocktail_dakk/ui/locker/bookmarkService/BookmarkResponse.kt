package com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService

import com.umcapplunching.cocktail_dakk.ui.search.searchService.Keyword
import com.google.gson.annotations.SerializedName

//data class BookmarkResponse(
//    @SerializedName("code")val code: Int,
//    @SerializedName("isSuccess")val isSuccess: Boolean,
//    @SerializedName("message")val message: String,
//    @SerializedName("result")val result: List<BookmarkBody>
//)

data class BookmarkBody(
    @SerializedName("cocktailInfoId") val cocktailInfoId: Int,
    @SerializedName("cocktailKeyword") val cocktailKeyword: List<Keyword>,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("koreanName") val koreanName: String,
    @SerializedName("nukkiImgUrl") val nukkiImgUrl: String,
    @SerializedName("smallNukkiImgUrl") val smallNukkiImgUrl: String
)

