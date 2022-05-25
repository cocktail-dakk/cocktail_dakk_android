package com.umcapplunching.cocktail_dakk

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.umcapplunching.cocktail_dakk.data.datastore.DataStoreSearchStr
import java.net.URI
import kotlin.properties.Delegates

class CocktailDakkApplication : Application() {
    private lateinit var dataStore : DataStoreSearchStr

    companion object {
        private lateinit var CocktailDakkApplication : CocktailDakkApplication
        fun getInstance() : CocktailDakkApplication = CocktailDakkApplication

        lateinit var ninkname : String
        // 유저 이미지 Uri 싱글톤패턴으로 관리
        lateinit var userImgUrl: Uri
    }

    override fun onCreate() {
        super.onCreate()
        CocktailDakkApplication = this
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
    }


}