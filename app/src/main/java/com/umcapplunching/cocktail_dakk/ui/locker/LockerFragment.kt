package com.umcapplunching.cocktail_dakk.ui.locker


import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.FragmentLockerBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService.BookmarkBody
import com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService.BookmarkService
import com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService.getIsLikeView
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.search.searchService.Keyword
import com.umcapplunching.cocktail_dakk.ui.start.Service.TokenResfreshView
import com.umcapplunching.cocktail_dakk.ui.start.Service.Tokenrespbody
import com.umcapplunching.cocktail_dakk.ui.start.Service.UserService
import com.umcapplunching.cocktail_dakk.utils.getrefreshtoken
import com.umcapplunching.cocktail_dakk.utils.setrefreshtoken
import kotlinx.coroutines.launch
import android.app.Application
import android.content.Intent
import androidx.annotation.UiThread
import androidx.lifecycle.ViewModelProvider
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication
import com.umcapplunching.cocktail_dakk.data.entities.Cocktail
import com.umcapplunching.cocktail_dakk.ui.BaseFragmentByDataBinding
import com.umcapplunching.cocktail_dakk.ui.menu_detail.MenuDetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class LockerFragment : BaseFragmentByDataBinding<FragmentLockerBinding>(R.layout.fragment_locker),getIsLikeView, TokenResfreshView {

    val bookmarkService = BookmarkService()
    val userService = UserService()
    lateinit var lockerCocklist :  List<BookmarkBody>
    lateinit var cocktailRecyclerViewAdapter : LockerRVAdapter
    private lateinit var lockerViewModel : LockerViewModel

    override fun initViewModel() {
        lockerViewModel = ViewModelProvider(requireActivity()).get(LockerViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        // 프래그먼트 재생성 될때마다 리스트 가져오기
        // Detail Activity에서만 즐겨찾기 설정이 가능해서 이렇게 해도 되긴함
        CoroutineScope(Dispatchers.IO).launch {
            bookmarkService.getisLikeCocktail()
        }
    }

    override fun initView() {
        // 더미데이터랑 Adapter 연결
        bookmarkService.setgetisLikeView(this)
        userService.settokenRefreshView(this)

        lockerCocklist = listOf(BookmarkBody(-1, listOf(Keyword(0," "))," "," "," "," "))
        cocktailRecyclerViewAdapter = LockerRVAdapter(lockerCocklist)
        binding.lockerCocktailListRv.adapter = cocktailRecyclerViewAdapter

        lockerViewModel.visibleItemList.observe(this,{
            cocktailRecyclerViewAdapter.updateList(it)
            cocktailRecyclerViewAdapter.setMyItemClickListener(object : LockerRVAdapter.MyItemClickListener{
                override fun onItemClick(cocktail: BookmarkBody, position: Int) {
                    lockerViewModel.setCurrentPosition(position)
                }
            })
            // 현재 선택하고있는 포지션의 아이템보다 리스트 마지막 인덱스가 작을 때
            if(lockerViewModel.currentPosition.value!! > it.lastIndex){
                if(it.lastIndex <0){
                    lockerViewModel.setCurrentPosition(0)
                }else{
                    lockerViewModel.setCurrentPosition(it.lastIndex)
                }
            }else{
                lockerViewModel.setCurrentPosition(lockerViewModel.currentPosition.value!!)
            }
        })

        lockerViewModel.currentPosition.observe(this,{
            selectCocktailByCocktail(lockerViewModel.visibleItemList.value!![it])
            cocktailRecyclerViewAdapter.changeSelcetedPosition(it)
        })

    }

    override fun initListener() {
        val onScrollListener = object: RecyclerView.OnScrollListener() {
            var temp: Int = 0
            override fun onScrolled(@NonNull recyclerView:RecyclerView, dx:Int, dy:Int) {
                if(temp == 1) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!binding.lockerCocktailListRv.canScrollHorizontally(1)) {
                        binding.lockerRightarrowIv.visibility = View.INVISIBLE
                    }
                    else if (!binding.lockerCocktailListRv.canScrollHorizontally(-1)) {
                        binding.lockerLeftarrowIv.visibility = View.INVISIBLE
                    }
                    else{
                        binding.lockerLeftarrowIv.visibility = View.VISIBLE
                        binding.lockerRightarrowIv.visibility = View.VISIBLE
                    }
                }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                temp = 1
            }
        }
        binding.lockerCocktailListRv.setOnScrollListener(onScrollListener)
    }

    override fun ongetIsLikeLoading() {
    }

    override fun ongetIsLikeSuccess(getislikebody: List<BookmarkBody>){
        val activity: Activity? = activity
        if (  activity != null) {
            lockerCocklist = getislikebody
            if (lockerCocklist.isEmpty()){
                requireActivity().runOnUiThread{
                    binding.lockerCocktailListRv.visibility = View.INVISIBLE
                    binding.lockerHasnococktailTv.visibility = View.VISIBLE
                    binding.lockerCocktailInfoContainer.visibility = View.GONE
                }
            }
            else {
                requireActivity().runOnUiThread {
                    binding.lockerCocktailListRv.visibility = View.VISIBLE
                    binding.lockerHasnococktailTv.visibility = View.GONE
                    binding.lockerCocktailInfoContainer.visibility = View.VISIBLE
                }
                lockerViewModel.setBookmarkList(lockerCocklist)
            }
        }
    }

    override fun ongetIsLikeFailure(code: Int, message: String) {
        if (code == 5000){
            userService.TokenRefresh(getrefreshtoken(requireContext()))
        }
    }

    private fun selectCocktailByCocktail(cocktail: BookmarkBody) {
        binding.lockerCocktailLocalNameTv.text = cocktail.koreanName
        binding.lockerCocktailEnglishNameTv.text = cocktail.englishName
        Glide.with(this)
            .load(cocktail.nukkiImgUrl)
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.lockerCocktailImgIv)
        binding.lockerCocktailImgIv.setOnClickListener {
//            (activity as MainActivity).detailcocktail(cocktail.cocktailInfoId)
            val intent = Intent(requireContext(), MenuDetailActivity::class.java)
            intent.putExtra("cocktailId",cocktail.cocktailInfoId)
            startActivity(intent)
        }

        val keywords : ArrayList<String> = ArrayList()
        for (i in cocktail.cocktailKeyword.indices) {
            if(cocktail.cocktailKeyword[i].keywordName!=" "){
                keywords.add(cocktail.cocktailKeyword[i].keywordName.trim())
            }
        }

        val l1 = binding.lockerCocktailKeywordLinearLa
        l1.removeAllViews()
        for (i in 0 until keywords.size) {
            l1.addView(createKeyword(keywords[i], 15.0f, "000000"))
            if (i == keywords.size - 1) {
                break
            }
            val vu = View(this.activity)
            val layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity, 10), 0)
            layoutparam.setMargins(0, 100, 0, 0)
            vu.layoutParams = layoutparam
            l1.addView(vu)
        }
    }

    private fun createKeyword(
        inputText: String,
        size: Float = 15.0f,
        color: String = "000000"
    ): TextView {
        val textView = TextView(this.activity)
        textView.text = inputText
        textView.textSize = size
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setBackgroundResource(R.drawable.round_rect_grey_in_sky)
        textView.setTextColor(Color.parseColor("#$color"))
        textView.setPadding(DPtoPX(this.activity, 10), DPtoPX(this.activity, 4), DPtoPX(this.activity, 10), DPtoPX(this.activity, 4))
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.layoutParams = lp
        return textView
    }

    private fun DPtoPX(context: FragmentActivity?, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context?.resources?.displayMetrics
        ).toInt()
    }

    override fun onTokenRefreshLoading() {
    }

    override fun onTokenRefreshSuccess(tokenSigninbody: Tokenrespbody) {
        CocktailDakkApplication.AccessToken = tokenSigninbody.token
        CocktailDakkApplication.RefreshToken = tokenSigninbody.refreshToken
        setrefreshtoken(requireContext(),tokenSigninbody.refreshToken)
    }

    override fun onTokenRefreshFailure(code: Int, message: String) {
    }


}