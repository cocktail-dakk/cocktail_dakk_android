package com.example.cocktail_dakk.ui.main.mainrecommand

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.cocktaildata_db.CocktailDatabase
import com.example.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_Mainrec
import com.example.cocktail_dakk.data.entities.getUser
import com.example.cocktail_dakk.databinding.FragmentMainrecommandBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.MainActivity
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.Mainrec
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecService
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecView
import com.example.cocktail_dakk.utils.getaccesstoken
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.launch


class MainrecommandFragment : BaseFragment<FragmentMainrecommandBinding>(FragmentMainrecommandBinding::inflate), MainrecView {

    private lateinit var CocktailDB : CocktailDatabase

    override fun initAfterBinding() {
        //메인화면 서버연결
        val mainrecService = MainrecService()
        mainrecService.setmainrecView(this)
        launch {
            mainrecService.mainRec(getaccesstoken(requireContext()))
        }
        val spf = activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab", 1)
        editor.apply()
    }

    override fun onMainrecLoading() {
        requireActivity().runOnUiThread(object : Runnable{
            override fun run() {
//                requireActivity().window.setFlags(
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                binding.mainRecLoadingPb.visibility = View.VISIBLE
            }
        })
    }

    override fun onMainrecSuccess(mainrecList : Mainrec) {
        //DB설정
        CocktailDB = CocktailDatabase.getInstance(requireContext())!!
        CocktailDB.MainrecDao().deleteAllCocktail()
        /* 여백, 너비에 대한 정의 */
        val screenWidth = resources.displayMetrics.widthPixels // 스마트폰의 너비 길이를 가져옴

        val offsetPx = screenWidth *0.05f
        binding.mainRecVp.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
            page.scaleY = 0.8f + (1 - Math.abs(position)) * 0.15f
        }
        binding.mainRecVp.offscreenPageLimit = 1 // 몇 개의 페이지를 미리 로드 해둘것인지

//        mainrecList.userRecommendationLists
        val bannerAdapter = BannerViewpagerAdapter(this)

        requireActivity().runOnUiThread(object : Runnable{
            override fun run() {
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                binding.mainRecLoadingPb.visibility = View.GONE
                binding.mainRecTv.setText(mainrecList.nickname + resources.getString(R.string.main_coktailrecommand))
            }
        })

        for (i in 0 until mainrecList.userRecommendationLists.size){
            bannerAdapter.addFragment(mainrecList.userRecommendationLists[i].cocktailInfoId,mainrecList.userRecommendationLists[i].cocktailImageURL)
            //DB에 저장
            CocktailDB.MainrecDao().insert(
            Cocktail_Mainrec(
                mainrecList.userRecommendationLists[i].koreanName,mainrecList.userRecommendationLists[i].cocktailInfoId
            ))
        }
        binding.mainRecVp.adapter = bannerAdapter
        binding.mainRecVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.mainRecIndicator.setViewPager2(binding.mainRecVp)

    }

    override fun onSignUpFailure(code: Int, message: String) {
        requireActivity().runOnUiThread(object : Runnable{
            override fun run() {
                binding.mainRecLoadingPb.visibility = View.GONE
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        })
        if (code==5000){
                (activity as MainActivity).TokenrefreshInMain()
        }
    }
}