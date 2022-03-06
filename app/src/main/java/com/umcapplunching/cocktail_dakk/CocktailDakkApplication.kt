package com.umcapplunching.cocktail_dakk

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO

class CocktailDakkApplication : Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: CocktailDakkApplication
        fun ApplicationContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
    }

}