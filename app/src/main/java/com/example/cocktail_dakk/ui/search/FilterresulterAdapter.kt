package com.example.cocktail_dakk.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktail_dakk.databinding.ItemRecentsearchBinding

class FilterresulterAdapter(private var resultlist : ArrayList<String> ) : RecyclerView.Adapter<FilterresulterAdapter.Viewholder>() {

    interface MyItemClickListener{
        fun onItemClick(cocktail: String)
        fun removestr(resultlist: String,position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    //클릭 리스너 설정
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding : ItemRecentsearchBinding = ItemRecentsearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    fun removeItem(position : Int){
        resultlist.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(resultlist[position])

//        holder.binding.itemRecentsearchTv.setOnClickListener{
//            mItemClickListener.onItemClick(resultlist[position])
//        }

        holder.binding.itemRecentsearchDelIv.setOnClickListener{
            mItemClickListener.removestr(resultlist[position],position)
        }

    }

    override fun getItemCount(): Int = resultlist.size

    inner class Viewholder(var binding : ItemRecentsearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword : String){
            binding.itemRecentsearchTv.text = keyword
        }
    }


}