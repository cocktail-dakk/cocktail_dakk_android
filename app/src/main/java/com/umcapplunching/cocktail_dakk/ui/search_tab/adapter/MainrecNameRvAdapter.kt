package com.umcapplunching.cocktail_dakk.ui.search_tab.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_Mainrec
import com.umcapplunching.cocktail_dakk.databinding.ItemSearchtabMainrecBinding

class MainrecNameRvAdapter(private var cocktaillist : List<Cocktail_Mainrec> ) : RecyclerView.Adapter<MainrecNameRvAdapter.Viewholder>() {


    interface MyItemClickListener{
        fun onItemClick(cocktail: Cocktail_Mainrec)
    }
    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding : ItemSearchtabMainrecBinding = ItemSearchtabMainrecBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(cocktaillist[position])
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(cocktaillist[position])    //외부에서 처리할 수 있도록 내보내기
        }
    }

    override fun getItemCount(): Int = cocktaillist.size

    inner class Viewholder(var binding : ItemSearchtabMainrecBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cocktaillist : Cocktail_Mainrec){
            binding.itemSearchtabTv.text = cocktaillist.koreanname
        }
    }

}