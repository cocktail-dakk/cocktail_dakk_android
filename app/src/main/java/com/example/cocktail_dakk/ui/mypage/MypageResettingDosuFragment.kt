package com.example.cocktail_dakk.ui.mypage

import android.widget.SeekBar
import com.example.cocktail_dakk.databinding.FragmentMypageResettingDosuBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.MainActivity


class MypageResettingDosuFragment():BaseFragment<FragmentMypageResettingDosuBinding>(FragmentMypageResettingDosuBinding::inflate) {
    override fun initAfterBinding() {


        binding.mypageResettingDosuSliderSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.mypageResettingDosuRangeTv.text = progress.toString()+"도"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                (activity as MainActivity).setMypageTempDosu(binding.mypageResettingDosuSliderSb.progress)
            }
        })
        (activity as MainActivity).setMypageTempDosu(binding.mypageResettingDosuSliderSb.progress)

    }

    override fun onStart() {
        binding.mypageResettingDosuSliderSb.progress = (activity as MainActivity)!!.getMypageDosu()
        binding.mypageResettingDosuRangeTv.text = binding.mypageResettingDosuSliderSb.progress.toString()+"도"
        super.onStart()

    }

//
//    override fun onStop() {
//        super.onStop()
//        onDestroy()
//    }

//
//    // 크기 다시 조절해주기
//    override fun onResume() {
//        super.onResume()
//        binding.root.requestLayout()
//    }

}