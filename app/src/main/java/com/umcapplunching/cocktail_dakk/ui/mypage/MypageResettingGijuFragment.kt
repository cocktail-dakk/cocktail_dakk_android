package com.umcapplunching.cocktail_dakk.ui.mypage

import android.util.Log
import android.widget.CompoundButton
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.FragmentMypageResettingGijuBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity

class MypageResettingGijuFragment:BaseFragment<FragmentMypageResettingGijuBinding>(FragmentMypageResettingGijuBinding::inflate) {

    private var gijukeyword = ArrayList<String>()
    private var gijustr = ""

    override fun initAfterBinding() {
        SetGijuListener()
        (activity as MainActivity).setMypageTempGijulist(gijukeyword)
    }

    override fun onStart() {
        gijukeyword.addAll((activity as MainActivity).getMypageGijulist())
        initSelected(gijukeyword)
        super.onStart()
        Log.d("lifeMy_2", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifeMy_2", "onResume")
    }

    override fun onPause() {
        Log.d("lifeMy_2", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("lifeMy_2", "onStop")
        super.onStop()
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
            Log.d("testYYYY", gijukeyword.toString())
            (activity as MainActivity).setMypageTempGijulist(gijukeyword)

        }
        binding.mypageResettingGijuVodcaCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuRumCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuTequilaCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuWiskiTv.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuBrandyCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuLiqueurCb.setOnCheckedChangeListener(gijuListner)
        binding.mypageResettingGijuJinTv.setOnCheckedChangeListener(gijuListner)

    }

    private fun initSelected(gijukeyword:
                             ArrayList<String>){
        val gijuTemp = arrayListOf(
            binding.mypageResettingGijuVodcaCb,
            binding.mypageResettingGijuRumCb,
            binding.mypageResettingGijuTequilaCb,
            binding.mypageResettingGijuWiskiTv,
            binding.mypageResettingGijuBrandyCb,
            binding.mypageResettingGijuLiqueurCb,
            binding.mypageResettingGijuJinTv)
        for (giju in gijuTemp){
            giju.isChecked = giju.text in gijukeyword
        }
    }

}