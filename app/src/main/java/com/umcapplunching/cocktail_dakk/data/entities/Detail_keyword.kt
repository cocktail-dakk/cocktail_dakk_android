package com.umcapplunching.cocktail_dakk.data.entities

class Detail_keyword : ArrayList<com.umcapplunching.cocktail_dakk.data.entities.Detail_keywordItem>()

data class Detail_keywordItem(
    val keywordId: Int,
    val keywordName: String
)