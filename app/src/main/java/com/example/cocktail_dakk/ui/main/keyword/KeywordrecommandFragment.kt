package com.example.cocktail_dakk.ui.main.keyword

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail
import com.example.cocktail_dakk.data.entities.Cocktail_SearchList
import com.example.cocktail_dakk.databinding.FragmentKeywordrecommandBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.MainActivity
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

        binding.mainKeywordrecThemecock1MoreIv.setOnClickListener {
            var spf = context?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = spf?.edit()!!
            editor.putString("searchstr", result[0].description)
            editor.apply()
            (activity as MainActivity).changetoSearchtab()
        }
        binding.mainKeywordrecThemecock1MoreTv.setOnClickListener {
            var spf = context?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = spf?.edit()!!
            editor.putString("searchstr", result[0].description)
            editor.apply()
            (activity as MainActivity).changetoSearchtab()
        }

        var cockRecommandRvAdapter = CockRecommandRvAdapter(cocktailList)
        binding.mainKeywordrecRv1.adapter = cockRecommandRvAdapter
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

        binding.mainKeywordrecThemecock2MoreIv.setOnClickListener {
            var spf = context?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = spf?.edit()!!
            editor.putString("searchstr", result[1].description)
            editor.apply()
            (activity as MainActivity).changetoSearchtab()
        }
        binding.mainKeywordrecThemecock2MoreTv.setOnClickListener {
            var spf = context?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = spf?.edit()!!
            editor.putString("searchstr", result[1].description)
            editor.apply()
            (activity as MainActivity).changetoSearchtab()
        }

        cockRecommandRvAdapter = CockRecommandRvAdapter(cocktailList)
        binding.mainKeywordrecRv2.adapter = cockRecommandRvAdapter
    }

    override fun onKeywordrecFailure(code: Int, message: String) {
        Log.d("KeywordRec_API_Failure", message + code.toString())
    }
}