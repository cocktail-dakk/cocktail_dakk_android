package com.umcapplunching.cocktail_dakk.ui.start.setting.fragment

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.FragmentStartCGenderBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.start.setting.StartSettingActivity


class StartCGenderFragment : BaseFragment<FragmentStartCGenderBinding>(FragmentStartCGenderBinding::inflate) {

    var gender = "M"
    var flag = false

    override fun initAfterBinding() {
        binding.itemStartSettingGenderMIb.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                binding.itemStartSettingGenderMIb.setImageResource(R.drawable.setting_gender_m_on)
                binding.itemStartSettingGenderWIb.setImageResource(R.drawable.setting_gender_w_off)
                binding.itemStartSettingGenderMIb.scaleType = ImageView.ScaleType.FIT_CENTER
                binding.itemStartSettingGenderWIb.scaleType = ImageView.ScaleType.CENTER
                gender = "M"
                flag = true
            }
        })

        binding.itemStartSettingGenderWIb.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                binding.itemStartSettingGenderMIb.setImageResource(R.drawable.setting_gender_m_off)
                binding.itemStartSettingGenderWIb.setImageResource(R.drawable.setting_gender_w_on)
                binding.itemStartSettingGenderWIb.scaleType = ImageView.ScaleType.FIT_CENTER
                binding.itemStartSettingGenderMIb.scaleType = ImageView.ScaleType.CENTER
                gender = "F"
                flag = true
            }
        })

        binding.startGenderNextTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (flag){
                    (activity as StartSettingActivity).setGender(gender)
                    (activity as StartSettingActivity).Nextpage()
                }
                else{
                    Toast.makeText(requireContext(),"성별을 선택해 주세요.",Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.startGenderBackTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as StartSettingActivity).Undopage()
            }
        })
    }

}