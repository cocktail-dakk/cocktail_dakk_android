package com.umcapplunching.cocktail_dakk.ui.menu_detail

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.CocktailDatabase
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_Rating
import com.umcapplunching.cocktail_dakk.databinding.DialogChangeNicknameBinding
import com.umcapplunching.cocktail_dakk.databinding.DialogEvalutaionBinding

class DialogEvaluate(context: Context,val localname : String, val englishname : String, val setEvaulate:(Int) -> Unit) : Dialog(context) {

    private lateinit var binding : DialogEvalutaionBinding
    private var tempStarPoint: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogEvalutaionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initLitener()
    }


    private fun initViews() {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.menuDetailEvaluateStar1Iv.setImageResource(R.drawable.star_off)
        binding.menuDetailEvaluateStar2Iv.setImageResource(R.drawable.star_off)
        binding.menuDetailEvaluateStar3Iv.setImageResource(R.drawable.star_off)
        binding.menuDetailEvaluateStar4Iv.setImageResource(R.drawable.star_off)
        binding.menuDetailEvaluateStar5Iv.setImageResource(R.drawable.star_off)
        binding.menuDetailEvaluateNameLocalTv.text =localname
        binding.menuDetailEvaluateNameEnglishTv.text = englishname
        binding.menuDetailEvaluateGuideTv.text = "${localname}에 대한 별점을 평가해 주세요."
        binding.menuDetailEvaluateOkOffTv.visibility = View.VISIBLE
        binding.menuDetailEvaluateOkOnTv.visibility = View.INVISIBLE
    }

    private fun initLitener(){
        binding.menuDetailEvaluateWhiteboardLa.setOnClickListener{
            // 아무것도 안함. 배경 클릭과의 대비를 두기 위한 코드. 지우지 말것!
        }

        binding.menuDetailEvaluateBackgroundLa.setOnClickListener{
            dismiss()
        }
        binding.menuDetailEvaluateExitIv.setOnClickListener{
            dismiss()
        }

        binding.menuDetailEvaluateStar1Iv.setOnClickListener{
            tempStarPoint = 1
            clickStar(1.0)
        }
        binding.menuDetailEvaluateStar2Iv.setOnClickListener{
            tempStarPoint = 2
            clickStar(2.0)
        }
        binding.menuDetailEvaluateStar3Iv.setOnClickListener{
            tempStarPoint = 3
            clickStar(3.0)
        }
        binding.menuDetailEvaluateStar4Iv.setOnClickListener{
            tempStarPoint = 4
            clickStar(4.0)
        }
        binding.menuDetailEvaluateStar5Iv.setOnClickListener{
            tempStarPoint = 5
            clickStar(5.0)
        }

        binding.menuDetailEvaluateOkOffTv.setOnClickListener{
            Toast.makeText(context, "별점을 평가해 주세요.", Toast.LENGTH_SHORT).show()
        }

        binding.menuDetailEvaluateOkOnTv.setOnClickListener{
//            val CocktailDB = CocktailDatabase.getInstance(this)!!
//            CocktailDB.RatingDao().insert(Cocktail_Rating(cocktailInfoId))
//            binding.menuDetailStarEvaluateTv.text = "평가 완료"
//            binding.menuDetailStarEvaluateTv.setOnClickListener {
//                Toast.makeText(this,"이미 평가 하셨습니다!", Toast.LENGTH_SHORT).show()
//            }
            setEvaulate(tempStarPoint)
            dismiss()
        }
    }

    private fun clickStar(point: Double){
        val full = R.mipmap.icon_star_on
        val empty = R.mipmap.icon_star_off

        when (point){
            1.0 -> {
                binding.menuDetailEvaluateStar1Iv.setImageResource(full)
                binding.menuDetailEvaluateStar2Iv.setImageResource(empty)
                binding.menuDetailEvaluateStar3Iv.setImageResource(empty)
                binding.menuDetailEvaluateStar4Iv.setImageResource(empty)
                binding.menuDetailEvaluateStar5Iv.setImageResource(empty)
            }
            2.0 -> {
                binding.menuDetailEvaluateStar1Iv.setImageResource(full)
                binding.menuDetailEvaluateStar2Iv.setImageResource(full)
                binding.menuDetailEvaluateStar3Iv.setImageResource(empty)
                binding.menuDetailEvaluateStar4Iv.setImageResource(empty)
                binding.menuDetailEvaluateStar5Iv.setImageResource(empty)
            }
            3.0 -> {
                binding.menuDetailEvaluateStar1Iv.setImageResource(full)
                binding.menuDetailEvaluateStar2Iv.setImageResource(full)
                binding.menuDetailEvaluateStar3Iv.setImageResource(full)
                binding.menuDetailEvaluateStar4Iv.setImageResource(empty)
                binding.menuDetailEvaluateStar5Iv.setImageResource(empty)
            }
            4.0 -> {
                binding.menuDetailEvaluateStar1Iv.setImageResource(full)
                binding.menuDetailEvaluateStar2Iv.setImageResource(full)
                binding.menuDetailEvaluateStar3Iv.setImageResource(full)
                binding.menuDetailEvaluateStar4Iv.setImageResource(full)
                binding.menuDetailEvaluateStar5Iv.setImageResource(empty)
            }
            5.0 -> {
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