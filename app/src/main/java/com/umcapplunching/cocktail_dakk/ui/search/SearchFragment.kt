package com.umcapplunching.cocktail_dakk.ui.search

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.data.datastore.DataStoreSearchStr
import com.umcapplunching.cocktail_dakk.data.entities.Cocktail_SearchList
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.CocktailDatabase
import com.umcapplunching.cocktail_dakk.databinding.FragmentSearchBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragmentByDataBinding
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.search.searchService.*
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchView
import com.umcapplunching.cocktail_dakk.ui.search_tab.SearchTabActivity
import com.umcapplunching.cocktail_dakk.ui.start.Service.UserService
import com.umcapplunching.cocktail_dakk.utils.getaccesstoken
import hearsilent.discreteslider.DiscreteSlider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchFragment : BaseFragmentByDataBinding<FragmentSearchBinding>(R.layout.fragment_search), SearchView{
//    SearchView, PagingView, FilterView, FilterpagingView, TokenResfreshView, IslikeView {

    private var recyclerViewState: Parcelable? = null
    private lateinit var callback: OnBackPressedCallback
    lateinit var cocktaildb : CocktailDatabase

    private val searchService by lazy {
        SearchService()
    }
    val userService = UserService()

    var currentpage = 0
    var totalcnt = 10
    private val cocktaillist: ArrayList<Cocktail_SearchList> = ArrayList()

    private var searchMode: Int = 0 //0이면 검색 1이면 필터
    private var scrollFlag: Boolean = true //스크롤 가능한지
//    private var isfilterring: Boolean = false
//    private var isfilterscroll: Boolean = false
    private var ispagingnow : Boolean = false // 지금 페이징 중인지

    var gijulist = ArrayList<String>()
    var favorkeyword = ArrayList<String>()
    var drink_foradapter = ArrayList<String>()
    var keyword_foradapter = ArrayList<String>()

    var dosumin: Int = 10
    var dosumax: Int = 30

    private lateinit var searchListAdapter: SearchlistRvAdapter
    private lateinit var searchCocktailViewModel : SearchCocktailViewModel
    private val TAG = "SearchFragment"

    override fun initViewModel() {
        searchCocktailViewModel = ViewModelProvider(requireActivity()).get(SearchCocktailViewModel::class.java)
        binding.lifecycleOwner = this
        binding.searchViewModel = searchCocktailViewModel
        searchService.setsearchView(this)


        // 어뎁터 기본설정
        searchListAdapter = SearchlistRvAdapter(searchCocktailViewModel.visibleitemList.value!!)
    }
    override fun initView() {

        searchCocktailViewModel.searchStr.observe(this,{
            if(it.trim()==""){
                binding.searchSearchbarTv.text = "검색어를 입력해주세요."
            }else{
                binding.searchSearchbarTv.text = it
            }
        })

        CoroutineScope(Dispatchers.IO).launch {
            // 아무것도 검색 안했을 때 (초기화 상태)일 때 가져오기
            searchService.search(getaccesstoken(requireContext())," ")
        }

        searchCocktailViewModel.visibleitemList.observe(this, {
            searchListAdapter.updateList(it)
        })

        searchCocktailViewModel.searchStr.observe(this, {
            CoroutineScope(Dispatchers.IO).launch {
                searchService.search(getaccesstoken(requireContext()),it)
            }
        })

        setOnClickListener()
        FilterClcikListener()
        SetMainRvScrollListener()

        binding.searchMainRv.adapter = searchListAdapter
    }

    override fun onSearchLoading() {

    }

    override fun onSearchSuccess(searchresult: SearchResult) {
        // 뷰모델 옵저빙하고 있어서 뷰모델 칵테일만 바꿔주기
        searchCocktailViewModel.setCocktail(searchresult.cocktailList)
        totalcnt = searchresult.size


        requireActivity().runOnUiThread {
            binding.searchLoadingBar.visibility = View.GONE
            binding.searchProgressbar.visibility = View.GONE
            binding.searchResultTv.text = "${totalcnt}개의 검색결과"
//            binding.searchSearchbarTv.text = "검색어를 입력해주세요."
            binding.searchMainRv.scrollToPosition(0)
        }
    }

    override fun onSearchFailure(code: Int, message: String) {

    }

//
//
//    private fun setCurrentPage() {
//        val spf = activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
//        val editor: SharedPreferences.Editor = spf?.edit()!!
//        editor.putInt("currenttab", 0)
//        editor.apply()
//    }
//
//    override fun onResume() {
//        super.onResume()
////        Log.d("test","SearchFragment : Onresume")
//
//        val spf = activity?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
//        searchService.setsearchView(this)
//        searchService.setpagingView(this)
//        searchService.setfilterView(this)
//        searchService.setislikeView(this)
//        searchService.setfilterPagingView(this)
//        userService.settokenRefreshView(this)
//
//        CoroutineScope(Dispatchers.IO).launch {
//            //검색모드
//            searchMode = 0
//            // 현재 페이지
//            currentpage = 0
//            scrollFlag = true
//            //리스트 갯수
//            totalcnt = 0
//            //필터링중인지
//            isfilterring = false
//            searchService.search(getaccesstoken(requireContext()),spf!!.getString("searchstr", " ").toString().trim())
//
//            //DB 최근검색어 넣기 중복체크 후 인설트
//            if (spf.getString("searchstr", " ")!!.trim() != " " || spf.getString("searchstr", " ")!!
//                    .trim() == ""
//            ) {
//                val CocktailDB = CocktailDatabase.getInstance(requireContext())!!
//                CocktailDB.RecentSearchDao()
//                    .duplicatecheck(spf.getString("searchstr", " ").toString().trim())
//                CocktailDB.RecentSearchDao()
//                    .insert(Cocktail_recentSearch(spf.getString("searchstr", " ").toString().trim()))
//            }
//        }
//
//        binding.mainFilterDosuSeekbar.minProgress = dosumin
//        binding.mainFilterDosuSeekbar.maxProgress = dosumax
//
//        //검색어 설정
//        val text = spf!!.getString("searchstr", " ")?.trim()
//        if (text == " " || text == "") {
//            binding.searchSearchbarExiticonIv.visibility = View.GONE
//            binding.searchSearchbarTv.text = "검색어를 입력해주세요."
//        } else {
//            binding.searchSearchbarExiticonIv.visibility = View.VISIBLE
//            binding.searchSearchbarTv.text = text
//        }
//
//    }
//
    private fun SetMainRvScrollListener() {
        val onScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE &&
                    scrollFlag &&
                    searchMode == 0 &&
                    !ispagingnow
                ) {
                    ispagingnow = true
                    binding.searchProgressbar.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed({
                        requsetnextpage()
                    }, 300)
                }
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && scrollFlag
                    && searchMode == 1  && !ispagingnow
                ) {
                    ispagingnow = true
                    binding.searchProgressbar.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed({
                        requsetnextpagefor_filter()
                    }, 300)
                }
            }
        }
        binding.searchMainRv.addOnScrollListener(onScrollListener)
    }

    private fun setOnClickListener() {
        //서치바클릭 했을때 애니메이션 및 효과
        binding.searchSearchbarLv.setOnClickListener {
            startActivity(Intent(activity, SearchTabActivity::class.java))
            val animTransRight: Animation = AnimationUtils
                .loadAnimation(activity, R.anim.horizon_out)
            animTransRight.duration = 700
            binding.searchSearchbarLv.startAnimation(animTransRight)
        }

        //필터탭 클릭했을때
        binding.searchFilterIv.setOnClickListener {
//            isfilterring = true
            ShowFilter(true)
            initSelected()
        }

        //exit_icon
        binding.searchSearchbarExiticonIv.setOnClickListener {
            searchCocktailViewModel.setSearchStr(" ")
        }

    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                if (isfilterring) {
//                    ShowFilter(false)
//                    isfilterring = false
//                }
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        callback.remove()
//    }
//
//    fun setCocktailList(cocktaillist: ArrayList<Cocktail_SearchList>) {
//        searchListAdapter = SearchlistRvAdapter(cocktaildb.IslikeDao().getcocktail(),cocktaillist)
//        binding.searchMainRv.adapter = searchListAdapter
//
//        searchListAdapter.setClickListiner(object : SearchlistRvAdapter.MyItemClickListener {
//            override fun onItemClick(cocktail: Cocktail_SearchList) {
//                changeDetailFragment(cocktail)
//            }
//
//            override fun onKeywordClick(keyword: String) {
//                if(searchMode==0) {
//                    val spf = requireContext().getSharedPreferences(
//                        "searchstr",
//                        AppCompatActivity.MODE_PRIVATE
//                    )
//                    val editor: SharedPreferences.Editor = spf?.edit()!!
//                    editor.putString("searchstr", keyword)
//                    editor.apply()
//                    onResume()
//                }
//            }
//
//            override fun onItemIsLike(islike: Boolean, cocktail: Cocktail_SearchList) {
//                if (islike){
//                    cocktaildb.IslikeDao().insert(Cocktail_Islike(cocktail.id))
//                    searchService.IsLike(getaccesstoken(requireContext()),cocktail.id)
//                }
//                else{
//                    cocktaildb.IslikeDao().unlike(cocktail.id)
//                    searchService.DisLike(getaccesstoken(requireContext()),cocktail.id)
//                }
//                recyclerViewState = binding.searchMainRv.layoutManager!!.onSaveInstanceState()
//                setCocktailList(cocktaillist)
//            }
//            private fun changeDetailFragment(cocktail: Cocktail_SearchList) {
//                (activity as MainActivity).detailcocktail(cocktail.id)
//                val spf = requireContext().getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
//                val editor: SharedPreferences.Editor = spf?.edit()!!
//                editor.putInt("currenttab", -1)
//                editor.apply()
//            }
//        })
//        binding.searchMainRv.layoutManager!!.onRestoreInstanceState(recyclerViewState)
//    }
//
//    // 필터 클릭됬을 때
    private fun FilterClcikListener() {
        binding.mainFilterBackgroundcoverIv.setOnClickListener {
            //아무것도 안하기
            ShowFilter(false)
//            isfilterring = false
            binding.mainFilterBackgroundcoverIv.postDelayed({
                if (searchMode == 0) {
                    KeywordReset()
                }
            }, 300)
        }

        binding.mainFilterExitIv.setOnClickListener {
            ShowFilter(false)
//            isfilterring = false
            binding.mainFilterExitIv.postDelayed({
                if (searchMode == 0) {
                    KeywordReset()
                }
            }, 300)
        }

        //적용
        binding.mainFilterAdjustBt.setOnClickListener {
            ShowFilter(false)
//            isfilterring = false
            currentpage = 0
            searchMode = 1
            scrollFlag = true

            val drink_dum =
                gijulist.toArray(arrayOfNulls<String>(gijulist.size)).toList() as List<String>
            drink_foradapter = ArrayList()
            drink_foradapter.addAll(gijulist)

            val keyword_dum = favorkeyword.toArray(arrayOfNulls<String>(favorkeyword.size))
                .toList() as List<String>
            keyword_foradapter = ArrayList()
            keyword_foradapter.addAll(favorkeyword)

            CoroutineScope(Dispatchers.IO).launch {
                searchService.filter(getaccesstoken(requireContext()),0, keyword_dum, dosumin, dosumax, drink_dum)
            }
            val animTransRight: Animation = AnimationUtils
                .loadAnimation(activity, R.anim.vertical_in)
            animTransRight.duration = 700
            binding.mainBgWhiteboardIv.startAnimation(animTransRight)

            val animation: Animation = AlphaAnimation(0f, 1f)
            animation.duration = 1500
            binding.searchFilterGijuTv.animation = animation
            binding.searchFilterKeywordTv.animation = animation
            binding.searchFilterDosuTv.animation = animation
            binding.searchFilterSmallLineIv.animation = animation
            binding.seracgFilterGijuResultRv.animation = animation
            binding.seracgFilterKeywordResultRv.animation = animation
            binding.searchFilterDosuResultTv.animation = animation
            binding.searchBackIv.animation = animation
            binding.searchFilterMaintv.animation = animation
            binding.searchMainRv.animation = animation

            binding.searchFilterGijuTv.visibility = View.VISIBLE
            binding.searchFilterDosuTv.visibility = View.VISIBLE
            binding.searchFilterKeywordTv.visibility = View.VISIBLE
            binding.searchFilterSmallLineIv.visibility = View.VISIBLE
            binding.seracgFilterKeywordResultRv.visibility = View.VISIBLE
            binding.seracgFilterGijuResultRv.visibility = View.VISIBLE
            binding.searchFilterDosuResultTv.visibility = View.VISIBLE
            binding.searchSearchbarLv.visibility = View.INVISIBLE
            binding.searchBackIv.visibility = View.VISIBLE
            binding.searchFilterMaintv.visibility = View.VISIBLE
            binding.searchFilterBlankTv.visibility = View.GONE

            val gijuresultAdapter = FilterresulterAdapter(drink_foradapter)
            binding.seracgFilterGijuResultRv.adapter = gijuresultAdapter

            gijuresultAdapter.setMyItemClickListener(object :
                FilterresulterAdapter.MyItemClickListener {
                override fun onItemClick(cocktail: String) {
                }
                override fun removestr(resultstr: String, position: Int) {
                    gijulist.remove("resultstr")
                    gijuresultAdapter.removeItem(position)
                    val keyword_dum =
                        keyword_foradapter.toArray(arrayOfNulls<String>(keyword_foradapter.size))
                            .toList() as List<String>
                    val drink_dum =
                        drink_foradapter.toArray(arrayOfNulls<String>(drink_foradapter.size))
                            .toList() as List<String>
                    CoroutineScope(Dispatchers.IO).launch {
                        searchService.filter(getaccesstoken(requireContext()),0, keyword_dum, dosumin, dosumax, drink_dum)
                    }
                    scrollFlag = true
                    currentpage = 0
                }
            })

            val keywordresultAdapter = FilterresulterAdapter(keyword_foradapter)
            binding.seracgFilterKeywordResultRv.adapter = keywordresultAdapter

            keywordresultAdapter.setMyItemClickListener(object :
                FilterresulterAdapter.MyItemClickListener {
                override fun onItemClick(cocktail: String) {
                }

                override fun removestr(resultstr: String, position: Int) {
                    gijulist.remove("resultstr")
                    keywordresultAdapter.removeItem(position)
                    val keyword_dum =
                        keyword_foradapter.toArray(arrayOfNulls<String>(keyword_foradapter.size))
                            .toList() as List<String>
                    val drink_dum =
                        drink_foradapter.toArray(arrayOfNulls<String>(drink_foradapter.size))
                            .toList() as List<String>
                    CoroutineScope(Dispatchers.IO).launch {
                        searchService.filter(getaccesstoken(requireContext()),0, keyword_dum, dosumin, dosumax, drink_dum)
                    }
                    scrollFlag = true
                    currentpage = 0
                }
            })
            binding.searchFilterDosuResultTv.text = dosumin.toString() + "도 ~ " + dosumax.toString() + "도"
            //페이징되게
            scrollFlag = true
            currentpage = 0
        }

        binding.searchBackIv.setOnClickListener {
            val animTransRight: Animation = AnimationUtils
                .loadAnimation(activity, R.anim.vertical_out)
            animTransRight.duration = 700
            binding.mainBgWhiteboardIv.startAnimation(animTransRight)
            val animation = AlphaAnimation(0f, 1f)
            animation.duration = 500
            binding.searchSearchbarLv.animation = animation

            val animation2: Animation = AlphaAnimation(1f, 0f)
            animation.duration = 500
            binding.searchFilterGijuTv.animation = animation2
            binding.searchFilterKeywordTv.animation = animation2
            binding.searchFilterDosuTv.animation = animation2
            binding.searchFilterSmallLineIv.animation = animation2
            binding.seracgFilterGijuResultRv.animation = animation2
            binding.seracgFilterKeywordResultRv.animation = animation2
            binding.searchFilterDosuResultTv.animation = animation2
            binding.searchBackIv.animation = animation2
            binding.searchFilterMaintv.animation = animation2
            binding.searchMainRv.animation = animation

            binding.searchSearchbarLv.visibility = View.VISIBLE
            binding.searchBackIv.visibility = View.GONE
            binding.searchFilterMaintv.visibility = View.INVISIBLE
            binding.searchFilterBlankTv.visibility = View.INVISIBLE
            binding.searchFilterGijuTv.visibility = View.GONE
            binding.searchFilterDosuTv.visibility = View.GONE
            binding.searchFilterKeywordTv.visibility = View.GONE
            binding.searchFilterSmallLineIv.visibility = View.GONE
            binding.seracgFilterKeywordResultRv.visibility = View.GONE
            binding.seracgFilterGijuResultRv.visibility = View.GONE
            binding.searchFilterDosuResultTv.visibility = View.GONE
            KeywordReset()
            CoroutineScope(Dispatchers.IO).launch {
                // 스크롤 가능하게
                scrollFlag =true
                //검색모드
                searchMode = 0
                // 현재 페이지
                currentpage = 0
                searchService.search(getaccesstoken(requireContext())," ")
            }

        }

        binding.mainFilterResetLayout.setOnClickListener {
            KeywordReset()
        }

        // 기주리스트는 gijulist로 들어감
        SetGijuKeyword()
        // 키워드리스트는 keywordlist로 들어감
        SetFavorKeyword()

        // Seekbar 리스너
        binding.mainFilterDosuSeekbar.setValueChangedImmediately(true)
        binding.mainFilterDosuSeekbar.setOnValueChangedListener(object : DiscreteSlider.OnValueChangedListener() {
            override fun onValueChanged(minProgress: Int, maxProgress: Int, fromUser: Boolean) {
                super.onValueChanged(minProgress, maxProgress, fromUser)
                changedosutv(minProgress, maxProgress)
                dosumin = minProgress
                dosumax = maxProgress
            }
        })

    }
    private fun initSelected() {
        val gijuTemp = arrayListOf(
            binding.mainFilterGijuVodcaBt,
            binding.mainFilterGijuRumBt,
            binding.mainFilterGijuTequilaBt,
            binding.mainFilterGijuWiskiBt,
            binding.mainFilterGijuBrandyBt,
            binding.mainFilterGijuLiqueurBt,
            binding.mainFilterGijuJinBt
        )
        for (giju in gijuTemp) {
            giju.isChecked = giju.text in drink_foradapter
        }
        val favorTemp = arrayListOf(
            binding.mainFilterKeywordLadykillerBt,
            binding.mainFilterKeywordShooterBt,
            binding.mainFilterKeywordCleanBt,
            binding.mainFilterKeywordTansanBt,
            binding.mainFilterKeywordLayeredBt,
            binding.mainFilterKeywordMartiniBt,
            binding.mainFilterKeywordPrettyBt,
            binding.mainFilterKeywordHighballBt,
            binding.mainFilterKeywordSweetBt,
            binding.mainFilterKeywordDockhanBt,
            binding.mainFilterKeywordSangqueBt,
            binding.mainFilterKeywordFluitfavorBt,
            binding.mainFilterKeywordSsupssupBt,
            binding.mainFilterKeywordOntherrockBt,
            binding.mainFilterKeywordSangkumBt,
            binding.mainFilterKeywordDansunBt,
            binding.mainFilterKeywordMilkBt,
            binding.mainFilterKeywordBockjapBt
        )
        for (favor in favorTemp) {
            favor.isChecked = favor.text in keyword_foradapter
        }
    }

    private fun changedosutv(mindosu: Int, maxdosu: Int) {
        binding.mainFilterDosunumTv.text = mindosu.toString() + "도 ~ " + maxdosu.toString() + "도"
    }

    private fun KeywordReset() {
        drink_foradapter = ArrayList()
        keyword_foradapter = ArrayList()

        //도수
        binding.mainFilterDosuSeekbar.minProgress = 10
        binding.mainFilterDosuSeekbar.maxProgress = 30

        //기주
        binding.mainFilterGijuVodcaBt.isChecked = false
        binding.mainFilterGijuWiskiBt.isChecked = false
        binding.mainFilterGijuRumBt.isChecked = false
        binding.mainFilterGijuJinBt.isChecked = false
        binding.mainFilterGijuTequilaBt.isChecked = false
        binding.mainFilterGijuLiqueurBt.isChecked = false
        binding.mainFilterGijuBrandyBt.isChecked = false

        //취향키워드
        binding.mainFilterKeywordLadykillerBt.isChecked = false
        binding.mainFilterKeywordShooterBt.isChecked = false
        binding.mainFilterKeywordCleanBt.isChecked = false
        binding.mainFilterKeywordTansanBt.isChecked = false
        binding.mainFilterKeywordLayeredBt.isChecked = false
        binding.mainFilterKeywordMartiniBt.isChecked = false
        binding.mainFilterKeywordPrettyBt.isChecked = false
        binding.mainFilterKeywordHighballBt.isChecked = false
        binding.mainFilterKeywordSweetBt.isChecked = false
        binding.mainFilterKeywordDockhanBt.isChecked = false
        binding.mainFilterKeywordSangqueBt.isChecked = false
        binding.mainFilterKeywordFluitfavorBt.isChecked = false
        binding.mainFilterKeywordSsupssupBt.isChecked = false
        binding.mainFilterKeywordOntherrockBt.isChecked = false
        binding.mainFilterKeywordSangkumBt.isChecked = false
        binding.mainFilterKeywordDansunBt.isChecked = false
        binding.mainFilterKeywordMilkBt.isChecked = false
        binding.mainFilterKeywordBockjapBt.isChecked = false
    }

    private fun SetFavorKeyword() {
        val favorListner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.main_filter_keyword_ladykiller_bt -> {
                        favorkeyword.add("레이디킬러")
                    }
                    R.id.main_filter_keyword_shooter_bt -> {
                        favorkeyword.add("슈터")
                    }
                    R.id.main_filter_keyword_clean_bt -> {
                        favorkeyword.add("깔끔한")
                    }
                    R.id.main_filter_keyword_tansan_bt -> {
                        favorkeyword.add("탄산")
                    }
                    R.id.main_filter_keyword_layered_bt -> {
                        favorkeyword.add("레이어드")
                    }
                    R.id.main_filter_keyword_martini_bt -> {
                        favorkeyword.add("마티니")
                    }
                    R.id.main_filter_keyword_pretty_bt -> {
                        favorkeyword.add("예쁜")
                    }
                    R.id.main_filter_keyword_highball_bt -> {
                        favorkeyword.add("하이볼")
                    }
                    R.id.main_filter_keyword_sweet_bt -> {
                        favorkeyword.add("달달한")
                    }
                    R.id.main_filter_keyword_dockhan_bt -> {
                        favorkeyword.add("독한")
                    }
                    R.id.main_filter_keyword_sangque_bt -> {
                        favorkeyword.add("상쾌한")
                    }
                    R.id.main_filter_keyword_fluitfavor_bt -> {
                        favorkeyword.add("과일향")
                    }
                    R.id.main_filter_keyword_ssupssup_bt -> {
                        favorkeyword.add("씁쓸한")
                    }
                    R.id.main_filter_keyword_ontherrock_bt -> {
                        favorkeyword.add("온더락")
                    }
                    R.id.main_filter_keyword_sangkum_bt -> {
                        favorkeyword.add("상큼한")
                    }
                    R.id.main_filter_keyword_dansun_bt -> {
                        favorkeyword.add("단순한")
                    }
                    R.id.main_filter_keyword_milk_bt -> {
                        favorkeyword.add("우유")
                    }
                    R.id.main_filter_keyword_bockjap_bt -> {
                        favorkeyword.add("복잡한")
                    }
                }
            } else {
                when (buttonView.id) {
                    R.id.main_filter_keyword_ladykiller_bt -> {
                        favorkeyword.remove("레이디킬러")
                    }
                    R.id.main_filter_keyword_shooter_bt -> {
                        favorkeyword.remove("슈터")
                    }
                    R.id.main_filter_keyword_clean_bt -> {
                        favorkeyword.remove("깔끔한")
                    }
                    R.id.main_filter_keyword_tansan_bt -> {
                        favorkeyword.remove("탄산")
                    }
                    R.id.main_filter_keyword_layered_bt -> {
                        favorkeyword.remove("레이어드")
                    }
                    R.id.main_filter_keyword_martini_bt -> {
                        favorkeyword.remove("마티니")
                    }
                    R.id.main_filter_keyword_pretty_bt -> {
                        favorkeyword.remove("예쁜")
                    }
                    R.id.main_filter_keyword_highball_bt -> {
                        favorkeyword.remove("하이볼")
                    }
                    R.id.main_filter_keyword_sweet_bt -> {
                        favorkeyword.remove("달달한")
                    }
                    R.id.main_filter_keyword_dockhan_bt -> {
                        favorkeyword.remove("독한")
                    }
                    R.id.main_filter_keyword_sangque_bt -> {
                        favorkeyword.remove("상쾌한")
                    }
                    R.id.main_filter_keyword_fluitfavor_bt -> {
                        favorkeyword.remove("과일향")
                    }
                    R.id.main_filter_keyword_ssupssup_bt -> {
                        favorkeyword.remove("씁쓸한")
                    }
                    R.id.main_filter_keyword_ontherrock_bt -> {
                        favorkeyword.remove("온더락")
                    }
                    R.id.main_filter_keyword_sangkum_bt -> {
                        favorkeyword.remove("상큼한")
                    }
                    R.id.main_filter_keyword_dansun_bt -> {
                        favorkeyword.remove("단순한")
                    }
                    R.id.main_filter_keyword_milk_bt -> {
                        favorkeyword.remove("우유")
                    }
                    R.id.main_filter_keyword_bockjap_bt -> {
                        favorkeyword.remove("복잡한")
                    }
                }
            }
        }
        binding.mainFilterKeywordLadykillerBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordShooterBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordCleanBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordTansanBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordLayeredBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordMartiniBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordPrettyBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordHighballBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordSweetBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordDockhanBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordSangqueBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordFluitfavorBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordSsupssupBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordOntherrockBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordSangkumBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordDansunBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordMilkBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordBockjapBt.setOnCheckedChangeListener(favorListner)
    }

    private fun SetGijuKeyword() {
        val gijulistner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.main_filter_giju_vodca_bt -> {
                        gijulist.add("보드카")
                    }
                    R.id.main_filter_giju_wiski_bt -> {
                        gijulist.add("위스키")
                    }
                    R.id.main_filter_giju_rum_bt -> {
                        gijulist.add("럼")
                    }
                    R.id.main_filter_giju_jin_bt -> {
                        gijulist.add("진")
                    }
                    R.id.main_filter_giju_tequila_bt -> {
                        gijulist.add("데킬라")
                    }
                    R.id.main_filter_giju_liqueur_bt -> {
                        gijulist.add("리큐르")
                    }
                    R.id.main_filter_giju_brandy_bt -> {
                        gijulist.add("브랜디")
                    }
//                    R.id.main_filter_giju_anything_bt -> {
//                        gijulist.add("상관없음")
//                    }
                }
            } else {
                when (buttonView.id) {
                    R.id.main_filter_giju_vodca_bt -> {
                        gijulist.remove("보드카")
                    }
                    R.id.main_filter_giju_wiski_bt -> {
                        gijulist.remove("위스키")
                    }
                    R.id.main_filter_giju_rum_bt -> {
                        gijulist.remove("럼")
                    }
                    R.id.main_filter_giju_jin_bt -> {
                        gijulist.remove("진")
                    }
                    R.id.main_filter_giju_tequila_bt -> {
                        gijulist.remove("데킬라")
                    }
                    R.id.main_filter_giju_liqueur_bt -> {
                        gijulist.remove("리큐르")
                    }
                    R.id.main_filter_giju_brandy_bt -> {
                        gijulist.remove("브랜디")
                    }
//                    R.id.main_filter_giju_anything_bt -> {
//                        gijulist.remove("상관없음")
//                    }
                }
            }
        }
        binding.mainFilterGijuVodcaBt.setOnCheckedChangeListener(gijulistner)
        binding.mainFilterGijuWiskiBt.setOnCheckedChangeListener(gijulistner)
        binding.mainFilterGijuRumBt.setOnCheckedChangeListener(gijulistner)
        binding.mainFilterGijuJinBt.setOnCheckedChangeListener(gijulistner)
        binding.mainFilterGijuTequilaBt.setOnCheckedChangeListener(gijulistner)
        binding.mainFilterGijuLiqueurBt.setOnCheckedChangeListener(gijulistner)
        binding.mainFilterGijuBrandyBt.setOnCheckedChangeListener(gijulistner)
