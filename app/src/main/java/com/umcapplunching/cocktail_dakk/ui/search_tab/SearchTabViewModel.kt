package com.umcapplunching.cocktail_dakk.ui.search_tab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.CocktailDBRepository
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_Mainrec
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_recentSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchTabViewModel(application: Application) : AndroidViewModel(application) {

    private val searchTabRepo = CocktailDBRepository(application)
    private val recentSearchList = searchTabRepo.getrecentSearchAll()
    private val mainRecList = searchTabRepo.getmainRecAll()

    fun getmainRecAll(): LiveData<List<Cocktail_Mainrec>> {
        return this.mainRecList
    }

    fun getrecentSearchAll(): LiveData<List<Cocktail_recentSearch>> {
        return recentSearchList
    }

    fun recentSearchDuplicateCheck(recentSearchStr: Cocktail_recentSearch) {
        viewModelScope.launch(Dispatchers.IO) {
            searchTabRepo.recentSearchDuplicateCheck(recentSearchStr)
        }
    }

    fun recentSearchinsert(recentSearchStr: Cocktail_recentSearch) {
        viewModelScope.launch(Dispatchers.IO) {
            searchTabRepo.recentSearchInsert(recentSearchStr)
        }
    }

    fun recentSearchdelete(recentSearchStr: Cocktail_recentSearch) {
        searchTabRepo.recentSearchDelete(recentSearchStr)
    }

    fun deletAllrecentCocktail(){
        viewModelScope.launch {
            searchTabRepo.deletAllrecentCocktail()
        }
    }


}