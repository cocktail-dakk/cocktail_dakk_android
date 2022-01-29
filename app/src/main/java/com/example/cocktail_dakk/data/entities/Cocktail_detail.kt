package com.example.cocktail_dakk.data.entities

import com.example.cocktail_dakk.ui.search.searchService.Keyword

data class Cocktail_detail (
    val localName: String = "",
    val englishName: String = "",
    val imageURL: String = "",
    val starPoint: Double = 0.0,
    val alcoholLevel: Int = 0,
    val mixxing: String = "",
    val keywords: List<Keyword>,
    val information: String = "",
    val ingredients: String = "",
)