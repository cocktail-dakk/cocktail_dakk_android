package com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RecentSearchTable")
data class Cocktail_recentSearch (
    var searchstr: String = "",
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
