package com.example.cocktail_dakk.ui.start.setting.fragment

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.FragmentStartCGenderBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.start.setting.StartSettingActivity


class StartCGenderFragment : BaseFragment<FragmentStartCGenderBinding>(FragmentStartCGenderBinding::inflate) {

    var gender = "M"

    override fun initAfterBinding() {
        binding.itemStartSettingGenderMIb.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                binding.itemStartSettingGenderMIb.setImageResource(R.drawable.setting_gender_m_on)
                binding.itemStartSettingGenderWIb.setImageResource(R.drawable.setting_gender_w_off)
                binding.itemStartSettingGenderMIb.scaleType = ImageView.ScaleType.FIT_CENTER
                binding.itemStartSettingGenderWIb.scaleType = ImageView.ScaleType.CENTER

                gender = "M"
            }
        })

        binding.itemStartSettingGenderWIb.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                binding.itemStartSettingGenderMIb.setImageResource(R.drawable.setting_gender_m_off)
                binding.itemStartSettingGenderWIb.setImageResource(R.drawable.setting_gender_w_on)
                binding.itemStartSettingGenderWIb.scaleType = ImageView.ScaleType.FIT_CENTER
                binding.itemStartSettingGenderMIb.scaleType = ImageView.ScaleType.CENTER
                gender = "F"
            }
        })

        binding.startGenderNextTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as StartSettingActivity).setGender(gender)
                (activity as StartSettingActivity).Nextpage()
            }
        })

        binding.startGenderBackTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as StartSettingActivity).Undopage()
            }
        })
    }

}