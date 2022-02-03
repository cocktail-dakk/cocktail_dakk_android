package com.example.cocktail_dakk.ui.main.keyword

import android.content.Intent
import android.graphics.Color
import android.util.Log
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail
import com.example.cocktail_dakk.data.entities.Cocktail_SearchList
import com.example.cocktail_dakk.databinding.FragmentKeywordrecommandBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.home_detail.HomeDetailActivity
import com.example.cocktail_dakk.ui.main.keyword.todayrec.KeywordrecService.*
import com.example.cocktail_dakk.ui.main.keyword.todayrec.TodayCocktailViewpagerAdapter


class KeywordrecommandFragment :
    BaseFragment<FragmentKeywordrecommandBinding>(FragmentKeywordrecommandBinding::inflate),
    TodayrecView, KeywordrecView {

    //    lateinit var cocktailkeywordlist : ArrayList<CocktailKeywords>
//    lateinit var cocktail: CocktailKeyword

    override fun initAfterBinding() {
        SetDummyData()

        //오늘의 칵테일 서버에서 받아오기
        val keywordRecService = KeywordrecService()
        keywordRecService.settodayrecView(this)
        keywordRecService.todayRec()

        keywordRecService.setkeywordrecView(this)
        keywordRecService.keywordRec("1234")
    }

    private fun SetDummyData() {
        val Cocktaillist: ArrayList<Cocktail> = ArrayList()
        // 칵테일 더미데이터
//        Cocktaillist.add(Cocktail("칵테일1", "CockTail_1", R.drawable.img_cocktail_b_52))
//        Cocktaillist.add(Cocktail("칵테일2", "CockTail_2", R.drawable.img_cocktail_21century))
//        Cocktaillist.add(Cocktail("칵테일3", "CockTail_3", R.drawable.img_cocktail_brandysour))
//        Cocktaillist.add(Cocktail("칵테일4", "CockTail_4", R.drawable.img_cocktail_woowoo))
//        Cocktaillist.add(Cocktail("칵테일5", "CockTail_5", R.drawable.img_cocktail_alaskaicedtea))
//        Cocktaillist.add(Cocktail("칵테일5", "CockTail_5", R.drawable.img_cocktail_brandysour))
//        Cocktaillist.add(Cocktail("칵테일5", "CockTail_5", R.drawable.img_cocktail_21century))
//        val cockRecommandRvAdapter = CockRecommandRvAdapter(Cocktaillist)
//        binding.mainKeywordrecRv1.adapter = cockRecommandRvAdapter
//
//        val cockRecommandRvAdapter2 = CockRecommandRvAdapter(Cocktaillist)
//        binding.mainKeywordrecRv2.adapter = cockRecommandRvAdapter2

        binding.mainKeywordrecThemecock1MoreIv.setOnClickListener {
            startActivity(Intent(activity, HomeDetailActivity::class.java))
        }
//        binding.mainKeywordrecThemecock1MoreTv.setOnClickListener{
//            startActivity(Intent(activity, HomeDetailActivity::class.java))
//        }


        //배너 뷰페이져
        val subbannerBinding = SubBannerViewpagerAdapter(this)
        subbannerBinding.addFragment(R.drawable.main_keyword_banner, " ", Color.WHITE)
        subbannerBinding.addFragment(R.drawable.recommend_todays2, "낮져밤이 칵테일", Color.BLACK)
        binding.mainKeywordrecSubbannerRv.adapter = subbannerBinding

    }

    override fun onTodayrecLoading() {
    }

    override fun onTodayrecSuccess(result: List<TodayrecResult>) {
        //오늘의 칵테일 뷰페이저
        val todayscockVpAdapter = TodayCocktailViewpagerAdapter(this)
        for (i in 0 until result.size-1) {
            todayscockVpAdapter.addFragment(
                i,
                result[i].cocktailInfoId, result[i].englishName,
                result[i].koreanName, result[i].cocktailKeywords, result[i].recommendImageURL!!
            )
        }
        binding.mainKeywordrecTodaycockRv.adapter = todayscockVpAdapter

    }

    override fun onTodayrecFailure(code: Int, message: String) {
        Log.d("TodayRec_API_Failure", message.toString() + code.toString())
    }

    override fun onKeywordrecLoading() {
    }

    override fun onKeywordrecSuccess(result: List<KeywordrecResult>) {
        Log.d("test", result.toString())
        var cocktailList: ArrayList<Cocktail_SearchList> = ArrayList()

        if (result[0].tag == "키워드") {
            binding.mainKeywordrecThemecock1Tv.setText("#" +result[0].description+" 태그가 들어간 칵테일")
            for (i in 0 until result[0].recommendationRes.size - 1) {
                cocktailList.add(
                    Cocktail_SearchList(
                        result[0].recommendationRes[i].koreanName,
                        result[0].recommendationRes[i].englishName,
                        result[0].recommendationRes[i].cocktailKeywords,
                        result[0].recommendationRes[i].smallNukkiImageURL,
                        result[0].recommendationRes[i].ratingAvg,
                        0,
                        "기주"
                    )
                )
            }
        }

        var cockRecommandRvAdapter = CockRecommandRvAdapter(cocktailList)
        binding.mainKeywordrecRv1.adapter = cockRecommandRvAdapter
//        cocktailList.clear()
        cocktailList = ArrayList()

        if (result[1].tag == "기주") {
            binding.mainKeywordrecThemecock2Tv.setText("기주가 " + result[1].description+"인 칵테일")
            for (i in 0 until result[1].recommendationRes.size - 1) {
                cocktailList.add(
                    Cocktail_SearchList(
                        result[1].recommendationRes[i].koreanName,
                        result[1].recommendationRes[i].englishName,
                        result[1].recommendationRes[i].cocktailKeywords,
                        result[1].recommendationRes[i].smallNukkiImageURL,
                        result[1].recommendationRes[i].ratingAvg,
                        0,
                        "기주"
                    )
                )
            }
        }
        cockRecommandRvAdapter = CockRecommandRvAdapter(cocktailList)
        binding.mainKeywordrecRv2.adapter = cockRecommandRvAdapter
    }

    override fun onKeywordrecFailure(code: Int, message: String) {
        Log.d("KeywordRec_API_Failure", message + code.toString())
    }
}