package com.example.cocktail_dakk.ui.mypage

import com.example.cocktail_dakk.databinding.FragmentMypageResettingDosuBinding
import com.example.cocktail_dakk.ui.BaseFragment

class MypageResettingDosuFragment:BaseFragment<FragmentMypageResettingDosuBinding>(FragmentMypageResettingDosuBinding::inflate) {
    override fun initAfterBinding() {
    }

    // 크기 다시 조절해주기
    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}