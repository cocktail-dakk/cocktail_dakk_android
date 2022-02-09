package com.example.cocktail_dakk.data.entities.cocktaildata_db

import androidx.room.*

@Dao
interface Cocktail_MainrecDao {
    @Insert
    fun insert(cocktailname: Cocktail_Mainrec)

    @Update
    fun update(cocktailname: Cocktail_Mainrec)

    @Delete
    fun delete(cocktailname: Cocktail_Mainrec)

    @Query("SELECT * FROM MainrecTable") // 테이블의 모든 값을 가져와라
    fun getcocktail(): List<Cocktail_Mainrec>

    @Query("DELETE FROM MainrecTable")
    fun deleteAllCocktail()

//    @Query("SELECT * FROM AlbumTable WHERE albumIdx = :albumIdx")
//    fun getAlbum(albumIdx: Int): Album
//
//    @Insert
//    fun likeAlbum(like : Like)
//
//    @Query("SELECT id FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
//    fun isLikeAlbum(userId : Int, albumId : Int):Int ?
//
//    @Query("DELETE FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
//    fun disLikeAlbum(userId : Int, albumId : Int)
//
//    @Query("SELECT AT.* FROM LikeTable as LT LEFT JOIN AlbumTable as AT ON LT.albumId = AT.albumIdx WHERE LT.userId = :userId")
//    fun getLikedAlbums(userId : Int):List<Album>

}