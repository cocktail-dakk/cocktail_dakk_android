package com.umcapplunching.cocktail_dakk.ui.start.setting.fragment

import android.widget.SeekBar
import com.umcapplunching.cocktail_dakk.databinding.FragmentStartDDrinkcapBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.start.setting.StartSettingActivity

class StartDDrinkcapFragment : BaseFragment<FragmentStartDDrinkcapBinding>(FragmentStartDDrinkcapBinding::inflate){
    var dosu : Int = 10
    override fun initAfterBinding() {
        binding.startDrinkcapNextTv.setOnClickListener {
            (activity as StartSettingActivity).setdosu(binding.itemDrinkcapSlider.progress)
            (activity as StartSettingActivity).Nextpage()
        }

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

        binding.startDrinkcapBackTv.setOnClickListener { (activity as StartSettingActivity).Undopage() }
    }

    private fun changedosutv(mindosu :Int) {
        binding.startDrinkcapRangeTv.text = mindosu.toString() + "도"
    }

}