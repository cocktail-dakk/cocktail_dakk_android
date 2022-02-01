package com.example.cocktail_dakk.ui.search

import android.content.Intent
import android.content.SharedPreferences
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail_SearchList
import com.example.cocktail_dakk.databinding.FragmentSearchBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.MainActivity
import com.example.cocktail_dakk.ui.menu_detail.MenuDetailActivity
import com.example.cocktail_dakk.ui.search.searchService.SearchResult
import com.example.cocktail_dakk.ui.search.searchService.SearchService
import com.example.cocktail_dakk.ui.search.searchService.SearchView
import com.example.cocktail_dakk.ui.search_tab.SearchTabActivity
import com.google.gson.Gson
import com.example.cocktail_dakk.ui.search.searchService.PagingView
import java.util.logging.Handler


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchView, PagingView {

    val gson: Gson = Gson()
    var currentpage = 1
    var totalcnt = 10
    var cocktaillist: ArrayList<Cocktail_SearchList> = ArrayList()
    var searchService = SearchService()

    lateinit var searchListAdapter: SearchlistRvAdapter
    override fun initAfterBinding() {
        binding.searchSearchbarLv.visibility = View.VISIBLE

        setCurrentPage()
        setOnClickListener()
//        var spf = context?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
//        var editor : SharedPreferences.Editor = spf?.edit()!!
//        editor.putString("searchstr"," ")
//        editor.apply()
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
        //서버에서 받아오기
        // 현재 페이지
        currentpage = 1
        //리스트 갯수
        totalcnt = 0
        searchService.setsearchView(this)
        searchService.setpagingView(this)
        Log.d("seachstr : ", spf!!.getString("searchstr", " ").toString()!!)
        searchService.search(spf!!.getString("searchstr", " ").toString())

        //검색어 설정
        var text = spf!!.getString("searchstr", " ")
        if (text == " ") {
            binding.searchSearchbarExiticonIv.visibility = View.GONE
            binding.searchSearchbarTv.setText("검색어를 입력해주세요.")
        } else {
            binding.searchSearchbarExiticonIv.visibility = View.VISIBLE
            binding.searchSearchbarTv.setText(text)
        }

        val onScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.searchProgressbar.visibility = View.VISIBLE
                    android.os.Handler(Looper.getMainLooper()).postDelayed({
                        requsetnextpage()
                        //네트워킹 처리, 데이터 처리를 요기서 할수 있을듯
                    }, 500)
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
            (activity as MainActivity).ShowFilter(true)
        }

        //exiticon
        binding.searchSearchbarExiticonIv.setOnClickListener {
            var spf = context?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = spf?.edit()!!
            editor.putString("searchstr", " ")
            editor.apply()
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
                val intent = Intent(activity, MenuDetailActivity::class.java)
                var json = gson.toJson(cocktail.keywords)
                intent.apply {
                    this.putExtra("localName", cocktail.localName) // 데이터 넣기
                    this.putExtra("englishName", cocktail.englishName)
                    this.putExtra("imageURL", cocktail.imageURL)
                    this.putExtra("starPoint", cocktail.starPoint)
                    this.putExtra("alcoholLevel", cocktail.alcoholLevel)
                    this.putExtra("mixxing", "섞는 방법")
                    this.putExtra("keywords", json)
                    this.putExtra("information", "칵테일 정보 어쩌구 저쩌구 설명 설명")
                    this.putExtra(
                        "ingredients",
                        "달걀 흰자 1개, 그레나딘 시럽 (10ml), 크림 (15ml), 드라이 진 (45ml), 크림  (15ml), 드라이 진 (45ml), 크림 (15ml), 드라이 진  (45ml)"
                    )
                }
                startActivity(intent)
//                arguments = Bundle().apply {
//                    var gson = Gson()
//                    var albumJson = gson.toJson(album)
//                    putString("album", albumJson)
//                } //q번들
            }
        })
    }


    //서치 뷰
    override fun onSearchLoading() {
    }

    override fun onSearchSuccess(searchresult: SearchResult) {
        cocktaillist = ArrayList()
        for (i in searchresult.cocktailList) {
            cocktaillist.add(
                Cocktail_SearchList(
                    i.koreanName,
                    i.englishName,
                    i.keywords,
                    "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_brandysour.webp",
                    i.ratingAvg,
                    i.alcoholLevel,
                    "기주"
                )
            )
        }
        setCocktailList(cocktaillist)
        totalcnt = cocktaillist.size
        binding.searchResultTv.text = totalcnt.toString() + "개의 검색결과"
    }

    override fun onSearchFailure(code: Int, message: String) {
    }


    //페이징 뷰
    override fun onPagingLoading() {

    }

    override fun onPagingSuccess(searchresult: SearchResult) {
        binding.searchProgressbar.visibility = View.GONE
        for (i in searchresult.cocktailList) {
            searchListAdapter.addItem(
                Cocktail_SearchList(
                    i.koreanName,
                    i.englishName,
                    i.keywords,
                    "https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_brandysour.webp",
                    i.ratingAvg,
                    i.alcoholLevel,
                    "기주"
                )
            )
        }
        totalcnt += searchresult.cocktailList.size
        binding.searchResultTv.text = (totalcnt).toString() + "개의 검색결과"
    }

    override fun onPagingFailure(code: Int, message: String) {
        binding.searchProgressbar.visibility = View.GONE
    }

    fun requsetnextpage() {
        var spf = activity?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
        currentpage += 1
        searchService.paging(currentpage, spf!!.getString("searchstr", " ").toString())
    }


}