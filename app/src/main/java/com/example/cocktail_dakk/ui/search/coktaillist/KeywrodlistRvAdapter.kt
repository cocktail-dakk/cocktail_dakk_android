package com.example.cocktail_dakk.ui.search.coktaillist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktail_dakk.databinding.ItemKeywordBinding
import com.example.cocktail_dakk.ui.search.searchService.Keyword


class KeywrodlistRvAdapter(private var keywordList : List<Keyword>) : RecyclerView.Adapter<KeywrodlistRvAdapter.Viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var binding: ItemKeywordBinding = ItemKeywordBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(keywordList[position])
    }

    override fun getItemCount(): Int =keywordList.size

    inner class Viewholder(var binding : ItemKeywordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(keywordList : Keyword){
            binding.itemCocktailKeywordTv.text = keywordList.keywordName
        }
    }

//    class SearchlistRvAdapter(private var cocktaillist : ArrayList<Cocktail>) : RecyclerView.Adapter<com.example.cocktail_dakk.ui.search.SearchlistRvAdapter.Viewholder>() {
}