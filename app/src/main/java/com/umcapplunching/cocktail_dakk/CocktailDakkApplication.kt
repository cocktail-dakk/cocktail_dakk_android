package com.umcapplunching.cocktail_dakk

import android.app.Application
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.umcapplunching.cocktail_dakk.data.entities.UserInfo_forApp

class CocktailDakkApplication : Application() {

    companion object {
        private lateinit var CocktailDakkApplication : CocktailDakkApplication
        fun getInstance() : CocktailDakkApplication = CocktailDakkApplication

        // 유저 정보 싱글톤패턴으로 관리
        lateinit var userImgUrl: Uri
        lateinit var userEmail: String
        lateinit var AccessToken : String
        lateinit var RefreshToken : String

//        private val _userInfo = MutableLiveData<UserInfo>()
//        private val userInfo : LiveData<UserInfo>
//            get() = _userInfo

        lateinit var userInfoForApp : UserInfo_forApp

    }

    override fun onCreate() {
        super.onCreate()
        CocktailDakkApplication = this
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)

    }

}