package com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db

import androidx.room.*


@Dao
interface Cocktail_RatingDao {
    @Insert
    fun insert(cocktailname: Cocktail_Rating)

    @Update
    fun update(cocktailname: Cocktail_Rating)

    @Delete
    fun delete(cocktailname: Cocktail_Rating)

    @Query("SELECT * FROM RatingTable") // 테이블의 모든 값을 가져와라
    fun getcocktails(): List<Cocktail_Rating>

    @Query("DELETE FROM RatingTable")
    fun deleteAllCocktail()

}