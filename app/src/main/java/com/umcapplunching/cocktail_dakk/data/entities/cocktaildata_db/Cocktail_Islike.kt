package com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "IsLikeTable")
data class Cocktail_Islike(
    var isLikeId : Int = 0,
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
