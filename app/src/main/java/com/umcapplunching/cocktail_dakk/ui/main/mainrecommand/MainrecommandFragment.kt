package com.umcapplunching.cocktail_dakk.ui.main.mainrecommand

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.CocktailDatabase
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_Mainrec
import com.umcapplunching.cocktail_dakk.databinding.FragmentMainrecommandBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.main.mainrecommand.MainrecService.Mainrec
import com.umcapplunching.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecService
import com.umcapplunching.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecView
import com.umcapplunching.cocktail_dakk.utils.getaccesstoken
import kotlinx.coroutines.launch
import kotlin.math.abs
import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.umcapplunching.cocktail_dakk.ui.BaseFragmentByDataBinding
import com.umcapplunching.cocktail_dakk.ui.main.MainViewModel


//class MainrecommandFragment : BaseFragment<FragmentMainrecommandBinding>(FragmentMainrecommandBinding::inflate), MainrecView {
class MainrecommandFragment : BaseFragmentByDataBinding<FragmentMainrecommandBinding>(R.layout.fragment_mainrecommand),MainrecView {

    val mainrecService = MainrecService()
    lateinit var bannerAdapter : BannerViewpagerAdapter
    private lateinit var callback: OnBackPressedCallback
    private lateinit var mainViewModel : MainViewModel

    override fun initView() {
        //메인화면 서버연결
        mainrecService.setmainrecView(this)
//        CocktailDB = (activity as MainActivity).CocktailDb
        val screenWidth = resources.displayMetrics.widthPixels // 스마트폰의 너비 길이를 가져옴
        bannerAdapter = BannerViewpagerAdapter(this)

        val offsetPx = screenWidth *0.05f
        binding.mainRecVp.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
            page.scaleY = 0.8f + (1 - abs(position)) * 0.15f
        }
        binding.mainRecVp.offscreenPageLimit = 1 // 몇 개의 페이지를 미리 로드 해둘것인지

//        launch {
            mainrecService.mainRec(getaccesstoken(requireContext()))
//        }
    }

    override fun initViewModel() {
        binding.lifecycleOwner = this
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onMainrecLoading() {
    }

    override fun onMainrecSuccess(mainrecList : Mainrec) {
        // DB설정
        val activity: Activity? = activity
        if ( this.isAdded && activity != null) {
            mainViewModel.deletemainRecAll()
            binding.mainRecLoadingPb.visibility = View.GONE
            binding.mainRecTv.text = "${mainrecList.nickname} ${resources.getString(R.string.main_coktailrecommand)}"
            for (i in mainrecList.userRecommendationLists.indices){
                bannerAdapter.addFragment(mainrecList.userRecommendationLists[i].cocktailInfoId,mainrecList.userRecommendationLists[i].cocktailImageURL)
                //DB에 저장
                mainViewModel.mainRecInsert(Cocktail_Mainrec(
                    mainrecList.userRecommendationLists[i].koreanName,mainrecList.userRecommendationLists[i].cocktailInfoId
                ))
            }
            binding.mainRecVp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            binding.mainRecVp.adapter = bannerAdapter
            binding.mainRecVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            binding.mainRecIndicator.setViewPager2(binding.mainRecVp)
        }
    }

    override fun onSignUpFailure(code: Int, message: String) {
        val activity: Activity? = activity
            if (isAdded && activity != null) {
            binding.mainRecLoadingPb.visibility = View.GONE
            if (code==5000){
                (activity as MainActivity).TokenrefreshInMain()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
    }
    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

}