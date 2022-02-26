package com.example.cocktail_dakk.data.entities

import com.example.cocktail_dakk.ui.search.searchService.BaseGiju
import com.example.cocktail_dakk.ui.search.searchService.Keyword
import com.google.gson.annotations.SerializedName

data class UserInfo(
    val age: Int,
    val alcoholLevel: Int,
    val nickname: String,
    val sex: String,
    val userDrinks: String,
    val userKeywords: String,
)
