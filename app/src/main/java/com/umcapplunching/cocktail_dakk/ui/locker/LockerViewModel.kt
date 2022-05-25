package com.umcapplunching.cocktail_dakk.ui.locker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService.BookmarkBody
import com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService.BookmarkService
import com.umcapplunching.cocktail_dakk.ui.search.searchService.CocktailList
import com.umcapplunching.cocktail_dakk.ui.search.searchService.Keyword

class LockerViewModel : ViewModel() {

    private val _bookmarkList = MutableLiveData<List<BookmarkBody>>()
    private var bookmarkList = listOf<BookmarkBody>()
//    private val bookmarkService = BookmarkService()
    val visibleItemList : LiveData<List<BookmarkBody>>
        get() = _bookmarkList

    private val _currentPosition = MutableLiveData<Int>()
    val currentPosition : LiveData<Int>
        get() = _currentPosition

    init{
        _bookmarkList.value = listOf(BookmarkBody(-1, listOf(Keyword(0, " "))," "," "," "," "))
        _currentPosition.value = 0
    }

    fun setCurrentPosition(position : Int){
        _currentPosition.postValue(position)

    }

    // 전체 즐겨찾기 등록
    fun setBookmarkList(itemList : List<BookmarkBody>){
        bookmarkList = itemList
        _bookmarkList.postValue(bookmarkList)
    }

    // 즐겨찾기 하나 등록
    fun addBookmark(item : BookmarkBody){
        bookmarkList = bookmarkList + item
        _bookmarkList.value = bookmarkList
    }

}