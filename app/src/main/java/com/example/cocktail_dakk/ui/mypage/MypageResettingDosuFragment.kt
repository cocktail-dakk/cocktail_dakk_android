package com.example.cocktail_dakk.ui.mypage

import android.util.Log
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

        // 초기데이터 처리 (1회용)
        (activity as MainActivity).setMypageTempGijulist((activity as MainActivity).getMypageGijulist())
        (activity as MainActivity).setMypageTempKeywords((activity as MainActivity).getMypageKeywords())

        super.onStart()
        Log.d("lifeMy_1", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifeMy_1", "onResume")
    }

    override fun onPause() {
        Log.d("lifeMy_1", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("lifeMy_1", "onStop")
        super.onStop()
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