package com.example.cocktail_dakk.ui.menu_detail

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import android.util.TypedValue
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.core.view.marginStart


class MenuDetailActivity : BaseActivity<ActivityMenuDetailBinding>(ActivityMenuDetailBinding::inflate) {

    // 단위 리스트. 나중에 다른 곳으로 옮길것
    private val unitList = arrayListOf("ml", "piece", "개", "필업")
    // 레시피 랜덤 색상 리스트. 나중에 다른 곳으로 옮길것
    private val colorList = arrayListOf("FF4668","FF6363", "FCF5A4", "BADF92", "03EF9A", "14D2D2", "19C0F2", "208DC8", "A35BBF", "C4A5E1")

    private val cocktail = Cocktail("핑크 레이디", "Pink Lady", R.drawable.detail_bg,
        "", "",
        3.5f, 20,
        "달걀 흰자 1개, 그레나딘 시럽 - 1/3oz (10ml), 크림 - 1/2oz (15ml), 드라이 진 - 1 1/2oz (45ml), 크림 - 1/2oz (15ml), 드라이 진 - 1 1/2oz (45ml), 크림 - 1/2oz (15ml), 드라이 진 - 1 1/2oz (45ml)",
        "상큼한, 예쁜, 과일향, testVal, testVal , testVal , testVal , testVal , testVal  ",
        "진을 베이스로 한 분홍색 칵테일\n" +
                "색깔을 내기 위해 그레나딘 시럽을 넣으며, 계란 흰자와 크림을 추가하여\n" +
                "입에 닿는 느낌은 비교적 부드러운 편\n" +
                "진 베이스 칵테일 입문으로 하기 좋은 칵테일"
    )

    private var ingredients : ArrayList<String> = ArrayList()
    private var keywords : ArrayList<String> = ArrayList()
    private val ratios : MutableList<Int> = ArrayList()
    private var colors : List<String> = (colorList as MutableList<String>).shuffled()
    private var weights : MutableList<Float> = ArrayList()

    override fun initAfterBinding() {

        initClicker()
        initCocktail(cocktail)
    }

    private fun initClicker(){

        binding.menuDetailBackIv.setOnClickListener(){
            finish()
        }

        binding.menuDetailStarEvaluateTv.setOnClickListener(){
            val intent = Intent(this, MenuDetailEvaluateActivity::class.java)
            intent.putExtra("localName",cocktail.localName)
            intent.putExtra("englishName",cocktail.englishName)
            startActivity(intent)
        }
    }
    

