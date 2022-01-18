package com.example.cocktail_dakk.ui.menu_detail

import android.view.View
import android.widget.Toast
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.ActivityMenuDetailEvaluateBinding
import com.example.cocktail_dakk.ui.BaseActivity

class MenuDetailEvaluateActivity: BaseActivity<ActivityMenuDetailEvaluateBinding>(ActivityMenuDetailEvaluateBinding::inflate) {

    private var starPoint: Int = -1

    override fun initAfterBinding() {
        initCocktailName()
        initClicker()
    }

    private fun initCocktailName() {
        if (intent.hasExtra("localName") && intent.hasExtra("englishName") ){
            binding.menuDetailEvaluateNameLocalTv.text = intent.getStringExtra("localName")
            binding.menuDetailEvaluateNameEnglishTv.text = intent.getStringExtra("englishName")
            binding.menuDetailEvaluateGuideTv.text = intent.getStringExtra("localName")+"에 대한 별점을 평가해 주세요."
        }
    }

    private fun initClicker(){
        binding.menuDetailEvaluateWhiteboardLa.setOnClickListener(){
            // 아무것도 안함. 배경 클릭과의 대비를 두기 위한 코드. 지우지 말것!
        }

        binding.menuDetailEvaluateBackgroundLa.setOnClickListener(){
            finish()
        }
        binding.menuDetailEvaluateExitIv.setOnClickListener(){
            finish()
        }

        binding.menuDetailEvaluateStar1Iv.setOnClickListener(){
            starPoint = 1
            clickStar(1)
        }
        binding.menuDetailEvaluateStar2Iv.setOnClickListener(){
            starPoint = 2
            clickStar(2)
        }
        binding.menuDetailEvaluateStar3Iv.setOnClickListener(){
            starPoint = 3
            clickStar(3)
        }
        binding.menuDetailEvaluateStar4Iv.setOnClickListener(){
            starPoint = 4
            clickStar(4)
        }
        binding.menuDetailEvaluateStar5Iv.setOnClickListener(){
            starPoint = 5
            clickStar(5)
        }


        binding.menuDetailEvaluateOkOffTv.setOnClickListener(){
            Toast.makeText(this, "별점을 평가해 주세요.", Toast.LENGTH_SHORT).show()
        }
        binding.menuDetailEvaluateOkOnTv.setOnClickListener(){
            Toast.makeText(this, "별점 ${starPoint}점을 기록했습니다.", Toast.LENGTH_SHORT).show()
            // 일단은 토스트 메시지로 기록하지만 서버에 점수 정보를 보내 평균을 낼것
            finish()
        }
    }

    private fun clickStar(point: Int){
        val full = R.drawable.detail_star
        val empty = R.drawable.detail_star_empty

        when (point){
            1 -> {
                binding.menuDetailEvaluateStar1Iv.setImageResource(full)
                binding.menuDetailEvaluateStar2Iv.setImageResource(empty)
                binding.menuDetailEvaluateStar3Iv.setImageResource(empty)
                binding.menuDetailEvaluateStar4Iv.setImageResource(empty)
                binding.menuDetailEvaluateStar5Iv.setImageResource(empty)
            }
           2 -> {
                binding.menuDetailEvaluateStar1Iv.setImageResource(full)
                binding.menuDetailEvaluateStar2Iv.setImageResource(full)
                binding.menuDetailEvaluateStar3Iv.setImageResource(empty)
                binding.menuDetailEvaluateStar4Iv.setImageResource(empty)
                binding.menuDetailEvaluateStar5Iv.setImageResource(empty)
            }
           3 -> {
                binding.menuDetailEvaluateStar1Iv.setImageResource(full)
                binding.menuDetailEvaluateStar2Iv.setImageResource(full)
                binding.menuDetailEvaluateStar3Iv.setImageResource(full)
                binding.menuDetailEvaluateStar4Iv.setImageResource(empty)
                binding.menuDetailEvaluateStar5Iv.setImageResource(empty)
            }
            4 -> {
                binding.menuDetailEvaluateStar1Iv.setImageResource(full)
                binding.menuDetailEvaluateStar2Iv.setImageResource(full)
                binding.menuDetailEvaluateStar3Iv.setImageResource(full)
                binding.menuDetailEvaluateStar4Iv.setImageResource(full)
                binding.menuDetailEvaluateStar5Iv.setImageResource(empty)
            }
            5 -> {
                binding.menuDetailEvaluateStar1Iv.setImageResource(full)
                binding.menuDetailEvaluateStar2Iv.setImageResource(full)
                binding.menuDetailEvaluateStar3Iv.setImageResource(full)
                binding.menuDetailEvaluateStar4Iv.setImageResource(full)
                binding.menuDetailEvaluateStar5Iv.setImageResource(full)
            }
        }

        binding.menuDetailEvaluateOkOffTv.visibility = View.INVISIBLE
        binding.menuDetailEvaluateOkOnTv.visibility = View.VISIBLE
    }

}