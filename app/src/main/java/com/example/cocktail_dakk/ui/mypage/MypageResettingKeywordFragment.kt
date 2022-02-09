package com.example.cocktail_dakk.ui.mypage

import com.example.cocktail_dakk.databinding.FragmentMypageResettingKeywordBinding
import com.example.cocktail_dakk.ui.BaseFragment

class MypageResettingKeywordFragment:BaseFragment<FragmentMypageResettingKeywordBinding>(FragmentMypageResettingKeywordBinding::inflate) {
    override fun initAfterBinding() {
    }
    // 크기 다시 조절해주기
    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }


}