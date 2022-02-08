package com.example.cocktail_dakk.data.entities.cocktaildata_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MainrecTable")
data class Cocktail_Mainrec(
    var koreanname: String = "",
    var cocktailinfoid : Int = 0,
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}



