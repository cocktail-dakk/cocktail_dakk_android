package com.example.cocktail_dakk.ui.search_tab

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktail_dakk.data.entities.cocktaildata_db.CocktailDatabase
import com.example.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_Mainrec
import com.example.cocktail_dakk.databinding.FragmentSearchTabBaseBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.menu_detail.MenuDetailActivity
import com.example.cocktail_dakk.ui.search_tab.adapter.MainrecNameRvAdapter
import com.example.cocktail_dakk.ui.search_tab.adapter.RecentSearchKeywordRvAdapter

class SearchTabBaseFragment : BaseFragment<FragmentSearchTabBaseBinding>(FragmentSearchTabBaseBinding::inflate) {

    private lateinit var CocktailDB : CocktailDatabase

    override fun initAfterBinding() {

        //최근검색어 DB
        CocktailDB = CocktailDatabase.getInstance(requireContext())!!
        var cocktaillist2 = CocktailDB.RecentSearchDao().getcocktail()
        cocktaillist2 = cocktaillist2.reversed()
        var strlist : ArrayList<String> = ArrayList()
        for(i in 0..cocktaillist2.size-1){
            if (cocktaillist2[i].searchstr == ""){
                continue
            }
            strlist.add(cocktaillist2[i].searchstr)
        }
        val recentSearchKeywordAdapter = RecentSearchKeywordRvAdapter(strlist)
        binding.searchTabBaseKeywordRv.adapter = recentSearchKeywordAdapter

        recentSearchKeywordAdapter.setMyItemClickListener(object : RecentSearchKeywordRvAdapter.MyItemClickListener{
            override fun onItemClick(cocktail: String) {
                var spf = context?.getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
                var editor: SharedPreferences.Editor = spf?.edit()!!
                editor.putString("searchstr", cocktail)
                editor.apply()
                (activity as SearchTabActivity).TomoveSearchTab()
            }
            override fun removestr(recentstr: String,position: Int) {
                CocktailDB.RecentSearchDao().duplicatecheck(recentstr.trim())
                recentSearchKeywordAdapter.removeItem(position)
            }
        })
        //전체삭제
        binding.searchTabBaseDeletallBt.setOnClickListener {
            CocktailDB.RecentSearchDao().deleteAllCocktail()
            cocktaillist2 = cocktaillist2.reversed()
            cocktaillist2 = CocktailDB.RecentSearchDao().getcocktail()
            strlist.clear()
            for(i in 0..cocktaillist2.size-1){
                strlist.add(cocktaillist2[i].searchstr)
            }
            val recentSearchKeywordAdapter = RecentSearchKeywordRvAdapter(strlist)
            binding.searchTabBaseKeywordRv.adapter = recentSearchKeywordAdapter
        }


        //나에게 맞는 칵테일 추천
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