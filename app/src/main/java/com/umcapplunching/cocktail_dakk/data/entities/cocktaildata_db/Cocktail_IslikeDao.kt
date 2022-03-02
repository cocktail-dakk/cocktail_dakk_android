package com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface Cocktail_IslikeDao {
    @Insert(onConflict = REPLACE)
    fun insert(cocktailid: Cocktail_Islike)

    @Update
    fun update(cocktailid: Cocktail_Islike)

    @Delete
    fun delete(cocktailid: Cocktail_Islike)

    @Query("DELETE FROM IsLikeTable WHERE IsLikeId = :cockid")
    fun unlike(cockid: Int)

    @Query("SELECT * FROM IsLikeTable") // 테이블의 모든 값을 가져와라
    fun getcocktail(): List<Cocktail_Islike>

    @Query("DELETE FROM IsLikeTable")
    fun deleteAllCocktail()

}