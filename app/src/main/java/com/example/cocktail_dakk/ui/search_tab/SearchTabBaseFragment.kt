package com.example.cocktail_dakk.ui.search_tab

import android.content.Intent
import android.util.Log
import com.example.cocktail_dakk.data.entities.datafordb.CocktailDatabase
import com.example.cocktail_dakk.data.entities.datafordb.Cocktail_Mainrec
import com.example.cocktail_dakk.databinding.FragmentSearchTabBaseBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.menu_detail.MenuDetailActivity
import com.example.cocktail_dakk.ui.search_tab.adapter.MainrecNameRvAdapter
import com.example.cocktail_dakk.ui.search_tab.adapter.RecentSearchKeywordRvAdapter

class SearchTabBaseFragment : BaseFragment<FragmentSearchTabBaseBinding>(FragmentSearchTabBaseBinding::inflate) {

    private lateinit var CocktailDB : CocktailDatabase

    override fun initAfterBinding() {

        var searchlist : ArrayList<String> = ArrayList()
        searchlist.add("블루 하와이")
        searchlist.add("모히또")
        searchlist.add("핑크 레이디")
        searchlist.add("피나 콜라다")
        searchlist.add("롱티")
        searchlist.add("퍼스트 키스")
        searchlist.add("블랙 러시안")

        val recentSearchKeywordAdapter = RecentSearchKeywordRvAdapter(searchlist)
        binding.searchTabBaseKeywordRv.adapter = recentSearchKeywordAdapter

        CocktailDB = CocktailDatabase.getInstance(requireContext())!!
        val cocktaillist = CocktailDB.MainrecDao().getcocktail()
        val mainrecNameRvAdapter = MainrecNameRvAdapter(cocktaillist)
        binding.searchTabBaseMainrecRv.adapter = mainrecNameRvAdapter

        mainrecNameRvAdapter.setMyItemClickListener(object : MainrecNameRvAdapter.MyItemClickListener{
            override fun onItemClick(cocktail: Cocktail_Mainrec) {
                val intent = Intent(activity, MenuDetailActivity::class.java)
                intent.putExtra("id",cocktail.cocktailinfoid)
                startActivity(intent)
            }
        })
    }
}