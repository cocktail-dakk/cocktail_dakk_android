package com.example.cocktail_dakk.ui.start.setting.fragment

import android.util.Log
import android.view.View
import com.example.cocktail_dakk.databinding.FragmentStartDDrinkcapBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.start.setting.StartSettingActivity
import hearsilent.discreteslider.DiscreteSlider
import it.mirko.rangeseekbar.OnRangeSeekBarListener
import it.mirko.rangeseekbar.RangeSeekBar


class StartDDrinkcapFragment : BaseFragment<FragmentStartDDrinkcapBinding>(FragmentStartDDrinkcapBinding::inflate){
    override fun initAfterBinding() {
        changedosutv(binding.itemDrinkcapSlider.minProgress,binding.itemDrinkcapSlider.maxProgress)
        binding.itemDrinkcapSlider.setValueChangedImmediately(true)
        binding.itemDrinkcapSlider.setOnValueChangedListener(object : DiscreteSlider.OnValueChangedListener() {
            override fun onValueChanged(minProgress: Int, maxProgress: Int, fromUser: Boolean) {
                super.onValueChanged(minProgress, maxProgress, fromUser)
                changedosutv(minProgress,maxProgress)
            }
        })

        binding.startDrinkcapNextTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as StartSettingActivity).setdosu(binding.itemDrinkcapSlider.minProgress,binding.itemDrinkcapSlider.maxProgress)
                (activity as StartSettingActivity).Nextpage()
            }
        })

        binding.startDrinkcapBackTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as StartSettingActivity).Undopage()
            }
        })

    }

    private fun changedosutv(mindosu :Int, maxdosu : Int) {
        binding.startDrinkcapRangeTv.setText(mindosu.toString() + "도 ~ " + maxdosu.toString() + "도")
    }


}