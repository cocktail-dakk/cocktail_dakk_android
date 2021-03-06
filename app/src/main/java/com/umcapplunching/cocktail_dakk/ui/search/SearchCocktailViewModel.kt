package com.umcapplunching.cocktail_dakk.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.umcapplunching.cocktail_dakk.data.entities.ResponseWrapper
import com.umcapplunching.cocktail_dakk.ui.search.searchService.CocktailList
import com.umcapplunching.cocktail_dakk.ui.search.searchService.SearchResult
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SearchCocktailViewModel(private val repository: SearchRepository) : ViewModel() {

    private val TAG = "SearchCocktailViewModel"
    private val _cocktailList = MutableLiveData<List<CocktailList>>()
    private var cocktailList = listOf<CocktailList>()

    enum class SearchMode{
        SEARCH, FILTER
    }

    fun search() {
        viewModelScope.launch {
            try{
                val response = repository.search(searchStr.value!!)
                _cocktailList.postValue(response.responseBody.cocktailList)
            }catch (e:Exception){
                Log.d(TAG,e.toString())
            }
        }
    }

    fun paging() {
        try{
            repository.paging(currentPage.value!!,searchStr.value!!).enqueue(object : Callback<ResponseWrapper<SearchResult>>{
                override fun onResponse(
                    call: Call<ResponseWrapper<SearchResult>>,
                    response: Response<ResponseWrapper<SearchResult>>
                ) {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> addCocktailList(resp.responseBody)
                        else -> {
                            throw Throwable("Filter 오류")
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseWrapper<SearchResult>>, t: Throwable) {
                    Log.d(TAG,t.message.toString())
                }
            })
        }catch (e :Exception){
            Log.d(TAG,e.toString())
        }
    }


    // 검색 단어
    private val _searchStr = MutableLiveData<String>()
    val searchStr : LiveData<String>
        get() = _searchStr

    // 기주, 취향 리스트 Pair로 저장
    private val _filterkeyword = MutableLiveData<Triple<ArrayList<String>,ArrayList<String>,Pair<Int,Int>>>()
    val filterkeyword : LiveData<Triple<ArrayList<String>,ArrayList<String>,Pair<Int,Int>>>
        get() = _filterkeyword

    // LiveData와 List를 따로 관리하기 화면에 보이는 리스트
    val visibleitemList: LiveData<List<CocktailList>>
        get() = _cocktailList
    
    // 현재 페이지
    private val _currentPage = MutableLiveData<Int>()
    val currentPage : LiveData<Int>
        get() = _currentPage

    // 검색 모드
    private val _searchMode = MutableLiveData<SearchMode>()
    val searchMode : LiveData<SearchMode>
        get() = _searchMode

    fun updateKeyword(keyword : Triple<ArrayList<String>,ArrayList<String>,Pair<Int,Int>>){
        _filterkeyword.value = keyword
    }

    fun deleteAllKeyword(){
        _filterkeyword.value = Triple(ArrayList<String>(),ArrayList<String>(),Pair<Int,Int>(10,30))
    }

    fun updateSearchMode(mode : SearchMode){
        _searchMode.value = mode
    }

    fun updatecurrentPage(pagenum : Int){
        _currentPage.value = pagenum
    }

    init{
        // 초반에 null 방지를 위해 그냥 비어있는 리스트로 초기화
        _cocktailList.value = cocktailList
        _searchStr.value = " "
        _currentPage.value = 1
    }

    fun setSearchStr(inputstr : String){
        _searchStr.value = inputstr
    }

    // 검색 : 칵테일 리스트 갈아치우기
    fun setCocktail(itemList : List<CocktailList>) {
        cocktailList = itemList
        // background 스레드에서는 MutableLiveData 값 할당 불가능
        _cocktailList.postValue(cocktailList)
    }

    // 칵테일 리스트에 하나 추가
    fun addCocktail(cocktail: CocktailList) {
//        cocktailList.add(cocktail)
        _cocktailList.value = cocktailList
    }

    // 페이징 : 칵테일 리스트에 여러개 추가
    fun addCocktailList(searchresult: SearchResult) {
        for(i in searchresult.cocktailList){
            cocktailList = cocktailList + i
        }
        _cocktailList.value = cocktailList
    }

    // 하나씩 제거할 일은 없음 안쓸 듯
    fun removeCocktail(cocktail: CocktailList) {
//        cocktailList.remove(cocktail)
        _cocktailList.value = cocktailList
    }


}