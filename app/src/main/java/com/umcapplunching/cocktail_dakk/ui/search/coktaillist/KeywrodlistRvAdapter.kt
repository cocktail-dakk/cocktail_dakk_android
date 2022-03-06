package com.umcapplunching.cocktail_dakk.ui.search.coktaillist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umcapplunching.cocktail_dakk.databinding.ItemKeywordBinding
import com.umcapplunching.cocktail_dakk.ui.search.searchService.Keyword


class KeywrodlistRvAdapter(private var keywordList : List<Keyword>) : RecyclerView.Adapter<KeywrodlistRvAdapter.Viewholder>() {

    private lateinit var mItemClickListener: MyItemClickListener

    interface MyItemClickListener{
        fun onItemClick(keyword: Keyword)
    }

    fun setClickListiner(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var binding: ItemKeywordBinding = ItemKeywordBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(keywordList[position])
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(keywordList[position])    //외부에서 처리할 수 있도록
        }

    }

    override fun getItemCount(): Int =keywordList.size

    inner class Viewholder(var binding : ItemKeywordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(keywordList : Keyword){
            binding.itemCocktailKeywordTv.text = keywordList.keywordName
        }
    }

//    class SearchlistRvAdapter(private var cocktaillist : ArrayList<Cocktail>) : RecyclerView.Adapter<com.umcapplunching.cocktail_dakk.ui.search.SearchlistRvAdapter.Viewholder>() {
}