    private fun initCocktail(cocktail: Cocktail ){
        // local 이름, english 이름, image 넣기
        binding.menuDetailNameLocalTv.text = cocktail.localName
        binding.menuDetailNameEnglishTv.text = cocktail.englishName
        binding.menuDetailBackgroundIv.setImageResource(cocktail.image)

        // 별점 넣기, 도수 넣기
        initStarPoint(
            cocktail.starPoint,
            binding.menuDetailStarContext1Iv,
            binding.menuDetailStarContext2Iv,
            binding.menuDetailStarContext3Iv,
            binding.menuDetailStarContext4Iv,
            binding.menuDetailStarContext5Iv
        )
        binding.menuDetailAlcoholLevelContextTv.text = cocktail.alcoholLevel.toString()

        // 키워드 넣기
        initKeywords(cocktail.keywords)
        val keywordTextWidth = 60
        val keywordSpaceWidth = 10
        var keywordNumInOneLine = 0 // 한줄에 키워드 몇개가 필요할지
        val parentWidth = 340 // 각 디바이스별로 스크린 사이즈 받아오는걸로 수정 필요!
        val titleWidth = binding.menuDetailKeywordsTitleTv.width

        while (keywordNumInOneLine*(keywordTextWidth + keywordSpaceWidth) <= parentWidth - titleWidth * 2 ) {
            keywordNumInOneLine++
        }
        keywordNumInOneLine--

        var l1 = binding.menuDetailKeywordsContext01La
        val l2 = binding.menuDetailKeywordsContext02La
        val l3 = binding.menuDetailKeywordsContext03La
        for (i in 0 until keywords.size) {
            l1 = when (i) {
                keywordNumInOneLine -> {
                    l2
                }
                keywordNumInOneLine*2 -> {
                    l3
                }
                else -> {
                    l1
                }
            }
            l1.addView(createKeyword(keywords[i], 12.0f, "000000", keywordTextWidth))
            l1.addView(createTextView("", 12.0f, "000000", keywordSpaceWidth, 1))
        }

        // 정보 넣기
        binding.menuDetailCocktailInformationContextTv.text = cocktail.information

        // 재료와 비율 넣기
        initIngredientsAndRatio(cocktail.ingredients)
        for (ing in ingredients) {
            binding.menuDetailIngredientsContextLa.addView(createTextView(ing, 13.0f, "000000"))
            binding.menuDetailIngredientsContextLa.addView(createTextView("", 0f,"000000",10,13))
        }

        for (i in 0 until ingredients.size) {
            binding.menuDetailRecipeContextLa.addView(createIngredientWithColor(colors[i], ingredients[i], 13.0f, "FFFFFF"))
            binding.menuDetailRecipeContextLa.addView(createTextView("", 0f,"000000",5,10))
        }

        // 재료 비율 시각화와 색깔지정
        var ratioSum: Int = 0
        var underFourCount: Int = 0
        for (i in 0 until ratios.size) {
            if (ratios[i] > 4) {
            ratioSum += ratios[i]
            }else {
            underFourCount += 1
            }
        }
        for (i in 0 until ratios.size) {
            weights.add( ((150 - underFourCount*4) * ratios[i]/ratioSum).toFloat())
        }

        for (i in 0 until ratios.size) {
            binding.menuDetailRecipeRatioLa.addView(createViewWithWeight(colors[i], weights[i]))
            binding.menuDetailRecipeRatioLa.addView(createViewWithHeight(4))
        }
        // binding.menuDetailRecipeRatioLa.requestLayout()
        binding.menuDetailRecipeRatioLa.requestLayout()

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
                val unitIdx = ing.lastIndexOf(unitList[unitCount])
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

    private fun initKeywords(inputKeywords: String) {
        // keywords
        keywords = inputKeywords.split(",") as ArrayList<String>
        for (i in 0 until keywords.size) {
            keywords[i] = keywords[i].trim()
        }
    }

    private fun createTextView(inputText : String, size: Float, color: String, width: Int = -1, height: Int = -1) :TextView{
        val textView = TextView(this)
        textView.text = inputText
        textView.textSize = size
        textView.setTextColor(Color.parseColor("#$color"))
        val lp =
            if (width==-1 && height==-1) LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            else LinearLayout.LayoutParams(DPtoPX(this, width), DPtoPX(this, height))
        textView.layoutParams = lp
        return textView
    }

    private fun createKeyword(inputText : String, size: Float, color: String, width: Int = -1, height: Int = -1) :TextView{
        val textView = TextView(this)
        textView.text = inputText
        textView.textSize = size
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setBackgroundResource(R.drawable.round_rect_white_in_sky)
        textView.setTextColor(Color.parseColor("#$color"))
        textView.setPadding(0,DPtoPX(this,2),0,DPtoPX(this,2))
        val lp =
            if (width==-1 && height==-1) LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            else if (width != -1) {
                LinearLayout.LayoutParams(DPtoPX(this, width), ViewGroup.LayoutParams.WRAP_CONTENT)
            } else LinearLayout.LayoutParams(DPtoPX(this, width), DPtoPX(this, height))
        textView.layoutParams = lp
        return textView
    }

    private fun DPtoPX(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
    }

    private fun createViewWithWeight(colorText: String, inputWeight: Float) :View{
        val vu = View(this)
        vu.setBackgroundResource(R.drawable.shape_rect_white)
        vu.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#"+colorText))

        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DPtoPX(this, 1)).apply {
            weight = inputWeight
        }
        vu.layoutParams = lp
        return vu
    }

    private fun createViewWithHeight(inputHeight: Int) :View{
        val vu = View(this)
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DPtoPX(this, inputHeight))
        vu.layoutParams = lp
        return vu
    }

    private fun createIngredientWithColor(colorText:String, inputText : String, size: Float, textColor: String, width: Int = -1, height: Int = -1): LinearLayout{
        val la = LinearLayout(this)
        var lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        la.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT or Gravity.FILL_HORIZONTAL
        la.orientation = LinearLayout.HORIZONTAL
        la.layoutParams = lp

        val vu = View(this)
        vu.layoutParams = LinearLayout.LayoutParams(DPtoPX(this, 18), DPtoPX(this, 18))
        vu.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#"+colorText))
        vu.setBackgroundResource(R.drawable.shape_circle_white)

        val tv = createTextView(inputText, size, textColor, width, height)
        var lp3 = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp3.setMargins(DPtoPX(this, 10),0,0,0)
        tv.layoutParams = lp3
        la.addView(vu)
        la.addView(tv)

        la.requestLayout()

        return la
    }


}