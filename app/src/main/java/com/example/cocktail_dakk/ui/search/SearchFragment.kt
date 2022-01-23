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
import com.example.cocktail_dakk.databinding.FragmentSearchBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.menu_detail.MenuDetailActivity
import com.example.cocktail_dakk.ui.search_tab.SearchTabActivity
import kotlin.math.log

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override fun initAfterBinding() {
        binding.searchSearchbarLv.visibility = View.VISIBLE

        var spf =  activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab",0)
        editor.commit()

        val cocktaillist: ArrayList<Cocktail> = ArrayList()
        cocktaillist.add(Cocktail("옴뇸뇸 칵테일", "CockTail_1", R.drawable.img_cocktail_21century))
        cocktaillist.add(Cocktail("아그작 칵테일", "CockTail_2", R.drawable.img_cocktail_alaskaicedtea))
        cocktaillist.add(Cocktail("당신의 사랑의 첫 키스", "CockTail_3", R.drawable.img_cocktail_b_b))
        cocktaillist.add(
            Cocktail(
                "안녕히계세요 여러분 저는 속세의 굴레를 벗어나",
                "CockTail_4",
                R.drawable.img_cocktail_brandysour
            )
        )
        cocktaillist.add(Cocktail("칵테일1", "CockTail_5", R.drawable.img_cocktail_woowoo))
        cocktaillist.add(Cocktail("칵테일2", "CockTail_6", R.drawable.img_cocktail_b_b))
        cocktaillist.add(Cocktail("칵테일3", "CockTail_6", R.drawable.img_cocktail_b_b))
        cocktaillist.add(Cocktail("칵테일4", "CockTail_6", R.drawable.img_cocktail_b_b))
        cocktaillist.add(Cocktail("칵테일5", "CockTail_6", R.drawable.img_cocktail_b_b))
        cocktaillist.add(Cocktail("칵테일1", "CockTail_5", R.drawable.img_cocktail_woowoo))



        val searchListAdapter = SearchlistRvAdapter(cocktaillist)
        binding.searchMainRv.adapter = searchListAdapter
        searchListAdapter.setClickListiner(object : SearchlistRvAdapter.MyItemClickListener {
            override fun onItemClick(cocktail: Cocktail) {
                changeAlbumFragment(cocktail)
            }

            private fun changeAlbumFragment(cocktail: Cocktail) {
                startActivity(Intent(activity, MenuDetailActivity::class.java))
            }
        })
        binding.searchSearchbarLv.setOnClickListener {
            startActivity(Intent(activity, SearchTabActivity::class.java))
            var animTransRight: Animation = AnimationUtils
                .loadAnimation(activity, R.anim.horizon_out);
            binding.searchSearchbarLv.startAnimation(animTransRight)
            binding.searchSearchbarLv.visibility = View.INVISIBLE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}