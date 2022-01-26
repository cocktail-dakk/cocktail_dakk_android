package com.example.cocktail_dakk.ui.main.keyword

import android.content.Intent
import android.graphics.Color
import android.util.Log
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail
import com.example.cocktail_dakk.data.entities.CocktailKeywords
import com.example.cocktail_dakk.databinding.FragmentKeywordrecommandBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.home_detail.HomeDetailActivity
import com.example.cocktail_dakk.ui.main.keyword.todayrec.Dailyrecservice.TodayrecService
import com.example.cocktail_dakk.ui.main.keyword.todayrec.Dailyrecservice.TodayrecView
import com.example.cocktail_dakk.ui.main.keyword.todayrec.TodayCocktailViewpagerAdapter
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecService


class KeywordrecommandFragment : BaseFragment<FragmentKeywordrecommandBinding>(FragmentKeywordrecommandBinding::inflate),
    TodayrecView {

//    lateinit var cocktailkeywordlist : ArrayList<CocktailKeywords>
    lateinit var cocktail : CocktailKeywords


        override fun initAfterBinding() {
        SetDummyData()
    }

    private fun SetDummyData() {
        val Cocktaillist: ArrayList<Cocktail> = ArrayList()
        // 칵테일 더미데이터

        Cocktaillist.add(Cocktail("칵테일1", "CockTail_1", R.drawable.img_cocktail_b_52))
        Cocktaillist.add(Cocktail("칵테일2", "CockTail_2", R.drawable.img_cocktail_21century))
        Cocktaillist.add(Cocktail("칵테일3", "CockTail_3", R.drawable.img_cocktail_brandysour))
        Cocktaillist.add(Cocktail("칵테일4", "CockTail_4", R.drawable.img_cocktail_woowoo))
        Cocktaillist.add(Cocktail("칵테일5", "CockTail_5", R.drawable.img_cocktail_alaskaicedtea))
        Cocktaillist.add(Cocktail("칵테일5", "CockTail_5", R.drawable.img_cocktail_brandysour))
        Cocktaillist.add(Cocktail("칵테일5", "CockTail_5", R.drawable.img_cocktail_21century))
        val cockRecommandRvAdapter = CockRecommandRvAdapter(Cocktaillist)
        binding.mainKeywordrecRv1.adapter = cockRecommandRvAdapter


        val cockRecommandRvAdapter2 = CockRecommandRvAdapter(Cocktaillist)
        binding.mainKeywordrecRv2.adapter = cockRecommandRvAdapter2
        binding.mainKeywordrecThemecock1MoreIv.setOnClickListener{
            startActivity(Intent(activity, HomeDetailActivity::class.java))
        }
        binding.mainKeywordrecThemecock1MoreTv.setOnClickListener{
            startActivity(Intent(activity, HomeDetailActivity::class.java))
        }
//        val keywordId : Int = 0,
//        val keywordName: String = "",
        var cocktailkeywordlist : ArrayList<CocktailKeywords> = ArrayList()

        cocktail = CocktailKeywords(0,"어머나")
        cocktail.keywordId=0
        cocktail.keywordName = "맛있는"
        cocktailkeywordlist.add(cocktail)
        cocktail.keywordId=1
        cocktail.keywordName = "개쩌는"
        cocktailkeywordlist.add(cocktail)

        //오늘의 칵테일 뷰페이저
        val todayscockVpAdapter = TodayCocktailViewpagerAdapter(this)
        todayscockVpAdapter.addFragment(5,"영어이름",
            "한글이름",cocktailkeywordlist,"https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/today/21stCentury.webp")
        todayscockVpAdapter.addFragment(5,"영어이름2",
            "한글이름2",cocktailkeywordlist,"https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/today/BlueStar.webp")
        binding.mainKeywordrecTodaycockRv.adapter = todayscockVpAdapter


        //배너 뷰페이져
        val subbannerBinding = SubBannerViewpagerAdapter(this)
        subbannerBinding.addFragment(R.drawable.detail_bg,"칵테일을 더 맛있게 먹고싶다면?", Color.WHITE)
        subbannerBinding.addFragment(R.drawable.recommend_todays2,"낮져밤이 칵테일", Color.BLACK)
        binding.mainKeywordrecSubbannerRv.adapter = subbannerBinding


        val todayRecService = TodayrecService()
        todayRecService.settodayrecView(this)
        todayRecService.todayRec()
    }

    override fun onMainrecLoading() {
    }

    override fun onMainrecSuccess(message: String) {
        Log.d("Test",message.toString())
    }

    override fun onSignUpFailure() {
        Log.d("Test","실패")
    }
}