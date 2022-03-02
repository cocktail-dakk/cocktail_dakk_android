package com.umcapplunching.cocktail_dakk.data.entities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.umcapplunching.cocktail_dakk.data.entities.UserInfo
import com.google.gson.Gson

fun getUser(context: Context) : UserInfo {
    val gson = Gson()
    var spf = context.getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)
    var userInfo = gson.fromJson(spf.getString("UserInfo", ""), UserInfo::class.java)

    return userInfo
}