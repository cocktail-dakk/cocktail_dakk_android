package com.example.cocktail_dakk.ui.main.keyword

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail
import com.example.cocktail_dakk.data.entities.Cocktail_SearchList
import com.example.cocktail_dakk.databinding.ItemMainrecCocktailBinding

class CockRecommandRvAdapter(private var cocktaillist : ArrayList<Cocktail_SearchList>) : RecyclerView.Adapter<CockRecommandRvAdapter.Viewholder>() {

    interface MyItemClickListener{
        fun onItemClick(cocktail: Cocktail_SearchList)
//        fun setminiplayer(album: Album)
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
    fun addItem(cocktail: Cocktail_SearchList ){
        cocktaillist.add(cocktail)
        notifyDataSetChanged()
    }

    fun removeItem(position : Int){
        cocktaillist.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(cocktaillist[position])
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(cocktaillist[position])    //외부에서 처리할 수 있도록 내보내기
        }
//        holder.binding.lockerSavedsongPlayIv.setOnClickListener{
//            mItemClickListener.setminiplayer(albumList[position])
//        }
    }

    override fun getItemCount(): Int = cocktaillist.size

    inner class Viewholder(var binding : ItemMainrecCocktailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cocktail : Cocktail_SearchList){
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