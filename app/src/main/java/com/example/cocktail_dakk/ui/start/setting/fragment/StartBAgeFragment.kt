package com.example.cocktail_dakk.ui.start.setting.fragment

import android.util.Log
import android.view.View
import android.widget.NumberPicker
import com.example.cocktail_dakk.databinding.FragmentStartBAgeBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.start.setting.StartSettingActivity


class StartBAgeFragment :
    BaseFragment<FragmentStartBAgeBinding>(FragmentStartBAgeBinding::inflate) {
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
                Log.d("text","old : " + oldVal.toString() + "new : "+ newVal.toString())
            }
        })

        binding.startAgeNextTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
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