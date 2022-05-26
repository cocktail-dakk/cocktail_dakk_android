package com.umcapplunching.cocktail_dakk.ui.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.FragmentSearchBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragmentByDataBinding
import com.umcapplunching.cocktail_dakk.ui.menu_detail.MenuDetailActivity
import com.umcapplunching.cocktail_dakk.ui.search.searchService.*
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchView
import com.umcapplunching.cocktail_dakk.ui.search_tab.SearchTabActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchFragment : BaseFragmentByDataBinding<FragmentSearchBinding>(R.layout.fragment_search), SearchView{

    private lateinit var callback: OnBackPressedCallback
    private val TAG = "SearchFragment"
    private val searchService by lazy {
        SearchService()
    }
    
    // 총 갯수 ViewModel로 옮길까
    private var totalcnt = 10

    // 스크롤 변수
    private var scrollFlag: Boolean = true //스크롤 가능한지 (끝에 도달했는지)
    private var ispagingnow : Boolean = false // 지금 페이징 중인지

    // 어뎁터용 필터 임의 변수
    private val drink_foradapter = ArrayList<String>()
    private val keyword_foradapter = ArrayList<String>()

    private lateinit var searchListAdapter: SearchlistRvAdapter
    private lateinit var searchCocktailViewModel : SearchCocktailViewModel

    override fun initViewModel() {
        binding.lifecycleOwner = this
        // 뷰 모델 정의
        searchCocktailViewModel = ViewModelProvider(requireActivity()).get(SearchCocktailViewModel::class.java)
        binding.searchViewModel = searchCocktailViewModel
        searchService.setsearchView(this)

        // 어뎁터 기본설정
        searchListAdapter = SearchlistRvAdapter(searchCocktailViewModel.visibleitemList.value!!)
    }

    override fun initView() {
        CoroutineScope(Dispatchers.IO).launch {
            // 아무것도 검색 안했을 때 (초기화 상태)일 때 가져오기
            searchService.search(" ")
        }

        searchCocktailViewModel.visibleitemList.observe(this, {
            // visibleList의 변화가 생기면 Adapter 업데이트
            // ex) 검색, 필터 링
            searchListAdapter.updateList(it)
            searchListAdapter.setClickListiner(object : SearchlistRvAdapter.MyItemClickListener{
                override fun onItemClick(cocktail: CocktailList) {
                    Log.d(TAG,cocktail.toString())
                    val intent = Intent(requireContext(),MenuDetailActivity::class.java)
                    intent.putExtra("cocktailId",cocktail.cocktailInfoId)
                    startActivity(intent)
                }

                override fun onKeywordClick(keyword: String) {
                    searchCocktailViewModel.setSearchStr(keyword)
                }

                override fun onItemIsLike(islike: Boolean, cocktail: CocktailList) {
                }
            })
        })

        // 검색어 바뀌는 것이 Observing 됬을 때
        searchCocktailViewModel.searchStr.observe(this, {
            // 스크롤 가능한 상태로 변경
            scrollFlag = true //스크롤 가능한지 (끝에 도달했는지)
            ispagingnow  = false // 지금 페이징 중인지
            searchCocktailViewModel.updatecurrentPage(1)
            // 검색모드
            searchCocktailViewModel.updateSearchMode(SearchCocktailViewModel.SearchMode.SEARCH)
            // 검색 단어가 변경되면 서버 API에서 받아옴
            // visibleitemList 바뀜
            CoroutineScope(Dispatchers.IO).launch {
                searchService.search(it)
            }
            if(it.trim()==""){
                binding.searchSearchbarTv.text = "검색어를 입력해주세요."
                binding.searchSearchbarExiticonIv.visibility = View.GONE
            }else{
                binding.searchSearchbarTv.text = it
                binding.searchSearchbarExiticonIv.visibility = View.VISIBLE
            }
        })

        searchCocktailViewModel.searchMode.observe(this,{
            scrollFlag = true
            if(it == SearchCocktailViewModel.SearchMode.FILTER){
                // 필터 모드일때 Visible관리
                val drink_dum =
                    searchCocktailViewModel.filterkeyword.value!!.first.toArray(arrayOfNulls<String>(searchCocktailViewModel.filterkeyword.value!!.first.size)).toList() as List<String>
                drink_foradapter.clear()
                drink_foradapter.addAll(searchCocktailViewModel.filterkeyword.value!!.first)
                val keyword_dum =
                    searchCocktailViewModel.filterkeyword.value!!.second.toArray(arrayOfNulls<String>(searchCocktailViewModel.filterkeyword.value!!.second.size))
                        .toList() as List<String>
                keyword_foradapter.clear()
                keyword_foradapter.addAll(searchCocktailViewModel.filterkeyword.value!!.second)

                CoroutineScope(Dispatchers.IO).launch {
                    searchService.filter(0, keyword_dum, searchCocktailViewModel.filterkeyword.value!!.third.first, searchCocktailViewModel.filterkeyword.value!!.third.second, drink_dum)
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
                        gijuresultAdapter.removeItem(position)
                        val keyword_dum =
                            keyword_foradapter.toArray(arrayOfNulls<String>(keyword_foradapter.size))
                                .toList() as List<String>
                        val drink_dum =
                            drink_foradapter.toArray(arrayOfNulls<String>(drink_foradapter.size))
                                .toList() as List<String>
                        CoroutineScope(Dispatchers.IO).launch {
                            searchService.filter(0,
                                keyword_dum,
                                searchCocktailViewModel.filterkeyword.value!!.third.first,
                                searchCocktailViewModel.filterkeyword.value!!.third.second,
                                drink_dum)
                        }
                        scrollFlag = true
                        searchCocktailViewModel.updatecurrentPage(0)
                    }
                })

                val keywordresultAdapter = FilterresulterAdapter(keyword_foradapter)
                binding.seracgFilterKeywordResultRv.adapter = keywordresultAdapter
                keywordresultAdapter.setMyItemClickListener(object :
                    FilterresulterAdapter.MyItemClickListener {
                    override fun onItemClick(cocktail: String) {

                    }
                    override fun removestr(resultstr: String, position: Int) {
                        keywordresultAdapter.removeItem(position)
                        val keyword_dum =
                            keyword_foradapter.toArray(arrayOfNulls<String>(keyword_foradapter.size))
                                .toList() as List<String>
                        val drink_dum =
                            drink_foradapter.toArray(arrayOfNulls<String>(drink_foradapter.size))
                                .toList() as List<String>
                        CoroutineScope(Dispatchers.IO).launch {
                            searchService.filter(0, keyword_dum, searchCocktailViewModel.filterkeyword.value!!.third.first, searchCocktailViewModel.filterkeyword.value!!.third.second, drink_dum)
                        }
                        scrollFlag = true
                        searchCocktailViewModel.updatecurrentPage(0)

                    }
                })
                binding.searchFilterDosuResultTv.text = "${searchCocktailViewModel.filterkeyword.value!!.third.first}도 ~ ${searchCocktailViewModel.filterkeyword.value!!.third.second}도"
                //페이징되게
                searchCocktailViewModel.updatecurrentPage(0)
            }else{
                // false일때 검색 Visible 관리
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
            }
        })

        searchCocktailViewModel.filterkeyword.observe(this,{
            Log.d(TAG,it.toString())
        })

        binding.searchMainRv.adapter = searchListAdapter
    }

    override fun initListener() {
        setOnClickListener()
        FilterClcikListener()
        SetMainRvScrollListener()
    }

    override fun onSearchLoading() {
        requireActivity().runOnUiThread {
            binding.searchProgressbar.visibility = View.VISIBLE
        }
    }

    override fun onSearchSuccess(searchresult: SearchResult) {
        // 뷰모델 옵저빙하고 있어서 뷰모델 칵테일만 바꿔주기
        searchCocktailViewModel.setCocktail(searchresult.cocktailList)
        totalcnt = searchresult.size

        // 코루틴에서 돌아가고 있어서 UI쓰레드에서 따로처리
        requireActivity().runOnUiThread {
            binding.searchLoadingBar.visibility = View.GONE
            binding.searchProgressbar.visibility = View.GONE
            binding.searchResultTv.text = "${totalcnt}개의 검색결과"
            // 스크롤 맨위로
            binding.searchMainRv.scrollToPosition(0)
        }
    }

    override fun onSearchFailure(code: Int, message: String) {
        // 못가져 왔을 때
    }

    private fun SetMainRvScrollListener() {
        val onScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE &&
                    scrollFlag &&
                    searchCocktailViewModel.searchMode.value == SearchCocktailViewModel.SearchMode.SEARCH &&
                    !ispagingnow
                ) {
                    ispagingnow = true
                    binding.searchProgressbar.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed({
                        requsetnextpage()
                    }, 300)
                }
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && scrollFlag
                    && searchCocktailViewModel.searchMode.value == SearchCocktailViewModel.SearchMode.FILTER  && !ispagingnow
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
            // 애니메이션
            val animTransRight: Animation = AnimationUtils
                .loadAnimation(activity, R.anim.horizon_out)
            animTransRight.duration = 700
            binding.searchSearchbarLv.startAnimation(animTransRight)

            val intent = Intent(activity, SearchTabActivity::class.java)
            // 뷰모델 searchStr 인텐트로 넘겨줘서 searchTabActivity에서 나오게
            intent.putExtra("searchStr",searchCocktailViewModel.searchStr.value)
            startActivity(intent)
        }

        // 필터 아이콘 클릭
        val bottomSheet = DialogBottomFilter()
        binding.searchFilterIv.setOnClickListener {
            searchCocktailViewModel.deleteAllKeyword()
            bottomSheet.show(childFragmentManager,bottomSheet.tag)
        }

        // 검색어 지우기 버튼
        binding.searchSearchbarExiticonIv.setOnClickListener {
            searchCocktailViewModel.setSearchStr(" ")
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

    private fun FilterClcikListener() {
        // 필터 뒤로가기 아이콘
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
            // 현재 페이지
            searchCocktailViewModel.updatecurrentPage(0)
            searchCocktailViewModel.updateSearchMode(SearchCocktailViewModel.SearchMode.SEARCH)
            // 스크롤 가능하게
            scrollFlag =true
            searchCocktailViewModel.setSearchStr(" ")
        }
    }

    // 페이징 로딩 성공 실패
    override fun onPagingLoading() {
        requireActivity().runOnUiThread {
            binding.searchProgressbar.visibility = View.VISIBLE
        }
    }

    override fun onPagingSuccess(searchresult: SearchResult) {
        val activity: Activity? = activity
        ispagingnow = false
        if (this.isAdded && activity != null)
        {
            Log.d(TAG,searchresult.toString())
            // 뷰모델 더하기로 업데이트
            searchCocktailViewModel.addCocktailList(searchresult)
            if (searchresult.cocktailList.isEmpty()) {
                scrollFlag = false
            }
            requireActivity().runOnUiThread {
                totalcnt += searchresult.cocktailList.size
                binding.searchProgressbar.visibility = View.GONE
                binding.searchResultTv.text = "${totalcnt}개의 검색결과"
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
        searchCocktailViewModel.updatecurrentPage(searchCocktailViewModel.currentPage.value!! + 1)
        CoroutineScope(Dispatchers.IO).launch{
            searchService.paging(searchCocktailViewModel.currentPage.value!!, searchCocktailViewModel.searchStr.value.toString())
        }
    }

    // 필터 로딩 성공 실패
    override fun onFilterLoading() {
        requireActivity().runOnUiThread {
            binding.searchLoadingBar.visibility = View.VISIBLE
        }
    }

    override fun onFilterSuccess(searchresult: SearchResult) {
        val activity: Activity? = activity
        if ( isAdded() && activity != null) {
            requireActivity().runOnUiThread {
                binding.searchSearchbarExiticonIv.visibility = View.GONE
                binding.searchLoadingBar.visibility = View.GONE
                binding.searchResultTv.text = "${totalcnt}개의 검색결과"
                binding.searchMainRv.scrollToPosition(0)
            }
            totalcnt = searchresult.cocktailList.size
            searchCocktailViewModel.setCocktail(searchresult.cocktailList)
            // 검색어 초기화
            searchCocktailViewModel.setSearchStr(" ")
            // 필터모드 진입
            searchCocktailViewModel.updateSearchMode(SearchCocktailViewModel.SearchMode.FILTER)
            // 비어있으면 스크롤 비활성화
            if (searchresult.cocktailList.isEmpty()) {
                scrollFlag = false
            }
        }
    }

    override fun onFilterFailure(code: Int, message: String) {
        val activity: Activity? = activity
        if ( isAdded() && activity != null) {
            requireActivity().runOnUiThread {
                binding.searchLoadingBar.visibility = View.GONE
            }
        }
    }

    fun requsetnextpagefor_filter() {
        searchCocktailViewModel.updatecurrentPage(searchCocktailViewModel.currentPage.value!! + 1)
        val keyword_dum = keyword_foradapter.toArray(arrayOfNulls<String>(keyword_foradapter.size))
            .toList() as List<String>
        val drink_dum = drink_foradapter.toArray(arrayOfNulls<String>(drink_foradapter.size))
            .toList() as List<String>
        CoroutineScope(Dispatchers.IO).launch {
            searchService.filterpaging(searchCocktailViewModel.currentPage.value!!, keyword_dum, searchCocktailViewModel.filterkeyword.value!!.third.first, searchCocktailViewModel.filterkeyword.value!!.third.second, drink_dum)
        }
    }


}