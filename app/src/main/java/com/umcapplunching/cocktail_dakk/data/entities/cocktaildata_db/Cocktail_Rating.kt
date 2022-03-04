package com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "RatingTable")
data class Cocktail_Rating(
    var cocktailinfoid : Int = 0,
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
