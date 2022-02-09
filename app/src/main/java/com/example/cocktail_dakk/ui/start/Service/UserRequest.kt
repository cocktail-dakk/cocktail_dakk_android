package com.example.cocktail_dakk.ui.start.Service

data class UserRequest(
    var id: Int =0,
    var deviceNum: String = "string",
    var nickname: String ="testnickname",
    var age: Int = 20,
    var sex: String = "M",
    var alcoholLevel: Int = 0,
    var favouritesKeywords: String = "",
    var favouritesDrinks: String = "",
)