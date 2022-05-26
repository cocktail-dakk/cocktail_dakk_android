package com.umcapplunching.cocktail_dakk.ui.start.setting.fragment

import android.annotation.SuppressLint
import android.view.View
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.FragmentStartAStartBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.BaseFragmentByDataBinding
import com.umcapplunching.cocktail_dakk.ui.start.setting.StartSettingActivity


class StartAStartFragment(var nickname : String) : BaseFragmentByDataBinding<FragmentStartAStartBinding>(
    R.layout.fragment_start_a_start) {

    override fun initView() {
        binding.startNameTitleTv.text = "안녕하세요\n\'$nickname\'님\n취향 키워드 설정을 시작합니다"
        binding.startNameNextTv.setOnClickListener { (activity as StartSettingActivity).Nextpage() }
    }

}