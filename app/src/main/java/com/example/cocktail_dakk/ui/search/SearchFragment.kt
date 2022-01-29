package com.example.cocktail_dakk.ui.search

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail
import com.example.cocktail_dakk.data.entities.Cocktail_SearchList
import com.example.cocktail_dakk.databinding.FragmentSearchBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.MainActivity
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecService
import com.example.cocktail_dakk.ui.menu_detail.MenuDetailActivity
import com.example.cocktail_dakk.ui.search.searchService.Keyword
import com.example.cocktail_dakk.ui.search.searchService.SearchResult
import com.example.cocktail_dakk.ui.search.searchService.SearchService
import com.example.cocktail_dakk.ui.search.searchService.SearchView
import com.example.cocktail_dakk.ui.search_tab.SearchTabActivity
import kotlin.math.log

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate), SearchView {


    override fun initAfterBinding(){
        binding.searchSearchbarLv.visibility = View.VISIBLE

        var spf =  activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab",0)
        editor.commit()

        setOnClickListener()

//        val cocktaillist: ArrayList<Cocktail> = ArrayList()
//        adddumylist(cocktaillist)
//        setCocktailList(cocktaillist)


    }

    override fun onResume() {
        super.onResume()
        var spf =  activity?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
        val searchService = SearchService()
        searchService.setsearchView(this)
        searchService.search(spf!!.getString("searchstr"," ").toString())
    }

    private fun setOnClickListener() {
        binding.searchSearchbarLv.setOnClickListener {
            startActivity(Intent(activity, SearchTabActivity::class.java))
            var animTransRight: Animation = AnimationUtils
                .loadAnimation(activity, R.anim.horizon_out)
            animTransRight.duration = 700
            binding.searchSearchbarLv.startAnimation(animTransRight)
            binding.searchSearchbarLv.visibility = View.INVISIBLE
        }

        //필터탭
        binding.searchFilterIv.setOnClickListener {
            (activity as MainActivity).ShowFilter(true)
        }
    }

    private fun setCocktailList(cocktaillist : ArrayList<Cocktail_SearchList>) {

        val searchListAdapter = SearchlistRvAdapter(cocktaillist)
        binding.searchMainRv.adapter = searchListAdapter
        searchListAdapter.setClickListiner(object : SearchlistRvAdapter.MyItemClickListener {
            override fun onItemClick(cocktail: Cocktail_SearchList) {
                changeDetailFragment(cocktail)
            }

            private fun changeDetailFragment(cocktail: Cocktail_SearchList) {
                startActivity(Intent(activity, MenuDetailActivity::class.java))
            }
        })
    }

    private fun adddumylist(cocktaillist: ArrayList<Cocktail>) {
        cocktaillist.add(
            Cocktail(
                "옴뇸뇸 칵테일",
                "CockTail_1",
                R.drawable.img_cocktail_21century,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_brandysour.webp"
            )
        )
        cocktaillist.add(
            Cocktail(
                "아그작 칵테일",
                "CockTail_2",
                R.drawable.img_cocktail_alaskaicedtea,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_woowoo.webp"
            )
        )
        cocktaillist.add(
            Cocktail(
                "당신의 사랑의 첫 키스",
                "CockTail_3",
                R.drawable.img_cocktail_b_b,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_b-52.webp"
            )
        )
        cocktaillist.add(
            Cocktail(
                "안녕히계세요 여러분 저는 속세의 굴레를 벗어나",
                "CockTail_4",
                R.drawable.img_cocktail_brandysour,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_21century.webp"
            )
        )
        cocktaillist.add(
            Cocktail(
                "칵테일1",
                "CockTail_5",
                R.drawable.img_cocktail_woowoo,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_alaskaicedtea.webp"
            )
        )
        cocktaillist.add(
            Cocktail(
                "칵테일1",
                "CockTail_5",
                R.drawable.img_cocktail_woowoo,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_alaskaicedtea.webp"
            )
        )
        cocktaillist.add(
            Cocktail(
                "칵테일1",
                "CockTail_5",
                R.drawable.img_cocktail_woowoo,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_alaskaicedtea.webp"
            )
        )

        cocktaillist.add(
            Cocktail(
                "옴뇸뇸 칵테일",
                "CockTail_1",
                R.drawable.img_cocktail_21century,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_brandysour.webp"
            )
        )
        cocktaillist.add(
            Cocktail(
                "아그작 칵테일",
                "CockTail_2",
                R.drawable.img_cocktail_alaskaicedtea,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_woowoo.webp"
            )
        )
        cocktaillist.add(
            Cocktail(
                "당신의 사랑의 첫 키스",
                "CockTail_3",
                R.drawable.img_cocktail_b_b,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_b-52.webp"
            )
        )
        cocktaillist.add(
            Cocktail(
                "안녕히계세요 여러분 저는 속세의 굴레를 벗어나",
                "CockTail_4",
                R.drawable.img_cocktail_brandysour,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_21century.webp"
            )
        )
        cocktaillist.add(
            Cocktail(
                "칵테일1",
                "CockTail_5",
                R.drawable.img_cocktail_woowoo,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_alaskaicedtea.webp"
            )
        )
        cocktaillist.add(
            Cocktail(
                "칵테일1",
                "CockTail_5",
                R.drawable.img_cocktail_woowoo,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_alaskaicedtea.webp"
            )
        )
        cocktaillist.add(
            Cocktail(
                "칵테일1",
                "CockTail_5",
                R.drawable.img_cocktail_woowoo,
                "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_alaskaicedtea.webp"
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    //검색했을 때
    override fun onSearchLoading() {
    }

    override fun onSearchSuccess(searchresult: SearchResult) {
        val cocktaillist: ArrayList<Cocktail_SearchList> = ArrayList()
        for (i in searchresult.cocktailList){
//            cocktaillist.add(Cocktail(i.koreanName,i.englishName,0,"i.smallNukkiImageURL","기주",2.5f,i.alcoholLevel,"재료","키워드","정보"))
            cocktaillist.add(Cocktail_SearchList(i.koreanName,i.englishName,i.keywords,"https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_brandysour.webp",i.ratingAvg,i.alcoholLevel,"기주"))
        }
        setCocktailList(cocktaillist)
        binding.searchResultTv.text = cocktaillist.size.toString() + "개의 검색결과"

//        val localName: String = "",
//        val englishName: String = "",
//        val keywords: List<Keyword>,
//        val imageURL: String = "",
//        val starPoint: Float = 0.0f,
//        val alcoholLevel: Int = 0,
//        val baseCocktail: String = "",

    }

    override fun onSearchFailure(code: Int, message: String) {
    }

}