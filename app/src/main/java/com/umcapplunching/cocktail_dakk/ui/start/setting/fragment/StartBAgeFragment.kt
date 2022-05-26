package com.umcapplunching.cocktail_dakk.ui.start.setting.fragment

import android.view.View
import android.widget.NumberPicker
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.FragmentStartBAgeBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragmentByDataBinding
import com.umcapplunching.cocktail_dakk.ui.start.setting.StartSettingActivity

class StartBAgeFragment :
    BaseFragmentByDataBinding<FragmentStartBAgeBinding>(R.layout.fragment_start_b_age) {
    private var age = 20

    override fun initView() {
        binding.itemStartSettingNumberpicker.isFadingEdgeEnabled = true
        binding.itemStartSettingNumberpicker.wrapSelectorWheel = true
        binding.itemStartSettingNumberpicker.setOnValueChangedListener(object :
            NumberPicker.OnValueChangeListener,
            com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener {
            override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {

            }
            override fun onValueChange(
                picker: com.shawnlin.numberpicker.NumberPicker?,
                oldVal: Int,
                newVal: Int
            ) {
                age = newVal
            }
        })

        binding.startAgeNextTv.setOnClickListener {
            (activity as StartSettingActivity).setAge(age)
            (activity as StartSettingActivity).Nextpage()
        }

        binding.startAgeBackTv.setOnClickListener { (activity as StartSettingActivity).Undopage() }

    }
}