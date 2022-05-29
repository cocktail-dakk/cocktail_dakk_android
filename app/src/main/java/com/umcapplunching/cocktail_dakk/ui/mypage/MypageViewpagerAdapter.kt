package com.umcapplunching.cocktail_dakk.ui.mypage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication
import com.umcapplunching.cocktail_dakk.data.entities.UserInfo_forApp


class MypageViewpagerAdapter(fragment: Fragment,val setUser : (UserInfo_forApp) -> Unit) : FragmentStateAdapter(fragment) {

    private var userDosu : Int = CocktailDakkApplication.userInfoForApp.alcoholLevel
    private var userDrinks : String = CocktailDakkApplication.userInfoForApp.userDrinks
    private var userKeywords : String = CocktailDakkApplication.userInfoForApp.userKeywords

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MypageResettingDosuFragment(setDosu = {
                userDosu = it
                setUser(UserInfo_forApp(
                    CocktailDakkApplication.userInfoForApp.age,
                    userDosu,
                    CocktailDakkApplication.userInfoForApp.nickname,
                    CocktailDakkApplication.userInfoForApp.sex,
                    userDrinks,
                    userKeywords
                ))
            })
            1 -> MypageResettingGijuFragment(setGiju = {
//                if(it.isNotEmpty()){
                    userDrinks = it
                    setUser(UserInfo_forApp(
                        CocktailDakkApplication.userInfoForApp.age,
                        userDosu,
                        CocktailDakkApplication.userInfoForApp.nickname,
                        CocktailDakkApplication.userInfoForApp.sex,
                        userDrinks,
                        userKeywords
                    ))
//                }
            })
            else -> MypageResettingKeywordFragment(setKeywords = {
                userKeywords = it
                setUser(UserInfo_forApp(
                    CocktailDakkApplication.userInfoForApp.age,
                    userDosu,
                    CocktailDakkApplication.userInfoForApp.nickname,
                    CocktailDakkApplication.userInfoForApp.sex,
                    userDrinks,
                    userKeywords
                ))
            })
            }
        }
    }
