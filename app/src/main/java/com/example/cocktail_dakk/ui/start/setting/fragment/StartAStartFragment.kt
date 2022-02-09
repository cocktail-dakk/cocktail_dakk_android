package com.example.cocktail_dakk.ui.start.setting.fragment

import android.view.View
import com.example.cocktail_dakk.databinding.FragmentStartAStartBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.start.setting.StartSettingActivity


class StartAStartFragment(var nickname : String) : BaseFragment<FragmentStartAStartBinding>(FragmentStartAStartBinding::inflate) {
    override fun initAfterBinding() {
        binding.startNameTitleTv.setText("안녕하세요\n"+ "\'" + nickname +"\'" +"님\n취향 키워드 설정을 시작합니다")

        binding.startNameNextTv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as StartSettingActivity).Nextpage()
            }
        })
    }

}