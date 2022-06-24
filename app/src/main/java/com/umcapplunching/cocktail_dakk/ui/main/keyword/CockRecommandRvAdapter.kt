package com.umcapplunching.cocktail_dakk.ui.main.keyword

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.ItemMainrecCocktailBinding

class CockRecommandRvAdapter(private var cocktaillist : ArrayList<com.umcapplunching.cocktail_dakk.data.entities.Cocktail_searchList>) : RecyclerView.Adapter<CockRecommandRvAdapter.Viewholder>() {

    interface MyItemClickListener{
        fun onItemClick(cocktail: com.umcapplunching.cocktail_dakk.data.entities.Cocktail_searchList)
    }
    private lateinit var mItemClickListener: MyItemClickListener

    //클릭 리스너 설정
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding:ItemMainrecCocktailBinding = ItemMainrecCocktailBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return Viewholder(binding)
    }
    fun addItem(cocktail: com.umcapplunching.cocktail_dakk.data.entities.Cocktail_searchList){
        cocktaillist.add(cocktail)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(cocktaillist[position])
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(cocktaillist[position])    //외부에서 처리할 수 있도록 내보내기
        }
    }

    override fun getItemCount(): Int = cocktaillist.size

    inner class Viewholder(var binding : ItemMainrecCocktailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cocktail : com.umcapplunching.cocktail_dakk.data.entities.Cocktail_searchList){
            Glide.with(itemView)
                .load(cocktail.imageURL)
                .thumbnail(0.1f)
                .override(80,160)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.detail_star)
                .into(binding.itemKeywordrecThemecockIv)
            binding.itemKeywordrecThemecockKnameTv.text =cocktail.localName
            binding.itemKeywordrecThemecockEnameTv.text =cocktail.englishName
        }
    }
}