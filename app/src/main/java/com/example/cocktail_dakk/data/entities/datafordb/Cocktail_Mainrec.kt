package com.example.cocktail_dakk.data.entities.datafordb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MainrecTable")
data class Cocktail_Mainrec(
    var koreanname: String = "",
    var cocktailinfoid : Int = 0,
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}

data class Cocktail_Mainrec_support(
    var koreanname : List<String>,
)

