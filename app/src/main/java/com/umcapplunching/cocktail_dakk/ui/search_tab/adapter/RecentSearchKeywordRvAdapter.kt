package com.umcapplunching.cocktail_dakk.ui.search_tab.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umcapplunching.cocktail_dakk.databinding.ItemRecentsearchBinding


class RecentSearchKeywordRvAdapter(private var searchlist : ArrayList<String> ) : RecyclerView.Adapter<RecentSearchKeywordRvAdapter.Viewholder>() {

    interface MyItemClickListener{
        fun onItemClick(cocktail: String)
        fun removestr(recentstr: String,position: Int)

    }
    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding : ItemRecentsearchBinding = ItemRecentsearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    fun removeItem(position : Int){
        searchlist.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(searchlist[position])

        holder.binding.itemRecentsearchTv.setOnClickListener{
            mItemClickListener.onItemClick(searchlist[position])
        }
        holder.binding.itemRecentsearchDelIv.setOnClickListener{
            mItemClickListener.removestr(searchlist[position],position)
        }
    }

    override fun getItemCount(): Int = searchlist.size

    inner class Viewholder(var binding : ItemRecentsearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword : String){
            binding.itemRecentsearchTv.text = keyword
        }
    }

}