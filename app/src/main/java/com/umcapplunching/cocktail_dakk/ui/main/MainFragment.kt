package com.umcapplunching.cocktail_dakk.ui.main

import android.content.Intent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.FragmentMainBinding
import com.umcapplunching.cocktail_dakk.ui.main.adapter.MainViewpagerAdapter
import com.umcapplunching.cocktail_dakk.ui.search_tab.SearchTabActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import android.app.ActionBar
import android.content.Context
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.umcapplunching.cocktail_dakk.ui.BaseFragmentByDataBinding
import com.umcapplunching.cocktail_dakk.ui.search.SearchCocktailViewModel


class MainFragment : BaseFragmentByDataBinding<FragmentMainBinding>(R.layout.fragment_main) {
    private val information = arrayListOf("  맞춤 추천  ", "  키워드 추천  ")
    private lateinit var callback: OnBackPressedCallback
    private lateinit var searchCocktailViewModel: SearchCocktailViewModel

    override fun initViewModel() {
        binding.lifecycleOwner = this
        searchCocktailViewModel = ViewModelProvider(requireActivity()).get(SearchCocktailViewModel::class.java)
        binding.searchViewModel = searchCocktailViewModel

    }

    override fun initView() {

        val metrics = resources.displayMetrics
        val widthPixels = metrics.widthPixels
        val heightPixels = metrics.heightPixels - 160
        val params = CoordinatorLayout.LayoutParams(widthPixels,heightPixels)

        binding.mainVp.layoutParams = params
        binding.mainVp.isUserInputEnabled = false
        binding.mainVp.adapter = MainViewpagerAdapter(this)
        TabLayoutMediator(binding.mainTl, binding.mainVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        // Tablayout 설정
        binding.mainTl.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0){
                    binding.mainVp.layoutParams = params
                }
                else{
                    val params = CoordinatorLayout.LayoutParams(widthPixels,ActionBar.LayoutParams.MATCH_PARENT)
                    val behavior : AppBarLayout.ScrollingViewBehavior = AppBarLayout.ScrollingViewBehavior()
                    params.behavior = behavior
                    binding.mainVp.layoutParams = params
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })


        // 검색어 글자 설정
        searchCocktailViewModel.searchStr.observe(this,{
            if(it.trim()==""){
                binding.mainSearchbarTv.text = "검색어를 입력해주세요."
                binding.mainSearchbarExiticonIv.visibility = View.GONE
            }else{
                binding.mainSearchbarTv.text = it
                binding.mainSearchbarExiticonIv.visibility = View.VISIBLE
            }
        })
    }

    override fun initListener() {
        // 검색어 지우기 버튼
        binding.mainSearchbarExiticonIv.setOnClickListener {
            searchCocktailViewModel.setSearchStr(" ")
        }

        binding.mainSearchbarIv.setOnClickListener{
            val animTransRight: Animation = AnimationUtils
                .loadAnimation(activity, R.anim.horizon_out)
            animTransRight.duration = 700
            binding.mainSearchbarIv.startAnimation(animTransRight)
            startActivity(Intent(activity, SearchTabActivity::class.java))
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

}   