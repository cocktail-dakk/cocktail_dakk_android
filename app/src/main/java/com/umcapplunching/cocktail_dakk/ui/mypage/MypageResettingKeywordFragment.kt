package com.umcapplunching.cocktail_dakk.ui.mypage

import android.widget.CheckBox
import android.widget.CompoundButton
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.FragmentMypageResettingKeywordBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment

class MypageResettingKeywordFragment(val setKeywords : (String)->Unit):BaseFragment<FragmentMypageResettingKeywordBinding>(FragmentMypageResettingKeywordBinding::inflate) {

    private var favorkeyword = ArrayList<String>()
    private lateinit var favorTemp : ArrayList<CheckBox>

    override fun initAfterBinding() {
        favorTemp = arrayListOf(
            binding.mypageResettingKeywordLadykillerBt,
            binding.mypageResettingKeywordShooterBt,
            binding.mypageResettingKeywordCleanBt,
            binding.mypageResettingKeywordTansanBt,
            binding.mypageResettingKeywordLayeredBt,
            binding.mypageResettingKeywordMartiniBt,
            binding.mypageResettingKeywordPrettyBt,
            binding.mypageResettingKeywordHighballBt,
            binding.mypageResettingKeywordSweetBt,
            binding.mypageResettingKeywordDockhanBt,
            binding.mypageResettingKeywordSangqueBt,
            binding.mypageResettingKeywordFluitfavorBt,
            binding.mypageResettingKeywordSsupssupBt,
            binding.mypageResettingKeywordOntherrockBt,
            binding.mypageResettingKeywordSangkumBt,
            binding.mypageResettingKeywordDansunBt,
            binding.mypageResettingKeywordMilkBt,
            binding.mypageResettingKeywordBockjapBt)
        SetkeywordListener()
        initSelected()
    }


    private fun SetkeywordListener() {
        val favorListner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.mypage_resetting_keyword_ladykiller_bt -> {
                        favorkeyword.add("???????????????")
                        binding.mypageResettingKeywordLadykillerBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_shooter_bt -> {
                        favorkeyword.add("??????")
                        binding.mypageResettingKeywordShooterBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_clean_bt -> {
                        favorkeyword.add("?????????")
                        binding.mypageResettingKeywordCleanBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_tansan_bt -> {
                        favorkeyword.add("??????")
                        binding.mypageResettingKeywordTansanBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_layered_bt -> {
                        favorkeyword.add("????????????")
                        binding.mypageResettingKeywordLayeredBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_martini_bt -> {
                        favorkeyword.add("?????????")
                        binding.mypageResettingKeywordMartiniBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_pretty_bt -> {
                        favorkeyword.add("??????")
                        binding.mypageResettingKeywordPrettyBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_highball_bt -> {
                        favorkeyword.add("?????????")
                        binding.mypageResettingKeywordHighballBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_sweet_bt -> {
                        favorkeyword.add("?????????")
                        binding.mypageResettingKeywordSweetBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_dockhan_bt -> {
                        favorkeyword.add("??????")
                        binding.mypageResettingKeywordDockhanBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_sangque_bt -> {
                        favorkeyword.add("?????????")
                        binding.mypageResettingKeywordSangqueBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_fluitfavor_bt -> {
                        favorkeyword.add("?????????")
                        binding.mypageResettingKeywordFluitfavorBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_ssupssup_bt -> {
                        favorkeyword.add("?????????")
                        binding.mypageResettingKeywordSsupssupBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_ontherrock_bt -> {
                        favorkeyword.add("?????????")
                        binding.mypageResettingKeywordOntherrockBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_sangkum_bt -> {
                        favorkeyword.add("?????????")
                        binding.mypageResettingKeywordSangkumBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_dansun_bt -> {
                        favorkeyword.add("?????????")
                        binding.mypageResettingKeywordDansunBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_milk_bt -> {
                        favorkeyword.add("??????")
                        binding.mypageResettingKeywordMilkBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.mypage_resetting_keyword_bockjap_bt -> {
                        favorkeyword.add("?????????")
                        binding.mypageResettingKeywordBockjapBt.setTextColor(resources.getColor(R.color.black))
                    }
                }
            } else {
                when (buttonView.id) {
                    R.id.mypage_resetting_keyword_ladykiller_bt -> {
                        favorkeyword.remove("???????????????")
                    }
                    R.id.mypage_resetting_keyword_shooter_bt -> {
                        favorkeyword.remove("??????")
                    }
                    R.id.mypage_resetting_keyword_clean_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.mypage_resetting_keyword_tansan_bt -> {
                        favorkeyword.remove("??????")
                    }
                    R.id.mypage_resetting_keyword_layered_bt -> {
                        favorkeyword.remove("????????????")
                    }
                    R.id.mypage_resetting_keyword_martini_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.mypage_resetting_keyword_pretty_bt -> {
                        favorkeyword.remove("??????")
                    }
                    R.id.mypage_resetting_keyword_highball_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.mypage_resetting_keyword_sweet_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.mypage_resetting_keyword_dockhan_bt -> {
                        favorkeyword.remove("??????")
                    }
                    R.id.mypage_resetting_keyword_sangque_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.mypage_resetting_keyword_fluitfavor_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.mypage_resetting_keyword_ssupssup_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.mypage_resetting_keyword_ontherrock_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.mypage_resetting_keyword_sangkum_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.mypage_resetting_keyword_dansun_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.mypage_resetting_keyword_milk_bt -> {
                        favorkeyword.remove("??????")
                    }
                    R.id.mypage_resetting_keyword_bockjap_bt -> {
                        favorkeyword.remove("?????????")
                    }
                }

            }
            var tempKeywords = ""
            for(i in favorkeyword){
                tempKeywords += "${i},"
            }
            setKeywords(tempKeywords)
        }
        binding.mypageResettingKeywordLadykillerBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordShooterBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordCleanBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordTansanBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordLayeredBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordMartiniBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordPrettyBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordHighballBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordSweetBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordDockhanBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordSangqueBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordFluitfavorBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordSsupssupBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordOntherrockBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordSangkumBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordDansunBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordMilkBt.setOnCheckedChangeListener(favorListner)
        binding.mypageResettingKeywordBockjapBt.setOnCheckedChangeListener(favorListner)

    }

    private fun initSelected(){
        val favorList = CocktailDakkApplication.userInfoForApp.userKeywords.split(",") as ArrayList<String>
        for (favor in favorTemp){
            favor.isChecked = favor.text in favorList
        }
    }

}