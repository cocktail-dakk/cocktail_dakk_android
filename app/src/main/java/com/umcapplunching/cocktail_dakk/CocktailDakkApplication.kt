package com.umcapplunching.cocktail_dakk

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.umcapplunching.cocktail_dakk.data.datastore.DataStoreSearchStr

class CocktailDakkApplication : Application() {
    private lateinit var dataStore : DataStoreSearchStr

    companion object {
        private lateinit var CocktailDakkApplication : CocktailDakkApplication
        fun getInstance() : CocktailDakkApplication = CocktailDakkApplication
    }

    override fun onCreate() {
        super.onCreate()
        CocktailDakkApplication = this
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        dataStore = DataStoreSearchStr(this)
    }
    fun getDataStore() : DataStoreSearchStr = dataStore
}