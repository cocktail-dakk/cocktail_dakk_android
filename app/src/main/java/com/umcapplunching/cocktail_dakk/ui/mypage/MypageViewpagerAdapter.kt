package com.umcapplunching.cocktail_dakk.ui.mypage

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication
import com.umcapplunching.cocktail_dakk.data.entities.UserInfo


class MypageViewpagerAdapter(fragment: Fragment,val setUser : (UserInfo) -> Unit) : FragmentStateAdapter(fragment) {

    private var userDosu : Int = CocktailDakkApplication.userInfo.alcoholLevel
    private var userDrinks : String = CocktailDakkApplication.userInfo.userDrinks
    private var userKeywords : String = CocktailDakkApplication.userInfo.userKeywords

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MypageResettingDosuFragment(setDosu = {
                userDosu = it
                setUser(UserInfo(
                    CocktailDakkApplication.userInfo.age,
                    userDosu,
                    CocktailDakkApplication.userInfo.nickname,
                    CocktailDakkApplication.userInfo.sex,
                    userDrinks,
                    userKeywords
                ))
            })
            1 -> MypageResettingGijuFragment(setGiju = {
//                if(it.isNotEmpty()){
                    userDrinks = it
                    setUser(UserInfo(
                        CocktailDakkApplication.userInfo.age,
                        userDosu,
                        CocktailDakkApplication.userInfo.nickname,
                        CocktailDakkApplication.userInfo.sex,
                        userDrinks,
                        userKeywords
                    ))
//                }
            })
            else -> MypageResettingKeywordFragment(setKeywords = {
                userKeywords = it
                setUser(UserInfo(
                    CocktailDakkApplication.userInfo.age,
                    userDosu,
                    CocktailDakkApplication.userInfo.nickname,
                    CocktailDakkApplication.userInfo.sex,
                    userDrinks,
                    userKeywords
                ))
            })
            }
        }
    }
