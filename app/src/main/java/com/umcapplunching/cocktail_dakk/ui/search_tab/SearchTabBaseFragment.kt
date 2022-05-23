package com.umcapplunching.cocktail_dakk.ui.search_tab

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_Mainrec
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_recentSearch
import com.umcapplunching.cocktail_dakk.databinding.FragmentSearchTabBaseBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragmentByDataBinding
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.search_tab.adapter.MainrecNameRvAdapter
import com.umcapplunching.cocktail_dakk.ui.search_tab.adapter.RecentSearchKeywordRvAdapter

class SearchTabBaseFragment : BaseFragmentByDataBinding<FragmentSearchTabBaseBinding>(R.layout.fragment_search_tab_base) {

    private lateinit var searchTabViewModel: SearchTabViewModel
    private lateinit var strlist : MutableList<Cocktail_recentSearch>
    private lateinit var recentSearchKeywordAdapter : RecentSearchKeywordRvAdapter

    override fun initViewModel() {
        binding.lifecycleOwner=this
        searchTabViewModel = ViewModelProvider(requireActivity()).get(SearchTabViewModel::class.java)
    }

    override fun initView() {
        // 최근 검색 결과를 observing 하여 뷰에 반영
        searchTabViewModel.getrecentSearchAll().observe(this,{
            strlist = it.toMutableList()
            if(strlist.size < 1){
                binding.searchTabBaseDeletallBt.visibility = View.INVISIBLE
            }else{
                binding.searchTabBaseDeletallBt.visibility = View.VISIBLE
            }
            recentSearchKeywordAdapter = RecentSearchKeywordRvAdapter(strlist.asReversed().toMutableList())
            binding.searchTabBaseKeywordRv.adapter = recentSearchKeywordAdapter
            recentSearchKeywordAdapter.setMyItemClickListener(object : RecentSearchKeywordRvAdapter.MyItemClickListener{
                override fun onItemClick(cocktail: Cocktail_recentSearch) {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.putExtra("searchStr",cocktail.searchstr)
                    startActivity(intent)
                }
                override fun removestr(recentstr: Cocktail_recentSearch,position: Int) {
                    searchTabViewModel.recentSearchdelete(recentstr)
                    recentSearchKeywordAdapter.removeItem(position)
                }
            })
        })

        // 최근 기록 전체 삭제
        binding.searchTabBaseDeletallBt.setOnClickListener {
            searchTabViewModel.deletAllrecentCocktail()
        }

        // mainRecommand 결과를 observing 하여 뷰에 반영
        searchTabViewModel.getmainRecAll().observe(this,{
            val mainrecNameRvAdapter = MainrecNameRvAdapter(it)
            binding.searchTabBaseMainrecRv.adapter = mainrecNameRvAdapter
            mainrecNameRvAdapter.setMyItemClickListener(object : MainrecNameRvAdapter.MyItemClickListener{
                override fun onItemClick(cocktail: Cocktail_Mainrec) {
                    // 디테일 화면 넘기기
//                val intent = Intent(activity, MenuDetailActivity::class.java)
//                intent.putExtra("id",cocktail.cocktailinfoid)
//                startActivity(intent)
//                    (activity as SearchTabActivity).detailcocktailInSearchtab(cocktail.cocktailinfoid)
                }
            })
        })
    }

}