//        binding.mainFilterGijuAnythingBt.setOnCheckedChangeListener(gijulistner)
    }

    fun ShowFilter(isshow: Boolean) {
        if (isshow) {
            val animation2: Animation = AlphaAnimation(0f, 1f)
            animation2.duration = 500
            binding.mainFilterBackgroundcoverIv.animation = animation2
            (activity as MainActivity).hidebottomnavation()
            val animation: Animation = TranslateAnimation(0f, 0f, 500f, 0f)
            animation.duration = 300
            binding.mainFilterBackLayout.animation = animation
            binding.mainFilterBackLayout.visibility = View.VISIBLE
            binding.mainFilterBackgroundcoverIv.visibility = View.VISIBLE
        } else {
            val animation2: Animation = AlphaAnimation(1f, 0.2f)
            animation2.duration = 300
            binding.mainFilterBackgroundcoverIv.animation = animation2
            var animation: Animation = AlphaAnimation(0f, 1f)
            animation.duration = 500
            (activity as MainActivity).showbottomnavation()
            binding.mainFilterBackLayout.animation = animation
            animation = TranslateAnimation(0f, 0f, 0f, 1500f)
            animation.setDuration(500)
            binding.mainFilterBackLayout.animation = animation
            binding.mainFilterBackLayout.visibility = View.GONE
            binding.mainFilterBackgroundcoverIv.visibility = View.GONE
        }
    }
