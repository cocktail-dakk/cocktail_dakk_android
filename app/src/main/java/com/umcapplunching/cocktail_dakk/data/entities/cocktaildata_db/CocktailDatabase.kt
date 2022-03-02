package com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cocktail_Mainrec::class,Cocktail_recentSearch::class,Cocktail_Rating::class,Cocktail_Islike::class], version = 1)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun MainrecDao() : Cocktail_MainrecDao
    abstract fun RecentSearchDao() : Cocktail_recentSearchDao
    abstract fun RatingDao() : Cocktail_RatingDao
    abstract fun IslikeDao() : Cocktail_IslikeDao

    companion object {
        private var instance: CocktailDatabase? = null
        @Synchronized
        fun getInstance(context: Context): CocktailDatabase? {
            if (instance == null) {
                synchronized(CocktailDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,CocktailDatabase::class.java,"cocktail-database"//다른 데이터 베이스랑 이름겹치면 꼬임
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                }//Please provide the necessary Migration path via RoomDatabase ->fallbackToDestructiveMigration
            }
            return instance
        }
    }
}