package com.example.cocktail_dakk.ui.menu_detail

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail
import com.example.cocktail_dakk.databinding.ActivityMenuDetailBinding
import com.example.cocktail_dakk.ui.BaseActivity
import org.w3c.dom.Text

class MenuDetailActivity : BaseActivity<ActivityMenuDetailBinding>(ActivityMenuDetailBinding::inflate) {

    private val unitList = arrayListOf("ml", "piece", "개", "필업") // 단위 리스트. 나중에 다른 곳으로 옮길것

    private val cocktail = Cocktail("핑크 레이디", "Pink Lady", R.drawable.detail_bg,
        "", "",
        3.5f, 20,
        "달걀 흰자 1개, 그레나딘 시럽 - 1/3oz (10ml), 크림 - 1/2oz (15ml), 드라이 진 - 1 1/2oz (45ml)",
        "상큼한, 예쁜, 과일향",
        "진을 베이스로 한 분홍색 칵테일\n" +
                "색깔을 내기 위해 그레나딘 시럽을 넣으며, 계란 흰자와 크림을 추가하여\n" +
                "입에 닿는 느낌은 비교적 부드러운 편\n" +
                "진 베이스 칵테일 입문으로 하기 좋은 칵테일"
    )

    private var ingredients : ArrayList<String> = ArrayList()
    private val ratios : MutableList<Int> = ArrayList()

    override fun initAfterBinding() {

        initCocktail(cocktail)
    }
    

    private fun initCocktail(cocktail: Cocktail ){
        // local 이름, english 이름, image 넣기
        binding.menuDetailNameLocalTv.text = cocktail.localName
        binding.menuDetailNameEnglishTv.text = cocktail.englishName
        binding.menuDetailBackgroundIv.setImageResource(cocktail.image)
        
        // 별점 넣기, 도수 넣기
        initStarPoint(cocktail.starPoint, binding.menuDetailStarContext1Iv, binding.menuDetailStarContext2Iv, binding.menuDetailStarContext3Iv, binding.menuDetailStarContext4Iv, binding.menuDetailStarContext5Iv, )
        binding.menuDetailAlcoholLevelContextTv.text = cocktail.alcoholLevel.toString()

        // 재료와 비율 넣기
        initIngredientsAndRatio(cocktail.ingredients)
        for (ing in ingredients) {
            binding.menuDetailIngredientsContextLa.addView(createTextView(ing, 13.0f, "000000"))
            binding.menuDetailIngredientsContextLa.addView(createTextView("", 0f,"000000",10,20))
        }


        // 키워드 - 수정 필요
//        while (true){
//            if (cocktail.keyword1 == "")
//                break
//            else {
//                binding.menuDetailKeywordsContext1Tv.text = cocktail.keyword1
//                binding.menuDetailKeywordsContext1Tv.visibility = View.VISIBLE
//            }
//            if (cocktail.keyword2 == "")
//                break
//            else {
//                binding.menuDetailKeywordsContext2Tv.text = cocktail.keyword2
//                binding.menuDetailKeywordsContext2Tv.visibility = View.VISIBLE
//            }
//            if (cocktail.keyword3 == "")
//                break
//            else {
//                binding.menuDetailKeywordsContext3Tv.text = cocktail.keyword3
//                binding.menuDetailKeywordsContext3Tv.visibility = View.VISIBLE
//            }
//            if (cocktail.keyword4 == "")
//                break
//            else {
//                binding.menuDetailKeywordsContext4Tv.text = cocktail.keyword4
//                binding.menuDetailKeywordsContext4Tv.visibility = View.VISIBLE
//            }
//            break
//        }

        // 정보
        binding.menuDetailCocktailInformationContextTv.text = cocktail.information
//
//        // 재료 - 수정 필요
//        binding.menuDetailIngredientsContext1Tv.text = cocktail.ingredient1
//        binding.menuDetailIngredientsContext2Tv.text = cocktail.ingredient2
//        binding.menuDetailIngredientsContext3Tv.text = cocktail.ingredient3
//        binding.menuDetailIngredientsContext4Tv.text = cocktail.ingredient4
//        binding.menuDetailRecipeContext1Tv.text = cocktail.ingredient1
//        binding.menuDetailRecipeContext2Tv.text = cocktail.ingredient2
//        binding.menuDetailRecipeContext3Tv.text = cocktail.ingredient3
//        binding.menuDetailRecipeContext4Tv.text = cocktail.ingredient4
//        binding.menuDetailRecipeRatio1Iv.background.setTint(Color.parseColor("#"+cocktail.color1))
//        binding.menuDetailRecipeRatio2Iv.background.setTint(Color.parseColor("#"+cocktail.color2))
//        binding.menuDetailRecipeRatio3Iv.background.setTint(Color.parseColor("#"+cocktail.color3))
//        binding.menuDetailRecipeRatio4Iv.background.setTint(Color.parseColor("#"+cocktail.color4))
//        binding.menuDetailRecipeContext1Vu.background.setTint(Color.parseColor("#"+cocktail.color1))
//        binding.menuDetailRecipeContext2Vu.background.setTint(Color.parseColor("#"+cocktail.color2))
//        binding.menuDetailRecipeContext3Vu.background.setTint(Color.parseColor("#"+cocktail.color3))
//        binding.menuDetailRecipeContext4Vu.background.setTint(Color.parseColor("#"+cocktail.color4))
//
//        // 재료 비율
//        var ratioSum: Int = 0
//        var underFourCount: Int = 0
//        if (cocktail.ratio1 > 4) {
//            ratioSum += cocktail.ratio1
//        }else {
//            underFourCount += 1
//        }
//        if (cocktail.ratio2 > 4) {
//            ratioSum += cocktail.ratio2
//        }else {
//            underFourCount += 1
//        }
//        if (cocktail.ratio3 > 4) {
//            ratioSum += cocktail.ratio3
//        }else {
//            underFourCount += 1
//        }
//        if (cocktail.ratio4 > 4) {
//            ratioSum += cocktail.ratio4
//        }else {
//            underFourCount += 1
//        }
//        val w1 = ((150 - underFourCount*4)*cocktail.ratio1/ratioSum).toFloat()
//        val w2 = ((150 - underFourCount*4)*cocktail.ratio2/ratioSum).toFloat()
//        val w3 = ((150 - underFourCount*4)*cocktail.ratio3/ratioSum).toFloat()
//        val w4 = ((150 - underFourCount*4)*cocktail.ratio4/ratioSum).toFloat()
//
//        (binding.menuDetailRecipeRatio1Iv.layoutParams as LinearLayout.LayoutParams).weight = if (w1>4) {w1} else {4.0f}
//        (binding.menuDetailRecipeRatio2Iv.layoutParams as LinearLayout.LayoutParams).weight = if (w2>4) {w2} else {4.0f}
//        (binding.menuDetailRecipeRatio3Iv.layoutParams as LinearLayout.LayoutParams).weight = if (w3>4) {w3} else {4.0f}
//        (binding.menuDetailRecipeRatio4Iv.layoutParams as LinearLayout.LayoutParams).weight = if (w4>4) {w4} else {4.0f}
//        binding.menuDetailRecipeRatioLa.requestLayout()
    }
    
