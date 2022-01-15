package com.example.cocktail_dakk.ui.main.keyword

import android.graphics.Color
import androidx.fragment.app.Fragment
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail
import com.example.cocktail_dakk.databinding.FragmentKeywordrecommandBinding
import com.example.cocktail_dakk.ui.BaseFragment


class KeywordrecommandFragment : BaseFragment<FragmentKeywordrecommandBinding>(FragmentKeywordrecommandBinding::inflate) {
    override fun initAfterBinding() {
        SetDummyData()

        //binding.mainKeywordrecTodaycockIv.setImageResource(R.drawable.test4)
    }

    private fun SetDummyData() {
        val Cocktaillist: ArrayList<Cocktail> = ArrayList()
        // 칵테일 더미데이터
        //        val localName: String = "",
        //        val englishName: String = "",
        //        val image: Int = 0,
        //        val mixing: String = "",
        //        val baseCocktail: String = "",
        //        val starPoint: Float = 0.0f,
        //        val alcoholLevel: Int = 0,
        //        val ingredients: String = "",
        //        val keywords: String = "",
        //        val information: String = "",
        Cocktaillist.add(Cocktail("칵테일1", "CockTail_1", R.drawable.search_ex1))
        Cocktaillist.add(Cocktail("칵테일2", "CockTail_2", R.drawable.search_ex2))
        Cocktaillist.add(Cocktail("칵테일3", "CockTail_3", R.drawable.search_ex3))
        Cocktaillist.add(Cocktail("칵테일4", "CockTail_4", R.drawable.search_ex1))
        Cocktaillist.add(Cocktail("칵테일5", "CockTail_5", R.drawable.search_ex2))
        Cocktaillist.add(Cocktail("칵테일6", "CockTail_6", R.drawable.search_ex3))
        val cockRecommandRvAdapter = CockRecommandRvAdapter(Cocktaillist)
        binding.mainKeywordrecRv1.adapter = cockRecommandRvAdapter


        val cockRecommandRvAdapter2 = CockRecommandRvAdapter(Cocktaillist)
        binding.mainKeywordrecRv2.adapter = cockRecommandRvAdapter2

        val subbannerBinding = SubBannerViewpagerAdapter(this)
        subbannerBinding.addFragment(R.drawable.detail_bg,"칵테일을 더 맛있게 먹고싶다면?", Color.WHITE)
        subbannerBinding.addFragment(R.drawable.recommend_todays2,"낮져밤이 칵테일", Color.BLACK)
        binding.mainKeywordrecSubbannerRv.adapter = subbannerBinding

    }
}