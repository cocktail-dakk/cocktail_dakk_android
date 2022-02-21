package com.example.cocktail_dakk.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

fun getjwt(context : Context) : String{
    var spf = context.getSharedPreferences("jwt", AppCompatActivity.MODE_PRIVATE)
    val jwt = spf.getString("jwt"," ")!!
    return jwt
}

fun initSplash(context: Context){
    var spf = context.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = spf?.edit()!!
    editor.putInt("currenttab", 1)
    editor.apply()

    var spf2 = context?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
    var editor2: SharedPreferences.Editor = spf2?.edit()!!
    editor2.putString("searchstr", " ")
    editor2.apply()
}