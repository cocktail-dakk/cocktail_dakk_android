package com.umcapplunching.cocktail_dakk.ui.locker


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.data.entities.Cocktail_locker
import com.umcapplunching.cocktail_dakk.databinding.FragmentLockerBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService.BookmarkBody
import com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService.BookmarkService
import com.umcapplunching.cocktail_dakk.ui.locker.bookmarkService.getIsLikeView
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.start.Service.TokenResfreshView
import com.umcapplunching.cocktail_dakk.ui.start.Service.Tokenrespbody
import com.umcapplunching.cocktail_dakk.ui.start.Service.UserService
import com.umcapplunching.cocktail_dakk.utils.getaccesstoken
import com.umcapplunching.cocktail_dakk.utils.getrefreshtoken
import com.umcapplunching.cocktail_dakk.utils.setaccesstoken
import com.umcapplunching.cocktail_dakk.utils.setrefreshtoken

class LockerFragment : BaseFragment<FragmentLockerBinding>(FragmentLockerBinding::inflate),getIsLikeView, TokenResfreshView {

    val bookmarkService = BookmarkService()
    val userService = UserService()
    lateinit var lockerCocklist :  List<BookmarkBody>
    private lateinit var callback: OnBackPressedCallback

    override fun initAfterBinding() {
        // 더미데이터랑 Adapter 연결
        bookmarkService.setgetisLikeView(this)
        userService.settokenRefreshView(this)
        setCurrentPage()
        bookmarkService.getisLikeCocktail(getaccesstoken(requireContext()))
    }
    private fun setCurrentPage() {
        var spf = activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab", 2)
        editor.commit()
    }

    override fun ongetIsLikeLoading() {
    }

    override fun ongetIsLikeSuccess(getislikebody: List<BookmarkBody>) {
        lockerCocklist = getislikebody
        if (lockerCocklist.size == 0){
            binding.lockerCocktailEnglishNameTv.setText("즐겨찾기 된 칵테일이 없습니다.")
        }
        else {
            val cocktailRecyclerViewAdapter = LockerRVAdapter(lockerCocklist)
            // 리사이클러뷰에 어댑터를 연결
            binding.lockerCocktailListRv.adapter = cocktailRecyclerViewAdapter
            selectCocktailByCocktail(lockerCocklist[0])
            cocktailRecyclerViewAdapter.setMyItemClickListener(object :
                LockerRVAdapter.MyItemClickListener {
                override fun onItemClick(cocktail: BookmarkBody, position: Int) {
                    cocktailRecyclerViewAdapter.changeSelcetedPosition(position)
                    selectCocktailByCocktail(cocktail)
                }
            })
        }
    }

    override fun ongetIsLikeFailure(code: Int, message: String) {
        if (code == 5000){
            Log.d("refreshtoken", getrefreshtoken(requireContext()).toString())
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
            var spf = activity?.getSharedPreferences("lockerflag", AppCompatActivity.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = spf?.edit()!!
            editor.putInt("lockerflag", 0)
            editor.apply()
            (activity as MainActivity).detailcocktail(cocktail.cocktailInfoId)
        }

        var keywords : ArrayList<String> = ArrayList()
        for (i in 0 until cocktail.cocktailKeyword.size) {
            keywords.add(cocktail.cocktailKeyword[i].keywordName.trim())
        }
        val sv = binding.lockerCocktailKeywordSv
        val l1 = binding.lockerCocktailKeywordLinearLa
        l1.removeAllViews()
        for (i in 0 until keywords.size) {
            l1.addView(createKeyword(keywords[i], 15.0f, "000000", 70))
            if (i == keywords.size - 1) {
                break
            }
            val vu = View(this.activity)
            var layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity, 10), 0)
            layoutparam.setMargins(0, 100, 0, 0)
            vu.layoutParams = layoutparam
            l1.addView(vu)
        }

        // 스크롤 안되는 문제 해결할것! todo
//        sv.removeAllViews()
//        sv.addView(l1)
    }

    private fun createKeyword(
        inputText: String,
        size: Float,
        color: String,
        width: Int = -1,
        height: Int = -1
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
        setaccesstoken(requireContext(),tokenSigninbody.token)
        setrefreshtoken(requireContext(),tokenSigninbody.refreshToken)
    }

    override fun onTokenRefreshFailure(code: Int, message: String) {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

}