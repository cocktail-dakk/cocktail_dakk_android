package com.example.cocktail_dakk.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

var accesstoken : String = " "
var refreshtoken : String = " "

fun getaccesstoken(context : Context) : String{
    var spf = context.getSharedPreferences("accesstoken", AppCompatActivity.MODE_PRIVATE)
    val at = spf.getString("accesstoken"," ")!!
    return at
}

fun getrefreshtoken(context : Context) : String{
    var spf = context.getSharedPreferences("refreshtoken", AppCompatActivity.MODE_PRIVATE)
    val rt = spf.getString("refreshtoken"," ")!!
    return rt
}

fun setaccesstoken(context: Context, token : String){
    var spf = context.getSharedPreferences("accesstoken", AppCompatActivity.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = spf?.edit()!!
    editor.putString("accesstoken",token)
    editor.apply()
}

fun setrefreshtoken(context: Context, token : String){
    var spf = context.getSharedPreferences("refreshtoken", AppCompatActivity.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = spf?.edit()!!
    editor.putString("refreshtoken",token)
    editor.apply()
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