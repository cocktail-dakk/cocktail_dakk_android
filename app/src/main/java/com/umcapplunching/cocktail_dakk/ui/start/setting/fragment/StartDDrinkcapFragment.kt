package com.umcapplunching.cocktail_dakk.ui.start.setting.fragment

import android.util.Log
import android.view.View
import android.widget.SeekBar
import com.umcapplunching.cocktail_dakk.databinding.FragmentStartDDrinkcapBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.start.setting.StartSettingActivity
import hearsilent.discreteslider.DiscreteSlider
import it.mirko.rangeseekbar.OnRangeSeekBarListener
import it.mirko.rangeseekbar.RangeSeekBar


class StartDDrinkcapFragment : BaseFragment<FragmentStartDDrinkcapBinding>(FragmentStartDDrinkcapBinding::inflate){
    var dosu : Int = 10
    override fun initAfterBinding() {
//        changedosutv(binding.itemDrinkcapSlider.minProgress,binding.itemDrinkcapSlider.maxProgress)
//        binding.itemDrinkcapSlider.setValueChangedImmediately(true)
//        binding.itemDrinkcapSlider.setOnValueChangedListener(object : DiscreteSlider.OnValueChangedListener() {
//            override fun onValueChanged(minProgress: Int, maxProgress: Int, fromUser: Boolean) {
//                super.onValueChanged(minProgress, maxProgress, fromUser)
//                changedosutv(minProgress,maxProgress)
//            }
//        })
//
        binding.startDrinkcapNextTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as StartSettingActivity).setdosu(binding.itemDrinkcapSlider.progress)
                (activity as StartSettingActivity).Nextpage()
            }
        })

        binding.itemDrinkcapSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                dosu = seekBar!!.progress
                changedosutv(dosu)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        binding.startDrinkcapBackTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as StartSettingActivity).Undopage()
            }
        })
    }

    private fun changedosutv(mindosu :Int) {
        binding.startDrinkcapRangeTv.setText(mindosu.toString() + "도")
    }

}