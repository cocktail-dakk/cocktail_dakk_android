package com.example.cocktail_dakk.ui.search

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.Cocktail
import com.example.cocktail_dakk.data.entities.Cocktail_SearchList
import com.example.cocktail_dakk.databinding.ItemCocktailBinding
import com.example.cocktail_dakk.ui.search.coktaillist.KeywrodlistRvAdapter

class SearchlistRvAdapter(private var cocktaillist : ArrayList<Cocktail_SearchList>) : RecyclerView.Adapter<SearchlistRvAdapter.Viewholder>() {

    private lateinit var mItemClickListener: MyItemClickListener

    interface MyItemClickListener{
        fun onItemClick(cocktail: Cocktail_SearchList)
    }

    fun setClickListiner(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun addItem(cocktail: Cocktail_SearchList){
        cocktaillist.add(cocktail)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding:ItemCocktailBinding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(cocktaillist[position])
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(cocktaillist[position])    //외부에서 처리할 수 있도록
        }
    }

//    fun isonloading(loading : Boolean){
//        cocktaillist.add(null)
//        notifyItemInserted(cocktaillist.size - 1)
//    }
//    fun setLoadingView(b: Boolean) {
//        if (b) {
//            android.os.Handler().post {
//                cocktaillist.add(null)
//                notifyItemInserted(cocktaillist.size - 1)
//            }
//        } else {
//            if (cocktaillist[cocktaillist.size - 1] == null) {
//                cocktaillist.removeAt(cocktaillist.size - 1)
//                notifyItemRemoved(cocktaillist.size)
//            }
//        }
//    }

    override fun getItemCount(): Int = cocktaillist.size

    inner class Viewholder(var binding : ItemCocktailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cocktail : Cocktail_SearchList){
            Glide.with(itemView)
                .load(cocktail.imageURL)
                .thumbnail(0.1f)
                .override(80,160)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.detail_star)
                .into(binding.itemCocktailImgIv)

            binding.itemCocktailNameLocalTv.text = cocktail.localName
            binding.itemCocktailNameEnglishTv.text = cocktail.englishName
            val keywordListAdapter = KeywrodlistRvAdapter(cocktail.keywords)
            binding.itemCocktailKeywordRv.adapter = keywordListAdapter

            initStarPoint(
                cocktail.starPoint,
                binding.itemCocktailStarContext1Iv,
                binding.itemCocktailStarContext2Iv,
                binding.itemCocktailStarContext3Iv,
                binding.itemCocktailStarContext4Iv,
                binding.itemCocktailStarContext5Iv
            )
        }
        private fun initStarPoint(starPoint: Double, star_1: ImageView, star_2: ImageView, star_3: ImageView, star_4: ImageView, star_5: ImageView){

            val starEmpty: Int = R.mipmap.icon_star_off
            val starFull: Int = R.mipmap.icon_star_on
            val starHalf: Int = R.mipmap.icon_star_half

            if (starPoint >= 1.0f) {
                star_1.setImageResource(starFull)
                if (starPoint >= 2.0f) {
                    star_2.setImageResource(starFull)
                    if (starPoint >= 3.0f) {
                        star_3.setImageResource(starFull)
                        if (starPoint >= 4.0f) {
                            star_4.setImageResource(starFull)
                            if (starPoint >= 5.0f) {
                                star_5.setImageResource(starFull)
                            } else if (starPoint >= 4.5f) {
                                star_5.setImageResource(starHalf)
                            } else {
                                star_5.setImageResource(starEmpty)
                            }
                        } else if (starPoint >= 3.5f) {
                            star_4.setImageResource(starHalf)
                        } else {
                            star_4.setImageResource(starEmpty)
                        }
                    } else if (starPoint >= 2.5f) {
                        star_3.setImageResource(starHalf)
                    } else {
                        star_3.setImageResource(starEmpty)
                    }
                } else if (starPoint >= 1.5f) {
                    star_2.setImageResource(starHalf)
                } else {
                    star_2.setImageResource(starEmpty)
                }
            } else {  // 0.0점~0.99점 까지는 예외적으로 0.5 를 줬음. (하나도 안 채워져 있으면 이상해보여서)
                star_1.setImageResource(starHalf)
            }
        }
    }
}