//
//        //서치 뷰
//    override fun onSearchLoading() {
//        val activity: Activity? = activity
//        if ( isAdded() && activity != null) {
////            binding.searchLoadingBar.visibility = View.VISIBLE
//        }
//    }
//
//    override fun onSearchSuccess(searchresult: SearchResult) {
//        val activity: Activity? = activity
//        if ( isAdded() && activity != null) {
//            requireActivity().runOnUiThread {
//                cocktaillist = ArrayList()
//                for (i in searchresult.cocktailList) {
//                    cocktaillist.add(
//                        Cocktail_SearchList(
//                            i.koreanName,
//                            i.englishName,
//                            i.keywords,
//                            i.smallNukkiImageURL,
//                            i.ratingAvg,
//                            i.alcoholLevel,
//                            "기주",
//                            i.cocktailInfoId
//                        )
//                    )
//                }
//                setCocktailList(cocktaillist)
//                totalcnt = cocktaillist.size
//                binding.searchLoadingBar.visibility = View.GONE
//                binding.searchResultTv.text = totalcnt.toString() + "개의 검색결과"
//            }
//        }
//    }
//
//    override fun onSearchFailure(code: Int, message: String) {
//        val activity: Activity? = activity
//        if (isAdded() && activity != null) {
//            requireActivity().runOnUiThread {
//                binding.searchLoadingBar.visibility = View.GONE
//                if (code == 5000) {
//                    Log.d("refreshtoken", getrefreshtoken(requireContext()))
//                    userService.TokenRefresh(getrefreshtoken(requireContext()))
//                }
//            }
//        }
//    }
//
    //페이징 뷰
    override fun onPagingLoading() {
        requireActivity().runOnUiThread {
            binding.searchProgressbar.visibility = View.VISIBLE
        }
    }

    override fun onPagingSuccess(searchresult: SearchResult) {
        val activity: Activity? = activity
        ispagingnow = false
        if (isAdded() && activity != null) {
            // 뷰모델 더하기로 업데이트
            searchCocktailViewModel.addCocktailList(searchresult)
            totalcnt += searchresult.cocktailList.size
            requireActivity().runOnUiThread {
                binding.searchProgressbar.visibility = View.GONE
                binding.searchResultTv.text = "${totalcnt}개의 검색결과"
            }
            if (searchresult.cocktailList.isEmpty()) {
                scrollFlag = false
            }
        }
    }

    override fun onPagingFailure(code: Int, message: String) {
        val activity: Activity? = activity
        ispagingnow = false
        if ( isAdded() && activity != null) {
            requireActivity().runOnUiThread {
               binding.searchProgressbar.visibility = View.GONE
            }
        }
    }


    fun requsetnextpage() {
//        val spf = activity?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
        currentpage += 1
        CoroutineScope(Dispatchers.IO).launch{
            searchService.paging(getaccesstoken(requireContext()),currentpage, searchCocktailViewModel.searchStr.value.toString())
        }
    }


    //    //필터 뷰
    override fun onFilterLoading() {
        requireActivity().runOnUiThread {
        binding.searchLoadingBar.visibility = View.VISIBLE
        binding.searchProgressbar.visibility = View.VISIBLE
        }
    }

    override fun onFilterSuccess(searchresult: SearchResult) {
        val activity: Activity? = activity
        if ( isAdded() && activity != null) {
            totalcnt = searchresult.cocktailList.size
            searchCocktailViewModel.setCocktail(searchresult.cocktailList)

            // 검색어 초기화
            searchCocktailViewModel.setSearchStr(" ")
//            val spf = context?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
//            val editor: SharedPreferences.Editor = spf?.edit()!!
//            editor.putString("searchstr", " ")
//            editor.apply()

            // 필터모드 진입
            searchMode = 1

            // 비어있으면 스크롤 비활성화
            if (searchresult.cocktailList.isEmpty()) {
                scrollFlag = false
            }

            requireActivity().runOnUiThread {
                binding.searchSearchbarExiticonIv.visibility = View.GONE
                binding.searchLoadingBar.visibility = View.GONE
                binding.searchProgressbar.visibility = View.GONE
                binding.searchResultTv.text = "${totalcnt}개의 검색결과"
//                binding.searchSearchbarTv.text = "검색어를 입력해주세요."
                binding.searchMainRv.scrollToPosition(0)
            }
        }

    }

    override fun onFilterFailure(code: Int, message: String) {
        val activity: Activity? = activity
        if ( isAdded() && activity != null) {
            requireActivity().runOnUiThread {
                binding.searchLoadingBar.visibility = View.GONE
                binding.searchProgressbar.visibility = View.GONE
            }
        }
    }



    fun requsetnextpagefor_filter() {
        currentpage += 1
        val keyword_dum = keyword_foradapter.toArray(arrayOfNulls<String>(keyword_foradapter.size))
            .toList() as List<String>
        val drink_dum = drink_foradapter.toArray(arrayOfNulls<String>(drink_foradapter.size))
            .toList() as List<String>
        CoroutineScope(Dispatchers.IO).launch {
            searchService.filterpaging(getaccesstoken(requireContext()),currentpage, keyword_dum, dosumin, dosumax, drink_dum)
        }
    }
