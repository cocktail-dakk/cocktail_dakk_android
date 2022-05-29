package com.umcapplunching.cocktail_dakk.ui.mypage

import android.widget.SeekBar
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication
import com.umcapplunching.cocktail_dakk.databinding.FragmentMypageResettingDosuBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment


class MypageResettingDosuFragment(val setDosu: (dosu : Int) -> Unit) :BaseFragment<FragmentMypageResettingDosuBinding>(FragmentMypageResettingDosuBinding::inflate) {

    override fun initAfterBinding() {
        binding.mypageResettingDosuSliderSb.progress = CocktailDakkApplication.userInfoForApp.alcoholLevel
        binding.mypageResettingDosuRangeTv.text = "${binding.mypageResettingDosuSliderSb.progress}도"

        binding.mypageResettingDosuSliderSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.mypageResettingDosuRangeTv.text = progress.toString()+"도"
                setDosu(binding.mypageResettingDosuSliderSb.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                setDosu(binding.mypageResettingDosuSliderSb.progress)
            }
        })

    }

}