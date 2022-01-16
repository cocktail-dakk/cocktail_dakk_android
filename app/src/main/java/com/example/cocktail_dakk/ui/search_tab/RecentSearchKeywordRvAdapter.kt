package com.example.cocktail_dakk.ui.search_tab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktail_dakk.databinding.ItemRecentsearchBinding


class RecentSearchKeywordRvAdapter(private var keywordlist : ArrayList<String> ) : RecyclerView.Adapter<RecentSearchKeywordRvAdapter.Viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding : ItemRecentsearchBinding = ItemRecentsearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(keywordlist[position])
    }

    override fun getItemCount(): Int = keywordlist.size

    inner class Viewholder(var binding : ItemRecentsearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword : String){
            binding.itemRecentsearchTv.text = keyword
        }
    }



//    class CockRecommandRvAdapter(private var cocktaillist : ArrayList<Cocktail>) : RecyclerView.Adapter<com.example.cocktail_dakk.ui.main.keyword.CockRecommandRvAdapter.Viewholder>() {

}