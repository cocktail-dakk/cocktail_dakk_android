package com.example.cocktail_dakk.ui.search

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail_SearchList
import com.example.cocktail_dakk.data.entities.cocktaildata_db.CocktailDatabase
import com.example.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_recentSearch
import com.example.cocktail_dakk.databinding.FragmentSearchBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.MainActivity
import com.example.cocktail_dakk.ui.menu_detail.MenuDetailActivity
import com.example.cocktail_dakk.ui.menu_detail.detailService.DetailService
import com.example.cocktail_dakk.ui.menu_detail.detailService.DetailView
import com.example.cocktail_dakk.ui.menu_detail.detailService.detail_Cocktail
import com.example.cocktail_dakk.ui.search.searchService.*
import com.example.cocktail_dakk.ui.search.searchService.SearchView
import com.example.cocktail_dakk.ui.search_tab.SearchTabActivity
import com.example.cocktail_dakk.ui.search_tab.adapter.RecentSearchKeywordRvAdapter
import com.google.gson.Gson
import hearsilent.discreteslider.DiscreteSlider
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchView, PagingView, FilterView, FilterpagingView {

    val gson: Gson = Gson()
    var currentpage = 0
    var totalcnt = 10
    var cocktaillist: ArrayList<Cocktail_SearchList> = ArrayList()
    var searchService = SearchService()
    var detailService = DetailService()
    var searchMode: Int = 0 //0이면 검색 1이면 필터
    var filterFlag: Boolean = true

    var gijulist = ArrayList<String>()
    var favorkeyword = ArrayList<String>()
    var drink_foradapter = ArrayList<String>()
    var keyword_foradapter = ArrayList<String>()


    var dosumin: Int = 10
    var dosumax: Int = 30

    lateinit var searchListAdapter: SearchlistRvAdapter
    override fun initAfterBinding() {
        binding.searchSearchbarLv.visibility = View.VISIBLE
        setCurrentPage()
        setOnClickListener()
        FilterClcikListener()

    }


    private fun setCurrentPage() {
        var spf = activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab", 0)
        editor.commit()
    }

    override fun onResume() {
        super.onResume()
        var spf = activity?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)

        searchService.setsearchView(this)
        searchService.setpagingView(this)
        searchService.setfilterView(this)
        searchService.setfilterPagingView(this)
        launch {
            //검색모드
            searchMode = 0
            // 현재 페이지
            currentpage = 0
            filterFlag = true
            //리스트 갯수
            totalcnt = 0
            searchService.search(spf!!.getString("searchstr", " ").toString().trim())

            //DB 최근검색어 넣기 중복체크 후 인설트
            if (spf!!.getString("searchstr", " ")!!.trim() != " " || spf!!.getString("searchstr", " ")!!
                    .trim() == ""
            ) {
                val CocktailDB = CocktailDatabase.getInstance(requireContext())!!
                CocktailDB.RecentSearchDao()
                    .duplicatecheck(spf!!.getString("searchstr", " ").toString().trim())
                CocktailDB.RecentSearchDao()
                    .insert(Cocktail_recentSearch(spf!!.getString("searchstr", " ").toString().trim()))
            }
        }
        binding.mainFilterDosuSeekbar.minProgress = dosumin
        binding.mainFilterDosuSeekbar.maxProgress = dosumax

        //검색어 설정
        val text = spf!!.getString("searchstr", " ")?.trim()
        if (text == " " || text == "") {
            binding.searchSearchbarExiticonIv.visibility = View.GONE
            binding.searchSearchbarTv.setText("검색어를 입력해주세요.")
        } else {
            binding.searchSearchbarExiticonIv.visibility = View.VISIBLE
            binding.searchSearchbarTv.setText(text)
        }

        //스크롤, 페이징, 처리
        SetMainRvScrollListener()

    }

    private fun SetMainRvScrollListener() {
        val onScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && filterFlag == true
                    && searchMode == 0
                ) {
                    //터치막기
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                    binding.searchProgressbar.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed({
                        requsetnextpage()
                    }, 300)
                }
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && filterFlag == true
                    && searchMode == 1
                ) {
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
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

        //서치바
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
//            (activity as MainActivity).ShowFilter(true)
            ShowFilter(true)
            initSelected()
        }

        //exiticon
        binding.searchSearchbarExiticonIv.setOnClickListener {
            var spf = context?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = spf?.edit()!!
            editor.putString("searchstr", " ")
            editor.apply()
            filterFlag = true
            searchMode = 0
            currentpage = 0
            totalcnt = 0
            onResume()
        }
    }

    private fun setCocktailList(cocktaillist: ArrayList<Cocktail_SearchList>) {
        searchListAdapter = SearchlistRvAdapter(cocktaillist)
        binding.searchMainRv.adapter = searchListAdapter
        searchListAdapter.setClickListiner(object : SearchlistRvAdapter.MyItemClickListener {
            override fun onItemClick(cocktail: Cocktail_SearchList) {
                changeDetailFragment(cocktail)
            }

            private fun changeDetailFragment(cocktail: Cocktail_SearchList) {
                (activity as MainActivity).detailcocktail(cocktail.id)
            }
        })
    }

    private fun FilterClcikListener() {
        binding.mainFilterBackgroundcoverIv.setOnClickListener {
            //아무것도 안하기
            ShowFilter(false)
            binding.mainFilterBackgroundcoverIv.postDelayed(object : Runnable {
                override fun run() {
                    if (searchMode == 0) {
                        KeywordReset()
                    }
                }
            }, 300)
        }

        binding.mainFilterExitIv.setOnClickListener {
            ShowFilter(false)
            binding.mainFilterExitIv.postDelayed(object : Runnable {
                override fun run() {
                    if (searchMode == 0) {
                        KeywordReset()
                    }
                }
            }, 300)
        }

        //적용
        binding.mainFilterAdjustBt.setOnClickListener {
            ShowFilter(false)
            currentpage = 0
            searchMode = 1
            filterFlag = true

            var drink_dum =
                gijulist.toArray(arrayOfNulls<String>(gijulist.size)).toList() as List<String>
            drink_foradapter = ArrayList()
            drink_foradapter.addAll(gijulist)

            var keyword_dum = favorkeyword.toArray(arrayOfNulls<String>(favorkeyword.size))
                .toList() as List<String>
            keyword_foradapter = ArrayList()
            keyword_foradapter.addAll(favorkeyword)

            launch {
                searchService.filter(0, keyword_dum, dosumin, dosumax, drink_dum)
            }
            var animTransRight: Animation = AnimationUtils
                .loadAnimation(activity, R.anim.vertical_in)
            animTransRight.duration = 700
            binding.mainBgWhiteboardIv.startAnimation(animTransRight)

            var animation: Animation = AlphaAnimation(0f, 1f);
            animation.setDuration(1500)
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

            var gijuresultAdapter = FilterresulterAdapter(drink_foradapter)
            binding.seracgFilterGijuResultRv.adapter = gijuresultAdapter

            gijuresultAdapter.setMyItemClickListener(object :
                FilterresulterAdapter.MyItemClickListener {
                override fun onItemClick(cocktail: String) {
                }
                override fun removestr(resultstr: String, position: Int) {
                    gijulist.remove("resultstr")
                    gijuresultAdapter.removeItem(position)
                    var keyword_dum =
                        keyword_foradapter.toArray(arrayOfNulls<String>(keyword_foradapter.size))
                            .toList() as List<String>
                    var drink_dum =
                        drink_foradapter.toArray(arrayOfNulls<String>(drink_foradapter.size))
                            .toList() as List<String>
                    launch {
                        searchService.filter(0, keyword_dum, dosumin, dosumax, drink_dum)
                    }
                    filterFlag = true
                    currentpage = 0
                }
            })

            var keywordresultAdapter = FilterresulterAdapter(keyword_foradapter)
            binding.seracgFilterKeywordResultRv.adapter = keywordresultAdapter

            keywordresultAdapter.setMyItemClickListener(object :
                FilterresulterAdapter.MyItemClickListener {
                override fun onItemClick(cocktail: String) {
                }

                override fun removestr(resultstr: String, position: Int) {
                    gijulist.remove("resultstr")
                    keywordresultAdapter.removeItem(position)
                    var keyword_dum =
                        keyword_foradapter.toArray(arrayOfNulls<String>(keyword_foradapter.size))
                            .toList() as List<String>
                    var drink_dum =
                        drink_foradapter.toArray(arrayOfNulls<String>(drink_foradapter.size))
                            .toList() as List<String>
                    launch {
                        searchService.filter(0, keyword_dum, dosumin, dosumax, drink_dum)
                    }
                    filterFlag = true
                    currentpage = 0
                }
            })

            binding.searchFilterDosuResultTv.setText(dosumin.toString() + "도 ~ " + dosumax.toString() + "도")

            //페이징되게
            filterFlag = true
            currentpage = 0
        }

        binding.searchBackIv.setOnClickListener {
//            ShowFilter(false)
//            currentpage = 0
//            searchMode = 1
//            filterFlag = true
//            var keyword_dum = favorkeyword.toArray(arrayOfNulls<String>(favorkeyword.size)).toList() as List<String>
//            var drink_dum = gijulist.toArray(arrayOfNulls<String>(gijulist.size)).toList() as List<String>
//            searchService.filter(0, keyword_dum,dosumin,dosumax,drink_dum)


            var animTransRight: Animation = AnimationUtils
                .loadAnimation(activity, R.anim.vertical_out)
            animTransRight.duration = 700
            binding.mainBgWhiteboardIv.startAnimation(animTransRight)
            var animation: Animation = AlphaAnimation(0f, 1f);
            animation.setDuration(500)
            binding.searchSearchbarLv.animation = animation

            var animation2: Animation = AlphaAnimation(1f, 0f);
            animation.setDuration(500)
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
            onResume()
        }

        binding.mainFilterResetLayout.setOnClickListener {
            KeywordReset()
        }

        //기주리스트는 gijulist로 들어감
        SetGijuKeyword()
        //키워드리스트는 keywordlist로 들어감
        SetFavorKeyword()

        //Seekbar 리스너
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
        var gijuTemp = arrayListOf(
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
        var favorTemp = arrayListOf(
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
        binding.mainFilterDosunumTv.setText(mindosu.toString() + "도 ~ " + maxdosu.toString() + "도")
    }

    private fun KeywordReset() {

        drink_foradapter = ArrayList<String>()
        keyword_foradapter = ArrayList<String>()

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
//        binding.mainFilterGijuAnythingBt.isChecked = false

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
        var favorListner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
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
        var gijulistner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
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
            var animation2: Animation = AlphaAnimation(0f, 1f);
            animation2.setDuration(500)
            binding.mainFilterBackgroundcoverIv.animation = animation2
            (activity as MainActivity).hidebottomnavation()
            var animation: Animation = TranslateAnimation(0f, 0f, 500f, 0f);
            animation.setDuration(300)
            binding.mainFilterBackLayout.animation = animation
            binding.mainFilterBackLayout.visibility = View.VISIBLE
            binding.mainFilterBackgroundcoverIv.visibility = View.VISIBLE
        } else {
            var animation2: Animation = AlphaAnimation(1f, 0.2f);
            animation2.setDuration(300)
            binding.mainFilterBackgroundcoverIv.animation = animation2
            var animation: Animation = AlphaAnimation(0f, 1f);
            animation.setDuration(500)
            (activity as MainActivity).showbottomnavation()
            binding.mainFilterBackLayout.animation = animation
            animation = TranslateAnimation(0f, 0f, 0f, 1500f);
            animation.setDuration(500)
            binding.mainFilterBackLayout.animation = animation
            binding.mainFilterBackLayout.visibility = View.GONE
            binding.mainFilterBackgroundcoverIv.visibility = View.GONE
        }
    }

    //서치 뷰
    override fun onSearchLoading() {
        requireActivity().runOnUiThread(object : Runnable{
             override fun run() {
                 requireActivity().window.setFlags(
                     WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                     WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                 )
                 binding.searchLoadingBar.visibility = View.VISIBLE
             }
        })
    }

    override fun onSearchSuccess(searchresult: SearchResult) {
        cocktaillist = ArrayList()
        for (i in searchresult.cocktailList) {
            cocktaillist.add(
                Cocktail_SearchList(
                    i.koreanName,
                    i.englishName,
                    i.keywords,
                    i.smallNukkiImageURL,
                    i.ratingAvg,
                    i.alcoholLevel,
                    "기주",
                    i.cocktailInfoId
                )
            )
        }
        setCocktailList(cocktaillist)
        totalcnt = cocktaillist.size
        requireActivity().runOnUiThread(object : Runnable{
            override fun run() {
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                binding.searchLoadingBar.visibility = View.GONE
                binding.searchResultTv.text = totalcnt.toString() + "개의 검색결과"
            }
        })
    }

    override fun onSearchFailure(code: Int, message: String) {
        requireActivity().runOnUiThread(object : Runnable{
            override fun run() {
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                binding.searchLoadingBar.visibility = View.GONE
            }
        })
    }


    //페이징 뷰
    override fun onPagingLoading() {
        requireActivity().runOnUiThread(object : Runnable {
            override fun run() {
                requireActivity().window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                binding.searchProgressbar.visibility = View.VISIBLE
            }
            })
    }

    override fun onPagingSuccess(searchresult: SearchResult) {


        for (i in searchresult.cocktailList) {
            cocktaillist.add(
                Cocktail_SearchList(
                    i.koreanName,
                    i.englishName,
                    i.keywords,
                    i.smallNukkiImageURL,
                    i.ratingAvg,
                    i.alcoholLevel,
                    "기주",
                    i.cocktailInfoId
                )
            )
        }
        totalcnt += searchresult.cocktailList.size
        requireActivity().runOnUiThread(object : Runnable {
            override fun run() {
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                binding.searchProgressbar.visibility = View.GONE
                binding.searchResultTv.text = (totalcnt).toString() + "개의 검색결과"
            }
        })
        if (searchresult.cocktailList.size <= 0) {
            filterFlag = false
        }
    }

    override fun onPagingFailure(code: Int, message: String) {
        requireActivity().runOnUiThread(object : Runnable {
            override fun run() {
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                binding.searchProgressbar.visibility = View.GONE
            }
        }  )
    }

    //필터 뷰
    override fun onFilterLoading() {
        requireActivity().runOnUiThread(object : Runnable {
            override fun run() {
                requireActivity().window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                binding.searchLoadingBar.visibility = View.VISIBLE
                binding.searchProgressbar.visibility = View.VISIBLE
            }
        })
    }

    override fun onFilterSuccess(searchresult: SearchResult) {


        cocktaillist = ArrayList()
        for (i in searchresult.cocktailList) {
            cocktaillist.add(
                Cocktail_SearchList(
                    i.koreanName,
                    i.englishName,
                    i.keywords,
                    i.smallNukkiImageURL,
                    i.ratingAvg,
                    i.alcoholLevel,
                    "기주",
                    i.cocktailInfoId
                )
            )
        }
        setCocktailList(cocktaillist)
        totalcnt = cocktaillist.size
//        filterFlag = false
        var spf = context?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putString("searchstr", " ")
        editor.apply()
        searchMode = 1
        binding.searchSearchbarExiticonIv.visibility = View.GONE

        if (searchresult.cocktailList.size <= 0) {
            filterFlag = false
        }

        requireActivity().runOnUiThread(object : Runnable {
            override fun run() {
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                binding.searchLoadingBar.visibility = View.GONE
                binding.searchProgressbar.visibility = View.GONE
                binding.searchResultTv.text = totalcnt.toString() + "개의 검색결과"
                binding.searchSearchbarTv.setText("검색어를 입력해주세요.")
            }
        })

    }

    override fun onFilterFailure(code: Int, message: String) {
        requireActivity().runOnUiThread(object : Runnable {
            override fun run() {
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                binding.searchLoadingBar.visibility = View.GONE
                binding.searchProgressbar.visibility = View.GONE
            }})
    }

    fun requsetnextpage() {
        var spf = activity?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
        currentpage += 1
        launch{
            searchService.paging(currentpage, spf!!.getString("searchstr", " ").toString())
        }
    }

    fun requsetnextpagefor_filter() {
        currentpage += 1
        var keyword_dum = keyword_foradapter.toArray(arrayOfNulls<String>(keyword_foradapter.size))
            .toList() as List<String>
        var drink_dum = drink_foradapter.toArray(arrayOfNulls<String>(drink_foradapter.size))
            .toList() as List<String>
        launch {
            searchService.filterpaging(currentpage, keyword_dum, dosumin, dosumax, drink_dum)
        }
    }

    override fun onFilterpagingLoading() {
        requireActivity().runOnUiThread(object : Runnable {
            override fun run() {
                requireActivity().window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            }
        })
    }

    override fun onFilterpagingSuccess(searchresult: SearchResult) {

        for (i in searchresult.cocktailList) {
            cocktaillist.add(
                Cocktail_SearchList(
                    i.koreanName,
                    i.englishName,
                    i.keywords,
                    i.smallNukkiImageURL,
                    i.ratingAvg,
                    i.alcoholLevel,
                    "기주",
                    i.cocktailInfoId
                )
            )
        }
        totalcnt += searchresult.cocktailList.size
        requireActivity().runOnUiThread(object : Runnable {
            override fun run() {
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                binding.searchProgressbar.visibility = View.GONE
                binding.searchResultTv.text = (totalcnt).toString() + "개의 검색결과"
            }
        })
        if (searchresult.cocktailList.size <= 0) {
            filterFlag = false
        }
    }

    override fun onFilterpagingFailure(code: Int, message: String) {
        requireActivity().runOnUiThread(object : Runnable {
            override fun run() {
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        })
    }
}