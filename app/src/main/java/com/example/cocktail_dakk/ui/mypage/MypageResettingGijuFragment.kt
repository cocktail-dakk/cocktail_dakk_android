package com.example.cocktail_dakk.ui.mypage

import android.util.Log
import android.view.View
import android.widget.CompoundButton
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.FragmentMypageResettingGijuBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.start.setting.StartSettingActivity

class MypageResettingGijuFragment:BaseFragment<FragmentMypageResettingGijuBinding>(FragmentMypageResettingGijuBinding::inflate) {

    private var gijukeyword = ArrayList<String>()
    private var gijustr = ""

    override fun initAfterBinding() {
        SetGijuListener()
    }
//    // 크기 다시 조절해주기
//    override fun onResume() {
//        super.onResume()
//        binding.root.requestLayout()
//    }


    private fun SetGijuListener() {
        var gijuListner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
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
            Log.d("test", gijukeyword.toString())

        }
        binding.mypageResettingGijuVodcaCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuRumCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuTequilaCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuWiskiTv.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuBrandyCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuLiqueurCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuJinTv.setOnCheckedChangeListener(gijuListner)

    }


}