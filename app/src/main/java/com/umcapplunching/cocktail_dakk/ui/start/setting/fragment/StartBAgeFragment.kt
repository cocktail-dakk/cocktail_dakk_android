package com.umcapplunching.cocktail_dakk.ui.start.setting.fragment

import android.view.View
import android.widget.NumberPicker
import com.umcapplunching.cocktail_dakk.databinding.FragmentStartBAgeBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.start.setting.StartSettingActivity

class StartBAgeFragment :
    BaseFragment<FragmentStartBAgeBinding>(FragmentStartBAgeBinding::inflate) {

    var age = 20

    override fun initAfterBinding() {
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

        binding.startAgeNextTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as StartSettingActivity).setAge(age)
                (activity as StartSettingActivity).Nextpage()
            }
        })

        binding.startAgeBackTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as StartSettingActivity).Undopage()
            }
        })

    }
}