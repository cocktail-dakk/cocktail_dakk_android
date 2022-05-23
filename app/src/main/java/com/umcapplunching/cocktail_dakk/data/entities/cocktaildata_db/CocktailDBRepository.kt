package com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CocktailDBRepository(application: Application) : ViewModel() {

    private val cocktailDB = CocktailDatabase.getInstance(application)!!
    private val mainRecDAO: Cocktail_MainrecDao = cocktailDB.MainrecDao()
    private val mainRecList: LiveData<List<Cocktail_Mainrec>> =
        mainRecDAO.getAllMainRec()

    private val recentSearchDAO: Cocktail_recentSearchDao = cocktailDB.RecentSearchDao()
    private val recentSearchList: LiveData<List<Cocktail_recentSearch>> =
        recentSearchDAO.getrecentSearchAll()

    fun getmainRecAll(): LiveData<List<Cocktail_Mainrec>> {
        return this.mainRecList
    }

    fun getrecentSearchAll(): LiveData<List<Cocktail_recentSearch>> {
        return this.recentSearchList
    }

    fun deletAllrecentCocktail(){
        viewModelScope.launch {
            recentSearchDAO.deleteAllCocktail()
        }
    }

    fun recentSearchInsert(recentSearchStr: Cocktail_recentSearch) {
        viewModelScope.launch {
            try {
                recentSearchDAO.insert(recentSearchStr)
            } catch (e: Exception) {
                Log.d("SearchTabRepository",e.toString())
            }
        }
    }

    fun recentSearchDuplicateCheck(recentSearchStr: Cocktail_recentSearch) {
        viewModelScope.launch {
            try {
                recentSearchDAO.duplicatecheck(recentSearchStr.searchstr)
            } catch (e: Exception) {
                Log.d("SearchTabRepository",e.toString())
            }
        }
    }

    fun recentSearchDelete(recentSearchStr: Cocktail_recentSearch) {
        try {
            recentSearchDAO.delete(recentSearchStr)
        } catch (e: Exception) {
            Log.d("SearchTabRepository",e.toString())
        }
    }


}