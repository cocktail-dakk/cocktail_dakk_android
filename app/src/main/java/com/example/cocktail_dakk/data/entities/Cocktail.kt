package com.example.cocktail_dakk.data.entities

data class Cocktail(
    val localName: String = "",
    val englishName: String = "",
    val image: Int = 0,

    val mixing: String = "",
    val baseCocktail: String = "",

    val starPoint: Float = 0.0f,
    val alcoholLevel: String = "",

    val ingredients: String = "",
    val keywords: String = "",
    val information: String = "",
)