    private fun initStarPoint(starPoint: Float, star_1: ImageView, star_2: ImageView, star_3: ImageView, star_4: ImageView, star_5: ImageView){

        // 별점
        // 0.5 단위로 "버림" 연산
        // 예) 5.0 -> 5  //  4.8 -> 4.5  // 4.4 -> 4  // 2.1 -> 2
        // 0.0점~0.99점 까지는 예외적으로 0.5 를 줬음. (하나도 안 채워져 있으면 이상해보여서)
        
        val starEmpty: Int = R.drawable.detail_star_empty
        val starFull: Int = R.drawable.detail_star
        val starHalf: Int = R.drawable.detail_star_half
        
        if (starPoint >= 1.0f) {
            star_1.setImageResource(starFull)
            if (starPoint >= 2.0f) {
                star_2.setImageResource(starFull)
                if (starPoint >= 3.0f) {
                    star_3.setImageResource(starFull)
                    if (starPoint >= 4.0f) {
                        star_4.setImageResource(starFull)
                        if (starPoint >= 5.0f) {
                            star_5.setImageResource(starFull)
                        } else if (starPoint >= 4.5f) {
                            star_5.setImageResource(starHalf)
                        } else {
                            star_5.setImageResource(starEmpty)
                        }
                    } else if (starPoint >= 3.5f) {
                        star_4.setImageResource(starHalf)
                    } else {
                        star_4.setImageResource(starEmpty)
                    }
                } else if (starPoint >= 2.5f) {
                    star_3.setImageResource(starHalf)
                } else {
                    star_3.setImageResource(starEmpty)
                }
            } else if (starPoint >= 1.5f) {
                star_2.setImageResource(starHalf)
            } else {
                star_2.setImageResource(starEmpty)
            }
        } else {  // 0.0점~0.99점 까지는 예외적으로 0.5 를 줬음. (하나도 안 채워져 있으면 이상해보여서)
            star_1.setImageResource(starHalf)
        }

    }

    private fun initIngredientsAndRatio(inputIngredients: String){
        // ingredients
        ingredients = inputIngredients.split(",") as ArrayList<String>
        for (i in 0 until ingredients.size){
            ingredients[i] = ingredients[i].trim()
        }

        // ratios
        for (ing in ingredients){
            var unitCount = 0
            var unitVal = 0

            while (unitCount < 4){
                var unitIdx = ing.lastIndexOf(unitList[unitCount])
                if (unitIdx == -1){
                    unitCount++
                } else {
                    unitVal = if (unitCount == 3) { // 필업인 경우 고정값
                        70
                    } else { // 단위 앞의 숫자를 unitVal에 찾아 넣기
                        var startIdx = unitIdx-1
                        while (startIdx >=0) {
                            var temp = Character.getNumericValue(ing[startIdx])
                            if (temp == -1) {
                                startIdx++
                                break
                            }

                            startIdx--
                        }
                        ing.substring(startIdx until unitIdx).toInt()
                    }
                    break
                }
            }
            ratios.add(unitVal)
        }

    }

    private fun createTextView(inputText : String, size: Float, color: String, width: Int = -1, height: Int = -1) :TextView{
        val textView = TextView(this)
        textView.text = inputText
        textView.textSize = size
        textView.setTextColor(Color.parseColor("#$color"))
        val lp =
            if (width==-1 && height==-1) LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            else LinearLayout.LayoutParams(width, height)
        textView.layoutParams = lp
        return textView
    }
}