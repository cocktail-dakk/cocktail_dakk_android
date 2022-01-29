package com.example.cocktail_dakk.data.entities

class Detail_keyword : ArrayList<Detail_keywordItem>()

data class Detail_keywordItem(
    val keywordId: Int,
    val keywordName: String
)