//
//    override fun onFilterpagingLoading() {
//        isfilterscroll = false
//
//    }
//
//    override fun onFilterpagingSuccess(searchresult: SearchResult) {
//        val activity: Activity? = activity
//        isfilterscroll = true
//        if ( isAdded() && activity != null) {
//            for (i in searchresult.cocktailList) {
//                cocktaillist.add(
//                    Cocktail_SearchList(
//                        i.koreanName,
//                        i.englishName,
//                        i.keywords,
//                        i.smallNukkiImageURL,
//                        i.ratingAvg,
//                        i.alcoholLevel,
//                        "기주",
//                        i.cocktailInfoId
//                    )
//                )
//            }
//            totalcnt += searchresult.cocktailList.size
//            requireActivity().runOnUiThread {
//                binding.searchProgressbar.visibility = View.GONE
//                binding.searchResultTv.text = (totalcnt).toString() + "개의 검색결과"
//            }
//            if (searchresult.cocktailList.isEmpty()) {
//                scrollFlag = false
//            }
//        }
//    }
//
//    override fun onFilterpagingFailure(code: Int, message: String) {
//        isfilterscroll = false
//    }
//
//    override fun onTokenRefreshLoading() {
//    }
//
//    override fun onTokenRefreshSuccess(tokenSigninbody: Tokenrespbody) {
//        setaccesstoken(requireContext(),tokenSigninbody.token)
//        setrefreshtoken(requireContext(),tokenSigninbody.refreshToken)
//    }
//
//    override fun onTokenRefreshFailure(code: Int, message: String) {
//    }
//
//    override fun onIsLikeLoading() {
//    }
//
//    override fun onIsLikeSuccess(isLikeResponse: IsLikeResponse) {
//    }
//
//    override fun onIsLikeFailure(code: Int, message: String) {
//    }

}