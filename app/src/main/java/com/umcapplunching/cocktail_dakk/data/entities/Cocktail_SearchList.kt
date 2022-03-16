package com.umcapplunching.cocktail_dakk.data.entities

import com.umcapplunching.cocktail_dakk.ui.search.searchService.Keyword

data class Cocktail_SearchList(
    val localName: String = "",
    val englishName: String = "",
    val keywords: List<Keyword>,
    val imageURL: String = "",
    val starPoint: Double = 0.0,
    val alcoholLevel: Int = 0,
    val baseCocktail: String = "",
    val id : Int = 0,
)
