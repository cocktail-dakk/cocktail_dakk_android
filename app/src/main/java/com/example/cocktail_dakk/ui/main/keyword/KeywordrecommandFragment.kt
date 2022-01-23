package com.example.cocktail_dakk.ui.main.keyword

import android.content.Intent
import android.graphics.Color
import androidx.fragment.app.Fragment
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail
import com.example.cocktail_dakk.databinding.FragmentKeywordrecommandBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.home_detail.HomeDetailActivity
import com.example.cocktail_dakk.ui.search_tab.SearchTabActivity


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

        val subbannerBinding = SubBannerViewpagerAdapter(this)
        subbannerBinding.addFragment(R.drawable.detail_bg,"칵테일을 더 맛있게 먹고싶다면?", Color.WHITE)
        subbannerBinding.addFragment(R.drawable.recommend_todays2,"낮져밤이 칵테일", Color.BLACK)
        binding.mainKeywordrecSubbannerRv.adapter = subbannerBinding

    }
}