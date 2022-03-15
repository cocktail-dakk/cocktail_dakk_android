package com.umcapplunching.cocktail_dakk.ui.main.keyword

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.data.entities.Cocktail_SearchList
import com.umcapplunching.cocktail_dakk.databinding.FragmentKeywordrecommandBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.main.keyword.todayrec.KeywordrecService.*
import com.umcapplunching.cocktail_dakk.ui.main.keyword.todayrec.TodayCocktailViewpagerAdapter
import com.umcapplunching.cocktail_dakk.utils.getaccesstoken
import kotlinx.coroutines.launch


class KeywordrecommandFragment :
    BaseFragment<FragmentKeywordrecommandBinding>(FragmentKeywordrecommandBinding::inflate),
    TodayrecView, KeywordrecView {

    override fun initAfterBinding() {
        SetDummyData()
        //오늘의 칵테일 서버에서 받아오기
        val keywordRecService = KeywordrecService()
        keywordRecService.settodayrecView(this)
        launch {
            keywordRecService.todayRec(getaccesstoken(requireContext()))
        }
        keywordRecService.setkeywordrecView(this)
        launch {
            keywordRecService.keywordRec(getaccesstoken(requireContext()))
        }
    }

    private fun SetDummyData() {
        //배너 뷰페이져
        val subbannerBinding = SubBannerViewpagerAdapter(this)
        subbannerBinding.addFragment(R.drawable.main_keyword_banner, " ", Color.WHITE)
//        subbannerBinding.addFragment(R.drawable.recommend_todays2, "낮져밤이 칵테일", Color.BLACK)
        binding.mainKeywordrecSubbannerRv.adapter = subbannerBinding
    }

    override fun onTodayrecLoading() {
    }

    override fun onTodayrecSuccess(result: List<TodayrecResult>) {
        //오늘의 칵테일 뷰페이저
        val todayscockVpAdapter = TodayCocktailViewpagerAdapter(this)
        for (i in 0 until result.size) {
            todayscockVpAdapter.addFragment(
                i,
                result[i].cocktailInfoId, result[i].englishName,
                result[i].koreanName, result[i].cocktailKeywords, result[i].recommendImageURL
            )
        }
        binding.mainKeywordrecTodaycockRv.adapter = todayscockVpAdapter

    }

    override fun onTodayrecFailure(code: Int, message: String) {
        Log.d("TodayRec_API_Failure", message + code.toString())
    }

    override fun onKeywordrecLoading() {
    }

    @SuppressLint("SetTextI18n")
    override fun onKeywordrecSuccess(result: List<KeywordrecResult>) {
        var cocktailList: ArrayList<Cocktail_SearchList> = ArrayList()

        if (result[0].tag == "키워드") {
            binding.mainKeywordrecThemecock1Tv.text = "#"+result[0].description+" 태그가 들어간 칵테일"
            for (i in result[0].recommendationRes.indices) {
                cocktailList.add(
                    Cocktail_SearchList(
                        result[0].recommendationRes[i].koreanName,
                        result[0].recommendationRes[i].englishName,
                        result[0].recommendationRes[i].cocktailKeywords,
                        result[0].recommendationRes[i].smallNukkiImageURL,
                        result[0].recommendationRes[i].ratingAvg,
                        0,
                        "기주",
                        result[0].recommendationRes[i].cocktailInfoId
                    )
                )
            }
        }

        val spf = context?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spf?.edit()!!
        binding.mainKeywordrecThemecock1MoreIv.setOnClickListener {
            editor.putString("searchstr", result[0].description)
            editor.apply()
            (activity as MainActivity).changetoSearchtab()
        }
        binding.mainKeywordrecThemecock1MoreTv.setOnClickListener {
            editor.putString("searchstr", result[0].description)
            editor.apply()
            (activity as MainActivity).changetoSearchtab()
        }

        val cockRecommandRvAdapter = CockRecommandRvAdapter(cocktailList)
        binding.mainKeywordrecRv1.adapter = cockRecommandRvAdapter
        cockRecommandRvAdapter.setMyItemClickListener(object : CockRecommandRvAdapter.MyItemClickListener {
            override fun onItemClick(cocktail: Cocktail_SearchList) =
                (activity as MainActivity).detailcocktail(cocktail.id)
        })
        cocktailList = ArrayList()

        if (result[1].tag == "기주") {
            binding.mainKeywordrecThemecock2Tv.text = "기주가 " + result[1].description+"인 칵테일"
            for (i in result[1].recommendationRes.indices) {
                cocktailList.add(
                    Cocktail_SearchList(
                        result[1].recommendationRes[i].koreanName,
                        result[1].recommendationRes[i].englishName,
                        result[1].recommendationRes[i].cocktailKeywords,
                        result[1].recommendationRes[i].smallNukkiImageURL,
                        result[1].recommendationRes[i].ratingAvg,
                        0,
                        "기주",
                        result[1].recommendationRes[i].cocktailInfoId
                    )
                )
            }
        }

        binding.mainKeywordrecThemecock2MoreIv.setOnClickListener {
            editor.putString("searchstr", result[1].description)
            editor.apply()
            (activity as MainActivity).changetoSearchtab()
        }
        binding.mainKeywordrecThemecock2MoreTv.setOnClickListener {
            editor.putString("searchstr", result[1].description)
            editor.apply()
            (activity as MainActivity).changetoSearchtab()
        }

        val cockRecommandRvAdapter2 = CockRecommandRvAdapter(cocktailList)
        binding.mainKeywordrecRv2.adapter = cockRecommandRvAdapter2

        cockRecommandRvAdapter2.setMyItemClickListener(object : CockRecommandRvAdapter.MyItemClickListener {
            override fun onItemClick(cocktail: Cocktail_SearchList) {
                (activity as MainActivity).detailcocktail(cocktail.id)
            }
        })
    }

    override fun onKeywordrecFailure(code: Int, message: String) {
        Log.d("KeywordRec_API_Failure", message + code.toString())
        if (code==5000){
            (activity as MainActivity).TokenrefreshInMain()
        }
    }
}