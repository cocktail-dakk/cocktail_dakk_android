package com.umcapplunching.cocktail_dakk.ui.search

import android.app.Application
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umcapplunching.cocktail_dakk.data.entities.Cocktail_SearchList
import com.umcapplunching.cocktail_dakk.ui.search.searchService.CocktailList
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchResult
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchService
import com.umcapplunching.cocktail_dakk.utils.getaccesstoken


class SearchCocktailViewModel : ViewModel() {

    private val _cotailList = MutableLiveData<List<CocktailList>>()

    private var cocktailList = mutableListOf<CocktailList>()

    private val TAG = "SearchCocktailViewModel"
    // LiveData와 List를 따로 관리하기
    // 화면에 보이는 리스트
    val visibleitemList: LiveData<List<CocktailList>>
        get() = _cotailList

    init{
        // 초반에 null 방지를 위해 그냥 비어있는 리스트로 초기화
        _cotailList.value = cocktailList
    }

    // 검색 : 칵테일 리스트 갈아치우기
    fun setCocktail(itemList : List<CocktailList>) {
        Log.d(TAG,itemList.toString())
        cocktailList = itemList as MutableList<CocktailList>

        // background 스레드에서는 MutableLiveData 값 할당 불가능
        _cotailList.postValue(cocktailList)
    }

    // 칵테일 리스트에 하나씩 추가
    fun addCocktail(cocktail: CocktailList) {
//        cocktailList.add(cocktail)
        _cotailList.value = cocktailList
    }

    // 페이징 : 칵테일 리스트에 여러개 추가
    fun addCocktailList(searchresult: SearchResult) {
        for(i in searchresult.cocktailList){
            cocktailList.add(i)
        }
        _cotailList.value = cocktailList
    }

    // 하나씩 제거할 일은 없음 안쓸 듯
    fun removeCocktail(cocktail: CocktailList) {
        cocktailList.remove(cocktail)
        _cotailList.value = cocktailList
    }


}