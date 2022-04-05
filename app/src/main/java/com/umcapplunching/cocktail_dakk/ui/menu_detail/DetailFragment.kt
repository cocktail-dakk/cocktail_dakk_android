package com.umcapplunching.cocktail_dakk.ui.menu_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.CocktailDatabase
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_Islike
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_Rating
import com.umcapplunching.cocktail_dakk.databinding.ActivityMenuDetailBinding
import com.umcapplunching.cocktail_dakk.databinding.FragmentDetailBinding
import com.umcapplunching.cocktail_dakk.ui.BaseActivity
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.menu_detail.detailService.*
import com.umcapplunching.cocktail_dakk.ui.search.searchService.*
import com.umcapplunching.cocktail_dakk.ui.search_tab.SearchTabActivity
import com.umcapplunching.cocktail_dakk.ui.start.Service.TokenResfreshView
import com.umcapplunching.cocktail_dakk.ui.start.Service.Tokenrespbody
import com.umcapplunching.cocktail_dakk.ui.start.Service.UserService
import com.umcapplunching.cocktail_dakk.utils.getaccesstoken
import com.umcapplunching.cocktail_dakk.utils.getrefreshtoken
import com.umcapplunching.cocktail_dakk.utils.setaccesstoken
import com.umcapplunching.cocktail_dakk.utils.setrefreshtoken
import kotlin.math.abs

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate),
    DetailView, RatingView, SearchView, TokenResfreshView, IslikeView {

    // 단위 리스트. 나중에 다른 곳으로 옮길것
    private val unitList = arrayListOf("ml", "piece", "개", "필업")

    // 레시피 랜덤 색상 리스트. 나중에 다른 곳으로 옮길것
    private val colorList1 = arrayListOf("FF4668", "FCF5A4", "03EF9A", "A35BBF")
    private val colorList2 = arrayListOf("FF6363", "14D2D2", "208DC8", "C4A5E1")
    private val detailService = DetailService()
    private val searchService = SearchService()
    private val userService = UserService()

    private var ingredients: ArrayList<String> = ArrayList()
    private var keywords: ArrayList<String> = ArrayList()
    private var ratios: MutableList<Int> = ArrayList()
    private var colors: List<String> =
        (colorList1 as MutableList<String>).shuffled() + (colorList2 as MutableList<String>).shuffled()
    private var weights: MutableList<Float> = ArrayList()
    private var starPoint: Double = 0.0
    private var tempStarPoint: Int = -1

    lateinit var localName: String
    lateinit var englishName: String
    lateinit var imageURL: String
    var alcoholLevel: Int = 0
    lateinit var mixxing: String
    lateinit var getkeywords: String
    private lateinit var information: String
    lateinit var getingredients: String
    var cocktailInfoId: Int = 0
    private lateinit var method : String
    private var toolbarFlag = false

    override fun initAfterBinding() {
        cocktailInfoId = Integer.parseInt(arguments?.getString("CocktailId"))
        method = arguments?.getString("DetailMethod").toString()

        initClicker()
        setappbarlayout()

        binding.menuDetailMltoozCloseIv.setOnClickListener {
            binding.menuDetailMltoozLayout.visibility = View.GONE
            binding.menuDetailMltoozToolbar.visibility = View.GONE
        }
        binding.menuDetailMltoozLayout.setOnClickListener {
            binding.menuDetailMltoozLayout.visibility = View.GONE
            binding.menuDetailMltoozToolbar.visibility = View.GONE
        }
        binding.menuDetailMoreinfoIv.setOnClickListener {
            val animation: Animation = TranslateAnimation(0f, 0f, 500f, 0f)
            animation.duration = 300
            binding.menuDetailMltoozWhiteboard.animation = animation
            binding.menuDetailMltoozLayout.visibility = View.VISIBLE
            if (!toolbarFlag) {
                binding.menuDetailMltoozToolbar.visibility = View.VISIBLE
            }
        }

        detailService.setdetailView(this)
        detailService.setratingView(this)
        searchService.setsearchView(this)
        searchService.setislikeView(this)
        userService.settokenRefreshView(this)
        detailService.detail(getaccesstoken(requireContext()), cocktailInfoId)

    }

    private fun setappbarlayout() {
        binding.mainAppbarlayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.menuDetailNameLocalTv.setPadding(
                (abs(verticalOffset) / appBarLayout.totalScrollRange.toFloat() * 35).toInt(),
                0,
                0,
                (abs(verticalOffset) / appBarLayout.totalScrollRange.toFloat() * 15).toInt()
            )
            binding.menuDetailNameLocalTv.setTextSize(35 - (abs(verticalOffset) / appBarLayout.totalScrollRange.toFloat() * 14))
            binding.menuDetailNameEnglishTv.setTextSize(25 - (abs(verticalOffset) / appBarLayout.totalScrollRange.toFloat() * 25))

            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                //  Collapsed
                binding.menuDetailBigCocktailIv.visibility = View.GONE
//                binding.menuDetailMltoozToolbar.visibility = View.VISIBLE
                toolbarFlag = false
            } else {
                //Expanded
                binding.menuDetailEvaluateNameLocalTv.setPadding(0, 0, 0, 0)
                binding.menuDetailBigCocktailIv.visibility = View.VISIBLE
                toolbarFlag = true
//                binding.menuDetailMltoozToolbar.visibility = View.GONE
            }
        })
    }

    //디테일 화면로딩
    override fun onDetailLoading() {
    }

    override fun onDetailSuccess(result: detail_Cocktail) {

        val animation2: Animation = AlphaAnimation(1f, 0f)
        animation2.setDuration(300)
        binding.menuDetailOnloadingLayout.animation = animation2
        binding.menuDetailOnloadingLayout.visibility = View.GONE

        localName = result.koreanName
        englishName = result.englishName
        imageURL = result.nukkiImgUrl
        starPoint = result.ratingAvg
        binding.menuDetailGijuContextTv.setText(result.cocktailDrink[0].drinkName)

        //즐겨찾기
        val cocktaildb = CocktailDatabase.getInstance(requireContext())
        for (i in cocktaildb!!.IslikeDao().getcocktail()) {
            if (result.cocktailInfoId == i.isLikeId) {
                binding.menuDetailHeartoff.visibility = View.GONE
                binding.menuDetailHearton.visibility = View.VISIBLE
                break
            } else {
                binding.menuDetailHeartoff.visibility = View.VISIBLE
                binding.menuDetailHearton.visibility = View.GONE
            }
        }

        binding.menuDetailHeartoff.setOnClickListener {
            searchService.IsLike(getaccesstoken(requireContext()), result.cocktailInfoId)
            cocktaildb.IslikeDao().insert(Cocktail_Islike(result.cocktailInfoId))
            binding.menuDetailHeartoff.visibility = View.GONE
            binding.menuDetailHearton.visibility = View.VISIBLE

            var spf = requireContext().getSharedPreferences("lockerflag", AppCompatActivity.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = spf?.edit()!!
            editor.putInt("lockerflag", 0)
            editor.apply()

        }
        binding.menuDetailHearton.setOnClickListener {
            searchService.DisLike(getaccesstoken(requireContext()), result.cocktailInfoId)
            cocktaildb.IslikeDao().unlike(result.cocktailInfoId)
            binding.menuDetailHeartoff.visibility = View.VISIBLE
            binding.menuDetailHearton.visibility = View.GONE
            var spf = requireContext().getSharedPreferences("lockerflag", AppCompatActivity.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = spf?.edit()!!
            editor.putInt("lockerflag", 1)
            editor.apply()

        }

        alcoholLevel = result.alcoholLevel
        mixxing = result.cocktailMixingMethod[0].mixingMethodName
        getkeywords = ""
        for (i in 0..result.cocktailKeyword.size - 1) {
            getkeywords += result.cocktailKeyword[i].keywordName + ","
        }
        information = result.description
        getingredients = result.ingredient
        Glide.with(requireContext())
            .load(result.todayImgUrl)
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.detail_bg)
            .into(binding.menuDetailBackgroundIv)
        initCocktail()
        ratingreset()
    }

    fun ratingreset() {
        //기본
        binding.menuDetailStarEvaluateTv.text = "평가 하기"
        binding.menuDetailStarEvaluateTv.setOnClickListener {
            binding.menuDetailMltoozToolbar.visibility = View.VISIBLE
            binding.menuDetailEvaluateBackgroundLa.visibility = View.VISIBLE
            val animation2: Animation = AlphaAnimation(0f, 1f)
            animation2.setDuration(300)
            binding.menuDetailEvaluateBackgroundLa.animation = animation2

            binding.menuDetailEvaluateStar1Iv.setImageResource(R.drawable.star_off)
            binding.menuDetailEvaluateStar2Iv.setImageResource(R.drawable.star_off)
            binding.menuDetailEvaluateStar3Iv.setImageResource(R.drawable.star_off)
            binding.menuDetailEvaluateStar4Iv.setImageResource(R.drawable.star_off)
            binding.menuDetailEvaluateStar5Iv.setImageResource(R.drawable.star_off)
            binding.menuDetailEvaluateOkOffTv.visibility = View.VISIBLE
            binding.menuDetailEvaluateOkOnTv.visibility = View.INVISIBLE
        }

        val CocktailDB = CocktailDatabase.getInstance(requireContext())!!
        val ratinglist = CocktailDB.RatingDao().getcocktails()
        for (element in ratinglist) {
            if (element.cocktailinfoid == cocktailInfoId) {
                binding.menuDetailStarEvaluateTv.text = "평가 완료"
                binding.menuDetailStarEvaluateTv.setOnClickListener {
                    Toast.makeText(requireContext(), "이미 평가 하셨습니다!", Toast.LENGTH_SHORT).show()
                }
                break
            }
        }

        binding.menuDetailEvaluateOkOnTv.setOnClickListener() {
            detailService.rating(
                getaccesstoken(requireContext()),
                DetailRequest(cocktailInfoId, tempStarPoint)
            )
        }
    }


    override fun onDetailFailure(code: Int, message: String) {
        if (method == "Main"){
            (activity as MainActivity).DetailBackArrow()
        }
        if (method == "SearchTab"){
            (activity as SearchTabActivity).DetailBackArrowInSearchtab()
        }
        Toast.makeText(requireContext(),"오류가 발생했습니다.",Toast.LENGTH_SHORT).show()
    }

    private fun initClicker() {

        binding.menuDetailBackIv.setOnClickListener {
            if (method == "Main"){
                (activity as MainActivity).DetailBackArrow()
            }
            if (method == "SearchTab"){
                (activity as SearchTabActivity).DetailBackArrowInSearchtab()
            }
        }

        binding.menuDetailStarEvaluateTv.setOnClickListener {
            binding.menuDetailMltoozToolbar.visibility = View.VISIBLE
            binding.menuDetailEvaluateBackgroundLa.visibility = View.VISIBLE
            val animation2: Animation = AlphaAnimation(0f, 1f)
            animation2.duration = 300
            binding.menuDetailEvaluateBackgroundLa.animation = animation2

//            if (starPoint != 0) {clickStar(starPoint)} else {
            binding.menuDetailEvaluateStar1Iv.setImageResource(R.drawable.star_off)
            binding.menuDetailEvaluateStar2Iv.setImageResource(R.drawable.star_off)
            binding.menuDetailEvaluateStar3Iv.setImageResource(R.drawable.star_off)
            binding.menuDetailEvaluateStar4Iv.setImageResource(R.drawable.star_off)
            binding.menuDetailEvaluateStar5Iv.setImageResource(R.drawable.star_off)
            binding.menuDetailEvaluateOkOffTv.visibility = View.VISIBLE
            binding.menuDetailEvaluateOkOnTv.visibility = View.INVISIBLE
//            }
        }

        // 평가하기
        binding.menuDetailEvaluateWhiteboardLa.setOnClickListener {
            // 아무것도 안함. 배경 클릭과의 대비를 두기 위한 코드. 지우지 말것!
        }

        binding.menuDetailEvaluateBackgroundLa.setOnClickListener {
            binding.menuDetailMltoozToolbar.visibility = View.INVISIBLE
            binding.menuDetailEvaluateBackgroundLa.visibility = View.GONE
        }
        binding.menuDetailEvaluateExitIv.setOnClickListener {
            binding.menuDetailMltoozToolbar.visibility = View.GONE
            binding.menuDetailEvaluateBackgroundLa.visibility = View.GONE
        }

        binding.menuDetailEvaluateStar1Iv.setOnClickListener {
            tempStarPoint = 1
            clickStar(1.0)
        }
        binding.menuDetailEvaluateStar2Iv.setOnClickListener {
            tempStarPoint = 2
            clickStar(2.0)
        }
        binding.menuDetailEvaluateStar3Iv.setOnClickListener {
            tempStarPoint = 3
            clickStar(3.0)
        }
        binding.menuDetailEvaluateStar4Iv.setOnClickListener {
            tempStarPoint = 4
            clickStar(4.0)
        }
        binding.menuDetailEvaluateStar5Iv.setOnClickListener {
            tempStarPoint = 5
            clickStar(5.0)
        }

        binding.menuDetailEvaluateOkOffTv.setOnClickListener {
            Toast.makeText(requireContext(), "별점을 평가해 주세요.", Toast.LENGTH_SHORT).show()
        }
        binding.menuDetailEvaluateOkOnTv.setOnClickListener {
            val CocktailDB = CocktailDatabase.getInstance(requireContext())!!
            CocktailDB.RatingDao().insert(Cocktail_Rating(cocktailInfoId))
            binding.menuDetailStarEvaluateTv.text = "평가 완료"
            binding.menuDetailStarEvaluateTv.setOnClickListener {
                Toast.makeText(requireContext(), "이미 평가 하셨습니다!", Toast.LENGTH_SHORT).show()
            }
//            detailService.rating(DetailRequest(cocktailInfoId,getUser(this).deviceNum,tempStarPoint))
            binding.menuDetailMltoozToolbar.visibility = View.GONE
            binding.menuDetailEvaluateBackgroundLa.visibility = View.GONE
        }
    }


    @SuppressLint("SetTextI18n")
    private fun initCocktail() {
        // local 이름, english 이름, image 넣기
        binding.menuDetailNameLocalTv.text = localName
        binding.menuDetailNameEnglishTv.text = englishName
        Glide.with(this)
            .load(imageURL)
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.img_cocktail_alaskaicedtea_dailyrec)
            .into(binding.menuDetailBigCocktailIv)

        // 별점 넣기, 도수 넣기
        initStarPoint(
            starPoint,
            binding.menuDetailStarContext1Iv,
            binding.menuDetailStarContext2Iv,
            binding.menuDetailStarContext3Iv,
            binding.menuDetailStarContext4Iv,
            binding.menuDetailStarContext5Iv
        )
        binding.menuDetailAlcoholLevelContextTv.text = "$alcoholLevel 도"

        // 평가하기 창 클릭시 이름들 넣기
        binding.menuDetailEvaluateNameLocalTv.text = localName
        binding.menuDetailEvaluateNameEnglishTv.text = englishName
        binding.menuDetailEvaluateGuideTv.text = localName + "에 대한 별점을 평가해 주세요."

        // 키워드 넣기
        initKeywords(getkeywords)
        val l1 = binding.menuDetailKeywordsContextFb
        l1.removeAllViews()
        for (i in 0 until keywords.size - 1) {
            l1.addView(createKeyword(keywords[i], 14.0f, "000000", 60))
            val vu = View(requireContext())
//            val layoutparam = LinearLayout.LayoutParams(DPtoPX(requireContext(), 10), 0)
            val layoutparam = LinearLayout.LayoutParams(DPtoPX(requireContext(), 10), 0)
            layoutparam.setMargins(0, 80, 0, 0)
            vu.layoutParams = layoutparam
            l1.addView(vu)
        }

        // 정보 넣기
        binding.menuDetailCocktailInformationContextTv.text = information

        // 재료와 비율 넣기
        binding.menuDetailIngredientsContextLa.removeAllViews()
        initIngredientsAndRatio(getingredients)
        for (ing in ingredients) {
            binding.menuDetailIngredientsContextLa.addView(createTextView(ing, 14.5f, "000000"))
            binding.menuDetailIngredientsContextLa.addView(createTextView("", 0f, "000000", 10, 13))
        }

        binding.menuDetailRecipeContextLa.removeAllViews()
        for (i in 0 until ingredients.size) {
            binding.menuDetailRecipeContextLa.addView(
                createIngredientWithColor(
                    colors[i],
                    ingredients[i],
                    13.0f,
                    "FFFFFF"
                )
            )
            binding.menuDetailRecipeContextLa.addView(createTextView("", 0f, "000000", 5, 10))
        }

        // 재료 비율 시각화와 색깔지정
        var ratioSum = 0
        var underFourCount = 0
        for (i in 0 until ratios.size) {
            if (ratios[i] > 4) {
                ratioSum += ratios[i]
            } else {
                underFourCount += 1
            }
        }
        for (i in 0 until ratios.size) {
            if (ratios[i] <= 4) {
                weights.add(4.0f)
            } else {
                weights.add(((150 - underFourCount * 4) * ratios[i] / ratioSum).toFloat())
            }
        }

        binding.menuDetailRecipeRatioLa.removeAllViews()
        for (i in 0 until ratios.size) {
            binding.menuDetailRecipeRatioLa.addView(createViewWithWeight(colors[i], weights[i]))
            binding.menuDetailRecipeRatioLa.addView(createViewWithHeight(3))
        }
        binding.menuDetailRecipeRatioLa.requestLayout()

    }

    private fun initStarPoint(
        starPoint: Double,
        star_1: ImageView,
        star_2: ImageView,
        star_3: ImageView,
        star_4: ImageView,
        star_5: ImageView
    ) {

        // 별점
        // 0.5 단위로 "버림" 연산
        // 예) 5.0 -> 5  //  4.8 -> 4.5  // 4.4 -> 4  // 2.1 -> 2
        // 0.0점~0.99점 까지는 예외적으로 0.5 를 줬음. (하나도 안 채워져 있으면 이상해보여서)

        val starEmpty: Int = R.mipmap.icon_star_off
        val starFull: Int = R.mipmap.icon_star_on
        val starHalf: Int = R.mipmap.icon_star_half

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

    private fun initIngredientsAndRatio(inputIngredients: String) {
        // ingredients
        ingredients = inputIngredients.split(",") as ArrayList<String>
        for (i in 0 until ingredients.size) {
            ingredients[i] = ingredients[i].trim()//공백제거
        }
        ingredients.reverse()

        // ratios
        ratios.clear()
        ratios = ArrayList()

        for (ing in ingredients) {
            var unitCount = 0
            var unitVal = 0

            while (unitCount < 4) {
                val unitIdx = ing.lastIndexOf(unitList[unitCount])
                if (unitIdx == -1) {
                    unitCount++
                } else {
                    unitVal = if (unitCount == 3) { // 필업인 경우 고정값
                        70
                    } else { // 단위 앞의 숫자를 unitVal에 찾아 넣기
                        var startIdx = unitIdx - 1
                        while (startIdx >= 0) {
                            val temp = Character.getNumericValue(ing[startIdx])
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
        keywords = ArrayList()
        keywords = inputKeywords.split(",") as ArrayList<String>
        for (i in 0 until keywords.size) {
            keywords[i] = keywords[i].trim()
        }
    }

    private fun createTextView(
        inputText: String,
        size: Float,
        color: String,
        width: Int = -1,
        height: Int = -1
    ): TextView {
        val textView = TextView(requireContext())
        //oz ml 변환
        var unitCount = 0
        var unitVal = 0
        while (unitCount < 4) {
            val unitIdx = inputText.lastIndexOf(unitList[unitCount])
            if (unitIdx == -1) {
                unitCount++
            } else {
                if (unitCount == 3) { // 필업인 경우 고정값
//                    Log.d("test",inputText)
//                    textView.text = inputText
                } else { // 단위 앞의 숫자를 unitVal에 찾아 넣기
                    var startIdx = unitIdx - 1
                    while (startIdx >= 0) {
                        val temp = Character.getNumericValue(inputText[startIdx])
                        if (temp == -1) {
                            startIdx++
                            break
                        }
                        startIdx--
                    }
                    inputText.substring(startIdx until unitIdx).toInt()
                    var quanti = 0
                    var oz = "ml"
                    if (inputText.subSequence(unitIdx until inputText.length) == "ml") {
                        oz = "oz"
                        if (inputText.substring(startIdx until unitIdx).toInt()<30) {
                            quanti = (inputText.substring(startIdx until unitIdx).toInt() / 30)
                        }else {
                            quanti = (inputText.substring(startIdx until unitIdx).toInt() / 30)
                        }
                    }else{
                        oz =inputText.subSequence(unitIdx until inputText.length).toString()
                        quanti = (inputText.substring(startIdx until unitIdx).toInt())
                    }
//                    Log.d("test",inputText.substring(0 until startIdx) +
//                            quanti.toString()+oz)
//                    textView.text = inputText.substring(0 until startIdx) + quanti.toString()+oz
                }
                break
            }
        }

        textView.text = inputText
        textView.textSize = size
        textView.setTextColor(Color.parseColor("#$color"))
        val lp =
            if (width == -1 && height == -1) LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            else LinearLayout.LayoutParams(
                DPtoPX(requireContext(), width),
                DPtoPX(requireContext(), height)
            )
        textView.layoutParams = lp
        return textView
    }

    private fun createKeyword(
        inputText: String,
        size: Float,
        color: String,
        width: Int = -1,
        height: Int = -1
    ): TextView {
        val textView = TextView(requireContext())
        textView.text = inputText
        textView.textSize = size
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setBackgroundResource(R.drawable.round_rect_white_in_sky)
        textView.setTextColor(Color.parseColor("#$color"))
        textView.setPadding(DPtoPX(requireContext(), 10),
            DPtoPX(requireContext(), 2), DPtoPX(requireContext(), 10), DPtoPX(requireContext(), 2))
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.layoutParams = lp

        return textView
    }

    private fun DPtoPX(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    private fun createViewWithWeight(colorText: String, inputWeight: Float): View {
        val vu = View(requireContext())
        vu.setBackgroundResource(R.drawable.shape_rect_white)
        vu.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#$colorText"))

        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            DPtoPX(requireContext(), 1)
        ).apply {
            weight = inputWeight
        }
        vu.layoutParams = lp
        return vu
    }

    private fun createViewWithHeight(inputHeight: Int): View {
        val vu = View(requireContext())
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            DPtoPX(requireContext(), inputHeight)
        )
        vu.layoutParams = lp
        return vu
    }

    private fun createIngredientWithColor(
        colorText: String,
        inputText: String,
        size: Float,
        textColor: String,
        width: Int = -1,
        height: Int = -1
    ): LinearLayout {
        val la = LinearLayout(requireContext())
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        la.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT or Gravity.FILL_HORIZONTAL
        la.orientation = LinearLayout.HORIZONTAL
        la.layoutParams = lp

        val vu = View(requireContext())
        vu.layoutParams =
            LinearLayout.LayoutParams(DPtoPX(requireContext(), 18), DPtoPX(requireContext(), 18))
        vu.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#$colorText"))
        vu.setBackgroundResource(R.drawable.shape_circle_white)

        val tv = createTextView(inputText, size, textColor, width, height)
        val lp3 = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp3.setMargins(DPtoPX(requireContext(), 10), 0, 0, 0)
        tv.layoutParams = lp3
        la.addView(vu)
        la.addView(tv)
        la.requestLayout()

        return la
    }

    private fun clickStar(point: Double) {
        val full = R.mipmap.icon_star_on
        val empty = R.mipmap.icon_star_off

        when (point) {
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

    //레이팅
    override fun onRatingLoading() {
    }

    override fun onRatingSuccess(result: ratingResponse) {
        val CocktailDB = CocktailDatabase.getInstance(requireContext())!!
        CocktailDB.RatingDao().insert(Cocktail_Rating(cocktailInfoId))
        binding.menuDetailStarEvaluateTv.text = "평가 완료"
        binding.menuDetailStarEvaluateTv.setOnClickListener {
            Toast.makeText(requireContext(), "이미 평가 하셨습니다!", Toast.LENGTH_SHORT).show()
        }
        binding.menuDetailMltoozToolbar.visibility = View.GONE
        binding.menuDetailEvaluateBackgroundLa.visibility = View.GONE
        Toast.makeText(requireContext(), "별점 ${tempStarPoint}점을 기록했습니다.", Toast.LENGTH_SHORT).show()

    }

    override fun onRatingFailure(code: Int, message: String) {
        val CocktailDB = CocktailDatabase.getInstance(requireContext())!!
        CocktailDB.RatingDao().insert(Cocktail_Rating(cocktailInfoId))
        binding.menuDetailStarEvaluateTv.text = "평가 완료"
        binding.menuDetailStarEvaluateTv.setOnClickListener {
            Toast.makeText(requireContext(), "이미 평가 하셨습니다!", Toast.LENGTH_SHORT).show()
        }
        binding.menuDetailMltoozToolbar.visibility = View.GONE
        binding.menuDetailEvaluateBackgroundLa.visibility = View.GONE
        Toast.makeText(requireContext(), "이미 별점 등록을 하셨어요!", Toast.LENGTH_SHORT).show()
    }

    override fun onSearchLoading() {
    }

    override fun onSearchSuccess(searchresult: SearchResult) {

    }

    override fun onSearchFailure(code: Int, message: String) {
        if (code == 5000) {
            userService.TokenRefresh(getrefreshtoken(requireContext()))
        }

    }

    override fun onTokenRefreshLoading() {
    }

    override fun onTokenRefreshSuccess(tokenSigninbody: Tokenrespbody) {
        setaccesstoken(requireContext(), tokenSigninbody.token)
        setrefreshtoken(requireContext(), tokenSigninbody.refreshToken)
        onStart()
    }

    override fun onTokenRefreshFailure(code: Int, message: String) {
    }

    override fun onIsLikeLoading() {
    }

    override fun onIsLikeSuccess(isLikeResponse: IsLikeResponse) {
    }

    override fun onIsLikeFailure(code: Int, message: String) {
        Toast.makeText(requireContext(), "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
    }

}
