package com.example.cocktail_dakk.ui.main

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.UserInfo
import com.example.cocktail_dakk.databinding.ActivityMainBinding
import com.example.cocktail_dakk.ui.BaseActivity
import com.example.cocktail_dakk.ui.search.SearchFragment
import hearsilent.discreteslider.DiscreteSlider
import kotlin.collections.ArrayList
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cocktail_dakk.data.entities.cocktaildata_db.CocktailDatabase
import com.example.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_Rating
import com.example.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_recentSearch
import com.example.cocktail_dakk.data.entities.getUser
import com.example.cocktail_dakk.ui.menu_detail.detailService.*
import com.example.cocktail_dakk.ui.mypage.MypageResettingDosuFragment
import com.example.cocktail_dakk.ui.mypage.MypageResettingGijuFragment
import com.example.cocktail_dakk.ui.mypage.MypageResettingKeywordFragment


class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), DetailView, RatingView {
    private lateinit var navHostFragment: NavHostFragment
    val detailService = DetailService()

    // 유저 변수에 저장 할 것
    private var mypageDosu:Int = 0
    private var mypageTempDosu: Int = 0
    private var mypageGijulist = ArrayList<String>()
    private var mypageTempGijulist = ArrayList<String>()
    private var mypageKeywords = ArrayList<String>()
    private var mypageTempKeywords = ArrayList<String>()
    private val threeFragments = arrayListOf<Fragment>(MypageResettingDosuFragment(), MypageResettingGijuFragment(), MypageResettingKeywordFragment())
    

    fun clearThree(){
        for (i in 0 until threeFragments.size) {supportFragmentManager.beginTransaction().remove(threeFragments[i])
            .commitAllowingStateLoss()
            // threeFragments.clear()
        }
        // supportFragmentManager.findFragmentById
    }

    fun getMypageDosu():Int = mypageDosu
    fun setMypageDosu(dosu : Int) {
        mypageDosu = dosu
    }
    fun getMypageTempDosu():Int = mypageTempDosu
    fun setMypageTempDosu(dosu : Int) {
        mypageTempDosu = dosu
    }

    // arrayList 들은 get 해올때 반드시 addall 등 "깊은 복사"를 할것! deep copy 하지 않으면 공통 참조가 됨
    fun getMypageGijulist():ArrayList<String> = mypageGijulist
    fun setMypageGijulist(gijulist : ArrayList<String>) {
        mypageGijulist = gijulist
    }
    fun getMypageTempGijulist():ArrayList<String> = mypageTempGijulist
    fun setMypageTempGijulist(gijulist : ArrayList<String>) {
        mypageTempGijulist = gijulist
    }

    fun getMypageKeywords():ArrayList<String> = mypageKeywords
    fun setMypageKeywords(keywords : ArrayList<String>) {
        mypageKeywords = keywords
    }
    fun getMypageTempKeywords():ArrayList<String> = mypageTempKeywords
    fun setMypageTempKeywords(keywords : ArrayList<String>) {
        mypageTempKeywords = keywords
    }

    // hide keyboard 는 mypage로 옮김
    
    fun showKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        view.requestFocus()
        inputMethodManager.showSoftInput(view, 0)
    }


    override fun initAfterBinding() {
        setBottomNavigation()
        initClicker()
        detailService.setdetailView(this)
        detailService.setratingView(this)
    }

    fun showbottomnavation() {
        var animation : Animation = AlphaAnimation(0f,1f);
        animation.setDuration(500)
        binding.mainBottomNavigation.animation = animation
        binding.mainBottomNavigation.visibility = View.VISIBLE
    }
    fun hidebottomnavation(){
        binding.mainBottomNavigation.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        changeSearchtab()
    }


    fun changeSearchtab(){
        var spf = getSharedPreferences("currenttab", MODE_PRIVATE)
        if (spf.getInt("currenttab",0) == 0){
            binding.mainBottomNavigation.selectedItemId = R.id.searchFragment
        }
        else if (spf.getInt("currenttab",0) == 1){
            binding.mainBottomNavigation.selectedItemId = R.id.homeFragment
        }
    }

    fun changetoSearchtab(){
        binding.mainBottomNavigation.selectedItemId = R.id.searchFragment
    }

    private fun setBottomNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()
        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null

        binding.navHostFragmentContainer.isSaveEnabled = false
    }


    override fun onDestroy() {
        super.onDestroy()
        SetCurrentpageMain()
    }

    private fun SetCurrentpageMain() {
        var spf = getSharedPreferences("currenttab", MODE_PRIVATE)
        var editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab", 1)
        editor.apply()
    }



