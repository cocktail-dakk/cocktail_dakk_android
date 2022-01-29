package com.example.cocktail_dakk.data.entities

import com.example.cocktail_dakk.ui.search.searchService.Keyword

data class Cocktail(
    val localName: String = "",
    val englishName: String = "",
    val image: Int = 0,

    val mixing: String = "",
    val baseCocktail: String = "",

    val starPoint: Float = 0.0f,
    val alcoholLevel: Int = 0,

    val ingredients: String = "",
    val keywords: String = "",
    val information: String = "",
)
