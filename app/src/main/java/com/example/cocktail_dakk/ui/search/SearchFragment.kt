package com.example.cocktail_dakk.ui.search

import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail
import com.example.cocktail_dakk.databinding.FragmentSearchBinding
import com.example.cocktail_dakk.ui.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    override fun initAfterBinding() {

        val cocktaillist: ArrayList<Cocktail> = ArrayList()
        cocktaillist.add(Cocktail("옴뇸뇸 칵테일", "CockTail_1", R.drawable.search_ex1))
        cocktaillist.add(Cocktail("아그작 칵테일", "CockTail_2", R.drawable.search_ex2))
        cocktaillist.add(Cocktail("당신의 사랑의 첫 키스", "CockTail_3", R.drawable.search_ex3))
        cocktaillist.add(Cocktail("안녕히계세요 여러분 저는 속세의 굴레를 벗어나", "CockTail_4", R.drawable.search_ex1))
        cocktaillist.add(Cocktail("칵테일1", "CockTail_5", R.drawable.search_ex2))
        cocktaillist.add(Cocktail("칵테일2", "CockTail_6", R.drawable.search_ex3))
        cocktaillist.add(Cocktail("칵테일3", "CockTail_6", R.drawable.search_ex3))
        cocktaillist.add(Cocktail("칵테일4", "CockTail_6", R.drawable.search_ex3))
        cocktaillist.add(Cocktail("칵테일5", "CockTail_6", R.drawable.search_ex3))

        val searchListAdapter = SearchlistRvAdapter(cocktaillist)
        binding.searchMainRv.adapter = searchListAdapter


    }
}