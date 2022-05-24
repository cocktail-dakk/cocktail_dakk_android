package com.umcapplunching.cocktail_dakk.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.CocktailDBRepository
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_Mainrec
import com.umcapplunching.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecService
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dbRepo = CocktailDBRepository(application)
    val mainrecService = MainrecService()

    fun mainRecInsert(mainrec : Cocktail_Mainrec){
        viewModelScope.launch {
            dbRepo.mainRecInsert(mainrec)
        }
    }

    fun deletemainRecAll(){
        viewModelScope.launch {
            dbRepo.deletemainRecAll()
        }
    }

}