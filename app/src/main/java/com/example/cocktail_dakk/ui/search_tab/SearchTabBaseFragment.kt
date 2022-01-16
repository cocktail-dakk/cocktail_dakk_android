package com.example.cocktail_dakk.ui.search_tab

import com.example.cocktail_dakk.databinding.FragmentSearchTabBaseBinding
import com.example.cocktail_dakk.ui.BaseFragment

class SearchTabBaseFragment : BaseFragment<FragmentSearchTabBaseBinding>(FragmentSearchTabBaseBinding::inflate) {
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

    }
}