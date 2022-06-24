package com.umcapplunching.cocktail_dakk.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// ViewModelProviders의 경우 인수가 없는 생성자일 때만 사용 가능
// 파라미터 있는 ViewModel 경우 인스턴스 전달을 위해 ViewModelProvider에 인수전달을 위한 Factory 사용해야함.
class SearchViewModelFactory constructor(private val repository: SearchRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchCocktailViewModel::class.java)) {
            SearchCocktailViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}