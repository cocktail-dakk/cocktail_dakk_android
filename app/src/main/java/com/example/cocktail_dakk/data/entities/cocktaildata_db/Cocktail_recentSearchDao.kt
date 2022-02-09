package com.example.cocktail_dakk.data.entities.cocktaildata_db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface Cocktail_recentSearchDao {
    @Insert(onConflict = REPLACE)
    fun insert(searchstr: Cocktail_recentSearch)

    @Update
    fun update(searchstr: Cocktail_recentSearch)

    @Delete
    fun delete(searchstr: Cocktail_recentSearch)

    @Query("DELETE FROM RecentSearchTable WHERE searchstr = :searchstr")
    fun duplicatecheck(searchstr: String)

    @Query("SELECT * FROM RecentSearchTable") // 테이블의 모든 값을 가져와라
    fun getcocktail(): List<Cocktail_recentSearch>

    @Query("DELETE FROM RecentSearchTable")
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