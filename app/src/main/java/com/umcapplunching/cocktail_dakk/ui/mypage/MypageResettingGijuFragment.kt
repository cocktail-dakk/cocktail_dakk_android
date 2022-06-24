package com.umcapplunching.cocktail_dakk.ui.mypage

import android.widget.CheckBox
import android.widget.CompoundButton
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.FragmentMypageResettingGijuBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment

class MypageResettingGijuFragment(val setGiju : (String)->Unit):BaseFragment<FragmentMypageResettingGijuBinding>(FragmentMypageResettingGijuBinding::inflate) {
    private val gijukeyword = ArrayList<String>()
    private lateinit var gijuTemp : ArrayList<CheckBox>

    override fun initAfterBinding() {
        gijuTemp = arrayListOf(
            binding.mypageResettingGijuVodcaCb,
            binding.mypageResettingGijuRumCb,
            binding.mypageResettingGijuTequilaCb,
            binding.mypageResettingGijuWiskiTv,
            binding.mypageResettingGijuBrandyCb,
            binding.mypageResettingGijuLiqueurCb,
            binding.mypageResettingGijuJinTv)
        SetGijuListener()
        initSelected()
    }


    private fun SetGijuListener() {
        val gijuListner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.mypage_resetting_giju_vodca_cb -> {
                        gijukeyword.add("보드카")
                    }
                    R.id.mypage_resetting_giju_rum_cb -> {
                        gijukeyword.add("럼")
                    }
                    R.id.mypage_resetting_giju_tequila_cb -> {
                        gijukeyword.add("데킬라")
                    }
                    R.id.mypage_resetting_giju_wiski_tv -> {
                        gijukeyword.add("위스키")
                    }
                    R.id.mypage_resetting_giju_brandy_cb -> {
                        gijukeyword.add("브랜디")
                    }
                    R.id.mypage_resetting_giju_liqueur_cb -> {
                        gijukeyword.add("리큐르")
                    }
                    R.id.mypage_resetting_giju_jin_tv -> {
                        gijukeyword.add("진")
                    }
                }
            } else {
                when (buttonView.id) {
                    R.id.mypage_resetting_giju_vodca_cb -> {
                        gijukeyword.remove("보드카")
                    }
                    R.id.mypage_resetting_giju_rum_cb -> {
                        gijukeyword.remove("럼")
                    }
                    R.id.mypage_resetting_giju_tequila_cb -> {
                        gijukeyword.remove("데킬라")
                    }
                    R.id.mypage_resetting_giju_wiski_tv -> {
                        gijukeyword.remove("위스키")
                    }
                    R.id.mypage_resetting_giju_brandy_cb -> {
                        gijukeyword.remove("브랜디")
                    }
                    R.id.mypage_resetting_giju_liqueur_cb -> {
                        gijukeyword.remove("리큐르")
                    }
                    R.id.mypage_resetting_giju_jin_tv -> {
                        gijukeyword.remove("진")
                    }
                }
            }
            var tempDrink = ""
            for(i in gijukeyword){
                tempDrink += "${i},"
            }
            setGiju(tempDrink)
        }
        binding.mypageResettingGijuVodcaCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuRumCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuTequilaCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuWiskiTv.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuBrandyCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuLiqueurCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuJinTv.setOnCheckedChangeListener(gijuListner)
    }

    private fun initSelected(){
        val drinkList = CocktailDakkApplication.userInfoForApp.userDrinks.split(",") as ArrayList<String>
        for (giju in gijuTemp){
            giju.isChecked = giju.text in drinkList
        }
    }

}