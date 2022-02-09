package com.example.cocktail_dakk.ui.start.setting.fragment

import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.FragmentStartELikeBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.start.setting.StartSettingActivity
import com.google.gson.Gson


class StartELikeFragment :
    BaseFragment<FragmentStartELikeBinding>(FragmentStartELikeBinding::inflate) {

    var gijukeyword = ArrayList<String>()
    var gijustr = ""
    var flag = false
    override fun initAfterBinding() {

        SetdosuListener()
        binding.startGijucapNextTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                gijustr = ""
                for(i in gijukeyword){
                    gijustr += i + ","
                }
                if(gijustr.length ==0){
                    Toast.makeText(requireContext(),"적어도 하나의 기주를 선택해주세요.",Toast.LENGTH_SHORT).show()
                }
                else{
                    (activity as StartSettingActivity).setBasegiju(gijustr)
                    (activity as StartSettingActivity).Nextpage()
                }
            }
        })

        binding.startGijucapBackTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as StartSettingActivity).Undopage()
            }
        })
    }

    private fun SetdosuListener() {
        var gijuListner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.setting_like_vodca_cb -> {
                        binding.settingLikeVodcaCb.setTextColor(resources.getColor(R.color.black))
                        gijukeyword.add("보드카")
                    }
                    R.id.setting_like_rum_cb -> {
                        binding.settingLikeRumCb.setTextColor(resources.getColor(R.color.black))
                        gijukeyword.add("럼")
                    }
                    R.id.setting_like_tequila_cb -> {
                        binding.settingLikeTequilaCb.setTextColor(resources.getColor(R.color.black))
                        gijukeyword.add("데킬라")
                    }
                    R.id.setting_like_wiski_tv -> {
                        binding.settingLikeWiskiTv.setTextColor(resources.getColor(R.color.black))
                        gijukeyword.add("위스키")
                    }
                    R.id.setting_like_brandy_cb -> {
                        binding.settingLikeBrandyCb.setTextColor(resources.getColor(R.color.black))
                        gijukeyword.add("브랜디")
                    }
                    R.id.setting_like_liqueur_cb -> {
                        binding.settingLikeLiqueurCb.setTextColor(resources.getColor(R.color.black))
                        gijukeyword.add("리큐르")
                    }
                    R.id.setting_like_jin_tv -> {
                        binding.settingLikeJinTv.setTextColor(resources.getColor(R.color.black))
                        gijukeyword.add("진")
                    }
                    R.id.setting_like_anything_tv -> {
                        binding.settingLikeAnythingTv.setTextColor(resources.getColor(R.color.black))
                        gijukeyword.add("상관없음")
                    }
                }
            } else {
                when (buttonView.id) {
                    R.id.setting_like_vodca_cb -> {
                        binding.settingLikeVodcaCb.setTextColor(resources.getColor(R.color.white))
                        gijukeyword.remove("보드카")
                    }
                    R.id.setting_like_rum_cb -> {
                        binding.settingLikeRumCb.setTextColor(resources.getColor(R.color.white))
                        gijukeyword.remove("럼")
                    }
                    R.id.setting_like_tequila_cb -> {
                        binding.settingLikeTequilaCb.setTextColor(resources.getColor(R.color.white))
                        gijukeyword.remove("데킬라")
                    }
                    R.id.setting_like_wiski_tv -> {
                        binding.settingLikeWiskiTv.setTextColor(resources.getColor(R.color.white))
                        gijukeyword.remove("위스키")
                    }
                    R.id.setting_like_brandy_cb -> {
                        binding.settingLikeBrandyCb.setTextColor(resources.getColor(R.color.white))
                        gijukeyword.remove("브랜디")
                    }
                    R.id.setting_like_liqueur_cb -> {
                        binding.settingLikeLiqueurCb.setTextColor(resources.getColor(R.color.white))
                        gijukeyword.remove("리큐르")
                    }
                    R.id.setting_like_jin_tv -> {
                        binding.settingLikeJinTv.setTextColor(resources.getColor(R.color.white))
                        gijukeyword.remove("진")
                    }
                    R.id.setting_like_anything_tv -> {
                        binding.settingLikeAnythingTv.setTextColor(resources.getColor(R.color.white))
                        gijukeyword.remove("상관없음")
                    }
                }
            }
            Log.d("test", gijukeyword.toString())

        }
        binding.settingLikeVodcaCb.setOnCheckedChangeListener(gijuListner)
        binding.settingLikeRumCb.setOnCheckedChangeListener(gijuListner)
        binding.settingLikeTequilaCb.setOnCheckedChangeListener(gijuListner)
        binding.settingLikeWiskiTv.setOnCheckedChangeListener(gijuListner)
        binding.settingLikeBrandyCb.setOnCheckedChangeListener(gijuListner)
        binding.settingLikeLiqueurCb.setOnCheckedChangeListener(gijuListner)
        binding.settingLikeJinTv.setOnCheckedChangeListener(gijuListner)
        binding.settingLikeAnythingTv.setOnCheckedChangeListener(gijuListner)

    }
}