//    디테일-------------------------------------------------------------------------------------------

    // 단위 리스트. 나중에 다른 곳으로 옮길것
    private val unitList = arrayListOf("ml", "piece", "개", "필업")
    // 레시피 랜덤 색상 리스트. 나중에 다른 곳으로 옮길것
    private val colorList1 = arrayListOf("FF4668", "FCF5A4","03EF9A","A35BBF")
    private val colorList2 = arrayListOf("FF6363", "14D2D2", "208DC8", "C4A5E1")

    private var ingredients : ArrayList<String> = ArrayList()
    private var keywords : ArrayList<String> = ArrayList()
    private var ratios : MutableList<Int> = ArrayList()
    private var colors : List<String> = (colorList1 as MutableList<String>).shuffled() + (colorList2 as MutableList<String>).shuffled()
    private var weights : MutableList<Float> = ArrayList()
    private var starPoint: Double = 0.0
    private var tempStarPoint: Int = -1

    lateinit var localName : String
    lateinit var englishName : String
    lateinit var imageURL : String
    //    private var starPoint: Double = 0.0
    var alcoholLevel : Int = 0
    lateinit var mixxing : String
    lateinit var getkeywords : String
    lateinit var informationdetail : String
    lateinit var getingredients : String
    var cocktailInfoId : Int = 0
    var backflag = false

    private var backKeyPressedTime: Long = 0
    lateinit var toast: Toast

    private var mypageReStatus: Boolean = false// false:기본, true:mypage닉네임or정보 설정창on상태
    fun setMypageReStatus(restatus: Boolean){
        mypageReStatus = restatus
    }

    override fun onBackPressed() {
        if (!mypageReStatus) {
            if(backflag){
                var animation2 : Animation = AlphaAnimation(1f,0f);
                animation2.setDuration(300)
                binding.searchDetailBack.animation = animation2
                binding.searchDetailBack.visibility = View.GONE
                binding.menuDetailBigCocktailIv.animation = animation2
                binding.menuDetailBigCocktailIv.visibility = View.INVISIBLE
                showbottomnavation()
                backflag = false
                return
            }
            if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
                backKeyPressedTime = System.currentTimeMillis();
                toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG)
                toast.show()
                return
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
                finish()
                toast.cancel()
//            toast = Toast.makeText(this,"이용해 주셔서 감사합니다.",Toast.LENGTH_LONG)
//            toast.show();
            }
        }
        else {
            super.onBackPressed() //mypage fragment 에서 설정창 incisible 하게
            mypageReStatus = false
        }
    }


    fun resetdetail(){
        ingredients = ArrayList()
        keywords = ArrayList()
        ratios = ArrayList()
        weights = ArrayList()
    }

    fun detailcocktail(id : Int){
        detailService.detail(id)
        cocktailInfoId = id
        backflag = true
        var animation : Animation = AlphaAnimation(0f,1f);
        animation.setDuration(700)
        binding.searchDetailBack.animation = animation
        binding.searchDetailBack.visibility = View.VISIBLE
        binding.mainDetailScrollview.scrollTo(0,0)
        hidebottomnavation()
    }

    override fun onDetailLoading() {
    }

    override fun onDetailSuccess(result: detail_Cocktail) {
        resetdetail()

        localName = result.koreanName
        englishName = result.englishName
        imageURL = result.nukkiImgUrl
        starPoint = result.ratingAvg

        alcoholLevel = result.alcoholLevel
        mixxing = result.cocktailMixingMethod[0].mixingMethodName
        getkeywords = ""
        for(i in 0..result.cocktailKeyword.size-1){
            getkeywords += result.cocktailKeyword[i].keywordName + ","
        }
        informationdetail = result.description
        getingredients = result.ingredient
        Glide.with(this)
            .load(result.todayImgUrl)
            .thumbnail(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.menuDetailBackgroundIv)

        var animation2 : Animation = AlphaAnimation(0f,1f);
        animation2.setDuration(300)
        binding.menuDetailBackgroundIv.animation = animation2

        Glide.with(this)
            .load(imageURL)
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.img_cocktail_alaskaicedtea_dailyrec)
            .into(binding.menuDetailBigCocktailIv)
        binding.menuDetailBigCocktailIv.animation = animation2
        binding.menuDetailBigCocktailIv.visibility = View.VISIBLE

        initCocktail()
        ratingreset()

    }

    override fun onDetailFailure(code: Int, message: String) {
    }

    fun ratingreset(){

        //기본
        binding.menuDetailStarEvaluateTv.text = "평가 하기"
        binding.menuDetailStarEvaluateTv.setOnClickListener(){
            binding.menuDetailEvaluateBackgroundLa.visibility = View.VISIBLE
            var animation2 : Animation = AlphaAnimation(0f,1f);
            animation2.setDuration(300)
            binding.menuDetailEvaluateBackgroundLa.animation = animation2

//            if (starPoint != 0.0) {
//                clickStar(starPoint)}
//            else {
                binding.menuDetailEvaluateStar1Iv.setImageResource(R.drawable.star_off)
                binding.menuDetailEvaluateStar2Iv.setImageResource(R.drawable.star_off)
                binding.menuDetailEvaluateStar3Iv.setImageResource(R.drawable.star_off)
                binding.menuDetailEvaluateStar4Iv.setImageResource(R.drawable.star_off)
                binding.menuDetailEvaluateStar5Iv.setImageResource(R.drawable.star_off)
                binding.menuDetailEvaluateOkOffTv.visibility = View.VISIBLE
                binding.menuDetailEvaluateOkOnTv.visibility = View.INVISIBLE
//            }
        }

        var CocktailDB = CocktailDatabase.getInstance(this)!!
        val ratinglist = CocktailDB.RatingDao().getcocktails()
        for(i in 0..ratinglist.size-1){
            if (ratinglist[i].cocktailinfoid == cocktailInfoId){
                binding.menuDetailStarEvaluateTv.text = "평가 완료"
                binding.menuDetailStarEvaluateTv.setOnClickListener {
                    Toast.makeText(this,"이미 평가 하셨습니다!",Toast.LENGTH_SHORT).show()
                }
                break
            }
        }

        binding.menuDetailEvaluateOkOnTv.setOnClickListener(){
            var CocktailDB = CocktailDatabase.getInstance(this)!!
            CocktailDB.RatingDao().insert(Cocktail_Rating(cocktailInfoId))
            binding.menuDetailStarEvaluateTv.text = "평가 완료"
            binding.menuDetailStarEvaluateTv.setOnClickListener {
                Toast.makeText(this,"이미 평가 하셨습니다!",Toast.LENGTH_SHORT).show()
            }
            detailService.rating(DetailRequest(cocktailInfoId,getUser(this).deviceNum,tempStarPoint))
            binding.menuDetailEvaluateBackgroundLa.visibility = View.GONE
        }
    }
    //레이팅
    override fun onRatingLoading() {
    }

    override fun onRatingSuccess(result: ratingResponse) {
        Toast.makeText(this, "별점 ${tempStarPoint}점을 기록했습니다.", Toast.LENGTH_SHORT).show()

    }

    override fun onRatingFailure(code: Int, message: String) {
        Toast.makeText(this, "별점 등록을 실패했어요!", Toast.LENGTH_SHORT).show()

    }


    private fun initClicker(){

        binding.menuDetailBackIv.setOnClickListener(){
            var animation2 : Animation = AlphaAnimation(1f,0f);
            animation2.setDuration(300)
            binding.searchDetailBack.animation = animation2
            binding.searchDetailBack.visibility = View.GONE
            binding.menuDetailBigCocktailIv.animation = animation2
            binding.menuDetailBigCocktailIv.visibility = View.INVISIBLE
            backflag = false
            showbottomnavation()
        }

//        binding.menuDetailStarEvaluateTv.setOnClickListener(){
//            binding.menuDetailEvaluateBackgroundLa.visibility = View.VISIBLE
//            var animation2 : Animation = AlphaAnimation(0f,1f);
//            animation2.setDuration(300)
//            binding.menuDetailEvaluateBackgroundLa.animation = animation2
//
//            if (starPoint != 0) {clickStar(starPoint)} else {
                binding.menuDetailEvaluateStar1Iv.setImageResource(R.drawable.star_off)
                binding.menuDetailEvaluateStar2Iv.setImageResource(R.drawable.star_off)
                binding.menuDetailEvaluateStar3Iv.setImageResource(R.drawable.star_off)
                binding.menuDetailEvaluateStar4Iv.setImageResource(R.drawable.star_off)
                binding.menuDetailEvaluateStar5Iv.setImageResource(R.drawable.star_off)
                binding.menuDetailEvaluateOkOffTv.visibility = View.VISIBLE
                binding.menuDetailEvaluateOkOnTv.visibility = View.INVISIBLE
//            }
//        }



        // 평가하기
        binding.menuDetailEvaluateWhiteboardLa.setOnClickListener(){
            // 아무것도 안함. 배경 클릭과의 대비를 두기 위한 코드. 지우지 말것!
        }

        binding.menuDetailEvaluateBackgroundLa.setOnClickListener(){
            binding.menuDetailEvaluateBackgroundLa.visibility = View.GONE
        }
        binding.menuDetailEvaluateExitIv.setOnClickListener(){
            binding.menuDetailEvaluateBackgroundLa.visibility = View.GONE
        }

        binding.menuDetailEvaluateStar1Iv.setOnClickListener(){
            tempStarPoint = 1
            clickStar(1.0)
        }
        binding.menuDetailEvaluateStar2Iv.setOnClickListener(){
            tempStarPoint = 2
            clickStar(2.0)
        }
        binding.menuDetailEvaluateStar3Iv.setOnClickListener(){
            tempStarPoint = 3
            clickStar(3.0)
        }
        binding.menuDetailEvaluateStar4Iv.setOnClickListener(){
            tempStarPoint = 4
            clickStar(4.0)
        }
        binding.menuDetailEvaluateStar5Iv.setOnClickListener(){
            tempStarPoint = 5
            clickStar(5.0)
        }

        binding.menuDetailEvaluateOkOffTv.setOnClickListener(){
            Toast.makeText(this, "별점을 평가해 주세요.", Toast.LENGTH_SHORT).show()
        }

        // 평가하기 //
    }




    private fun initCocktail(){
        // local 이름, english 이름, image 넣기
        binding.menuDetailNameLocalTv.text = localName
        binding.menuDetailNameEnglishTv.text = englishName


        binding.menuDetailStarContext1Iv.setImageResource(R.mipmap.icon_star_off)
        binding.menuDetailStarContext2Iv.setImageResource(R.mipmap.icon_star_off)
        binding.menuDetailStarContext3Iv.setImageResource(R.mipmap.icon_star_off)
        binding.menuDetailStarContext4Iv.setImageResource(R.mipmap.icon_star_off)
        binding.menuDetailStarContext5Iv.setImageResource(R.mipmap.icon_star_off)
        // 별점 넣기, 도수 넣기
        initStarPoint(
            starPoint,
            binding.menuDetailStarContext1Iv,
            binding.menuDetailStarContext2Iv,
            binding.menuDetailStarContext3Iv,
            binding.menuDetailStarContext4Iv,
            binding.menuDetailStarContext5Iv
        )
        binding.menuDetailAlcoholLevelContextTv.text = alcoholLevel.toString() + " 도"

        // 평가하기 창 클릭시 이름들 넣기
        binding.menuDetailEvaluateNameLocalTv.text =localName
        binding.menuDetailEvaluateNameEnglishTv.text = englishName
        binding.menuDetailEvaluateGuideTv.text = localName+"에 대한 별점을 평가해 주세요."

        // 키워드 넣기
        initKeywords(getkeywords)

        val l1 = binding.menuDetailKeywordsContextFb
        l1.removeAllViews()
        for (i in 0 until keywords.size-1){
            l1.addView(createKeyword(keywords[i], 12.0f, "000000", 60))
            val vu = View(this)
            var layoutparam = LinearLayout.LayoutParams(DPtoPX(this,10), 0)
            layoutparam.setMargins(0,80,0,0)
            vu.layoutParams = layoutparam
            l1.addView(vu)
        }

        // 정보 넣기
        binding.menuDetailCocktailInformationContextTv.text = informationdetail

        // 재료와 비율 넣기
        binding.menuDetailIngredientsContextLa.removeAllViews()
        initIngredientsAndRatio(getingredients)
        for (ing in ingredients) {
            binding.menuDetailIngredientsContextLa.addView(createTextView(ing, 14.5f, "000000"))
            binding.menuDetailIngredientsContextLa.addView(createTextView("", 0f,"000000",10,13))
        }

        binding.menuDetailRecipeContextLa.removeAllViews()
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
            if (ratios[i]<=4) {
                weights.add(4.0f)
            } else {
                weights.add( ((150 - underFourCount*4) * ratios[i]/ratioSum).toFloat())
            }
        }

        binding.menuDetailRecipeRatioLa.removeAllViews()
        for (i in 0 until ratios.size) {
            binding.menuDetailRecipeRatioLa.addView(createViewWithWeight(colors[i], weights[i]))
            binding.menuDetailRecipeRatioLa.addView(createViewWithHeight(4))
        }
        binding.menuDetailRecipeRatioLa.requestLayout()

    }

    private fun initStarPoint(starPoint: Double, star_1: ImageView, star_2: ImageView, star_3: ImageView, star_4: ImageView, star_5: ImageView){

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

    private fun initIngredientsAndRatio(inputIngredients: String){
        // ingredients
        ingredients = inputIngredients.split(",") as ArrayList<String>
        for (i in 0 until ingredients.size){
            ingredients[i] = ingredients[i].trim()//공백제거
        }
        ingredients.reverse()

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
        keywords = inputKeywords.split(",") as ArrayList<String>
        for (i in 0 until keywords.size) {
            keywords[i] = keywords[i].trim()
        }
    }

    private fun createTextView(inputText : String, size: Float, color: String, width: Int = -1, height: Int = -1) : TextView {
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

    private fun createKeyword(inputText : String, size: Float, color: String, width: Int = -1, height: Int = -1) : TextView {
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

    private fun createIngredientWithColor(colorText:String, inputText : String, size: Float, textColor: String, width: Int = -1, height: Int = -1): LinearLayout {
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


