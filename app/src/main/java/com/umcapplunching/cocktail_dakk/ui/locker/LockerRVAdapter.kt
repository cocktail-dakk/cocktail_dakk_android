package com.umcapplunching.cocktail_dakk.ui.locker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.data.entities.Cocktail_locker
import com.umcapplunching.cocktail_dakk.databinding.ItemLockerCocktailBinding
import com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService.BookmarkBody

class LockerRVAdapter(private val cocktailList: List<BookmarkBody>) :
    RecyclerView.Adapter<LockerRVAdapter.ViewHolder>(){

    private var selectedItemPosition: Int = 0
    fun changeSelcetedPosition(num: Int) {
        selectedItemPosition = num
    }

    // 클릭 인터페이스를 정의
    interface MyItemClickListener{
        fun onItemClick(cocktail: BookmarkBody, position: Int)
        // fun onRemoveCocktail(position: Int)
    }

    // 클릭 리스너 선언
    private lateinit var mItemClickListener: MyItemClickListener

    // 클릭 리스너 등록 메서드 (메인 액티비티에서 inner Class로 호출)
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    // 뷰홀더 생성해주는 함수 => 처음에 몇 번만 호출됨
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LockerRVAdapter.ViewHolder {
        val binding: ItemLockerCocktailBinding = ItemLockerCocktailBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    // 뷰홀더에 Data 를 binding 위아래로 스크롤 할 때마다 엄청나게 호출
    // 뷰홀더가 매개변수로 들어와서 자식뷰에 접근가능 => 데이터 바인딩
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cocktailList[position])
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(cocktailList[position], position)
            notifyDataSetChanged()
        }
        if(selectedItemPosition == position){
            holder.boarder.borderColor = Color.parseColor("#FFFFFFFF")//"#606200EE"
            holder.boarder.setCircleBackgroundColorResource(R.color.nam)
            holder.binding.itemLockerCircleCi2.visibility = View.VISIBLE
        }
        else {
            holder.boarder.borderColor = Color.parseColor("#E1E1E1")
            holder.boarder.setCircleBackgroundColorResource(R.color.soft_grey)
            holder.binding.itemLockerCircleCi2.visibility = View.GONE
        }
    }

    fun addItems(cocktails: ArrayList<Cocktail_locker>) {
//        cocktailList.clear()
//        cocktailList.addAll(cocktails)
        notifyDataSetChanged()
    }

    fun addItem(cocktail: Cocktail_locker) {
//        cocktailList.add(cocktail)
        notifyDataSetChanged()
    }

    fun removeItems() {
//        cocktailList.clear()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
//        cocktailList.removeAt(position)
        notifyDataSetChanged()
    }

    // 데이터가 Size 가 뭔지 => 마지막이 언제인지를 알 수 있음
    override fun getItemCount() = cocktailList.size

    inner class ViewHolder(val binding: ItemLockerCocktailBinding): RecyclerView.ViewHolder(binding.root){
        var boarder = binding.itemLockerCircleCi
        fun bind(cocktail: BookmarkBody){
            Glide.with(itemView)
                .load(cocktail.smallNukkiImgUrl)
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.itemLockerCircleCi)
            binding.itemLockerTextTv.text = cocktail.koreanName
        }
    }
}