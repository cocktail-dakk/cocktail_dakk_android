package com.example.cocktail_dakk.ui.main.mainrecommand

import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.data.entities.datafordb.CocktailDatabase
import com.example.cocktail_dakk.data.entities.datafordb.Cocktail_Mainrec
import com.example.cocktail_dakk.databinding.FragmentMainrecommandBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.Mainrec
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecService
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecView


class MainrecommandFragment : BaseFragment<FragmentMainrecommandBinding>(FragmentMainrecommandBinding::inflate), MainrecView {

    private lateinit var CocktailDB : CocktailDatabase

    override fun initAfterBinding() {
        //메인화면 서버연결
        val mainrecService = MainrecService()
        mainrecService.setmainrecView(this)
        mainrecService.mainRec("1234")

        //배너어뎁터
//        val bannerAdapter = BannerViewpagerAdapter(this)
//        binding.mainRecVp.adapter = bannerAdapter
//        binding.mainRecVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        binding.mainRecIndicator.setViewPager2(binding.mainRecVp)

        val spf = activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab", 1)
        editor.apply()
    }

    override fun onMainrecLoading() {
    }

    override fun onMainrecSuccess(mainrecList : Mainrec) {
        //DB설정
        CocktailDB = CocktailDatabase.getInstance(requireContext())!!
        CocktailDB.MainrecDao().deleteAllCocktail()
//        if (cocktails.isNotEmpty()) return
//        CocktailDB.MainrecDao().insert(
//            Cocktail_Mainrec(
//                "가나다라",1
//            )
//        )
//        )
        
        /* 여백, 너비에 대한 정의 */
//        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin) // dimen 파일 안에 크기를 정의해두었다.
        val screenWidth = resources.displayMetrics.widthPixels // 스마트폰의 너비 길이를 가져옴
        val offsetPx = screenWidth *0.05f
        binding.mainRecVp.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
            page.scaleY = 0.8f + (1 - Math.abs(position)) * 0.15f
        }
        binding.mainRecVp.offscreenPageLimit = 1 // 몇 개의 페이지를 미리 로드 해둘것인지

//        mainrecList.userRecommendationLists
        val bannerAdapter = BannerViewpagerAdapter(this)
        binding.mainRecTv.setText(mainrecList.nickname + resources.getString(R.string.main_coktailrecommand))
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
        Log.d("MainRecAPI",message)
    }
}