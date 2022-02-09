package com.example.cocktail_dakk.ui.start.setting.fragment

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.FragmentStartFKeywordBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.MainActivity
import com.example.cocktail_dakk.ui.start.setting.StartSettingActivity


class StartFKeywordFragment : BaseFragment<FragmentStartFKeywordBinding>(FragmentStartFKeywordBinding::inflate) {

    private var favorkeyword = ArrayList<String>()
    private var keywordstr = ""

    override fun initAfterBinding() {

        SetFavorKeyword()

        binding.settingKeywordBtnTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                keywordstr = ""
                for(i in favorkeyword){
                    keywordstr += i + ","
                }
                if(keywordstr.length ==0){
                    Toast.makeText(requireContext(),"적어도 하나의 취향을 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
                else{
                    (activity as StartSettingActivity).setKeyword(keywordstr)
                    (activity as StartSettingActivity).signupfinish()
                }
//                startActivity(Intent(activity, MainActivity::class.java))
            }
        })


        binding.startKeywordBackTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as StartSettingActivity).Undopage()
            }
        })

    }
    private fun SetFavorKeyword() {
        var favorListner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.start_filter_keyword_ladykiller_bt -> {
                        favorkeyword.add("레이디킬러")
                        binding.startFilterKeywordLadykillerBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_shooter_bt -> {
                        favorkeyword.add("슈터")
                        binding.startFilterKeywordShooterBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_clean_bt -> {
                        favorkeyword.add("깔끔한")
                        binding.startFilterKeywordCleanBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_tansan_bt -> {
                        favorkeyword.add("탄산")
                        binding.startFilterKeywordTansanBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_layered_bt -> {
                        favorkeyword.add("레이어드")
                        binding.startFilterKeywordLayeredBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_martini_bt -> {
                        favorkeyword.add("마티니")
                        binding.startFilterKeywordMartiniBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_pretty_bt -> {
                        favorkeyword.add("예쁜")
                        binding.startFilterKeywordPrettyBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_highball_bt -> {
                        favorkeyword.add("하이볼")
                        binding.startFilterKeywordHighballBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_sweet_bt -> {
                        favorkeyword.add("달달한")
                        binding.startFilterKeywordSweetBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_dockhan_bt -> {
                        favorkeyword.add("독한")
                        binding.startFilterKeywordDockhanBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_sangque_bt -> {
                        favorkeyword.add("상퀘한")
                        binding.startFilterKeywordSangqueBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_fluitfavor_bt -> {
                        favorkeyword.add("과일향")
                        binding.startFilterKeywordFluitfavorBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_ssupssup_bt -> {
                        favorkeyword.add("씁쓸한")
                        binding.startFilterKeywordSsupssupBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_ontherrock_bt -> {
                        favorkeyword.add("온더락")
                        binding.startFilterKeywordOntherrockBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_sangkum_bt -> {
                        favorkeyword.add("상큼한")
                        binding.startFilterKeywordSangkumBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_dansun_bt -> {
                        favorkeyword.add("단순한")
                        binding.startFilterKeywordDansunBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_milk_bt -> {
                        favorkeyword.add("우유")
                        binding.startFilterKeywordMilkBt.setTextColor(resources.getColor(R.color.black))
                    }
                    R.id.start_filter_keyword_bockjap_bt -> {
                        favorkeyword.add("복잡한")
                        binding.startFilterKeywordBockjapBt.setTextColor(resources.getColor(R.color.black))
                    }
                }
            } else {
                when (buttonView.id) {
                    R.id.start_filter_keyword_ladykiller_bt -> {
                        favorkeyword.remove("레이디킬러")
                        binding.startFilterKeywordLadykillerBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_shooter_bt -> {
                        favorkeyword.remove("슈터")
                        binding.startFilterKeywordShooterBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_clean_bt -> {
                        favorkeyword.remove("깔끔한")
                        binding.startFilterKeywordCleanBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_tansan_bt -> {
                        favorkeyword.remove("탄산")
                        binding.startFilterKeywordTansanBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_layered_bt -> {
                        favorkeyword.remove("레이어드")
                        binding.startFilterKeywordLayeredBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_martini_bt -> {
                        favorkeyword.remove("마티니")
                        binding.startFilterKeywordMartiniBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_pretty_bt -> {
                        favorkeyword.remove("예쁜")
                        binding.startFilterKeywordPrettyBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_highball_bt -> {
                        favorkeyword.remove("하이볼")
                        binding.startFilterKeywordHighballBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_sweet_bt -> {
                        favorkeyword.remove("달달한")
                        binding.startFilterKeywordSweetBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_dockhan_bt -> {
                        favorkeyword.remove("독한")
                        binding.startFilterKeywordDockhanBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_sangque_bt -> {
                        favorkeyword.remove("상퀘한")
                        binding.startFilterKeywordSangqueBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_fluitfavor_bt -> {
                        favorkeyword.remove("과일향")
                        binding.startFilterKeywordFluitfavorBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_ssupssup_bt -> {
                        favorkeyword.remove("씁쓸한")
                        binding.startFilterKeywordSsupssupBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_ontherrock_bt -> {
                        favorkeyword.remove("온더락")
                        binding.startFilterKeywordOntherrockBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_sangkum_bt -> {
                        favorkeyword.remove("상큼한")
                        binding.startFilterKeywordSangkumBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_dansun_bt -> {
                        favorkeyword.remove("단순한")
                        binding.startFilterKeywordDansunBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_milk_bt -> {
                        favorkeyword.remove("우유")
                        binding.startFilterKeywordMilkBt.setTextColor(resources.getColor(R.color.white))
                    }
                    R.id.start_filter_keyword_bockjap_bt -> {
                        favorkeyword.remove("복잡한")
                        binding.startFilterKeywordBockjapBt.setTextColor(resources.getColor(R.color.white))
                    }
                }
            }
            Log.d("Start_filter_test", favorkeyword.toString())
        }
        binding.startFilterKeywordLadykillerBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordShooterBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordCleanBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordTansanBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordLayeredBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordMartiniBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordPrettyBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordHighballBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordSweetBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordDockhanBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordSangqueBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordFluitfavorBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordSsupssupBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordOntherrockBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordSangkumBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordDansunBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordMilkBt.setOnCheckedChangeListener(favorListner)
        binding.startFilterKeywordBockjapBt.setOnCheckedChangeListener(favorListner)

    }



}