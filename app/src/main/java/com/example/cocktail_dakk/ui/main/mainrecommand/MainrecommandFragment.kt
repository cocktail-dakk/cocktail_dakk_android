//package com.cock_tail.test_xml.ui.main.mrecommand
package com.example.cocktail_dakk.ui.main.mainrecommand

import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.FragmentMainrecommandBinding
import com.example.cocktail_dakk.ui.BaseFragment
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainRec
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecService
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecView

//import com.cock_tail.test_xml.R
//import com.cock_tail.test_xml.databinding.FragmentMainrecommandBinding
//import com.cock_tail.test_xml.ui.BaseFragment
//import com.cock_tail.test_xml.ui.main.adapter.BannerViewpagerAdapter

class MainrecommandFragment : BaseFragment<FragmentMainrecommandBinding>(FragmentMainrecommandBinding::inflate), MainrecView {
    override fun initAfterBinding() {

        //메인화면 서버연결
        val mainrecService = MainrecService()
        mainrecService.setmainrecView(this)
        mainrecService.mainRec("1234")

        val bannerAdapter = BannerViewpagerAdapter(this)
        //더미데이터
//        var uri : Uri? = Uri.parse("https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/img_cocktail_b%26b_main.png")
//        bannerAdapter.addFragment(R.drawable.img_cocktail_alaskaicedtea_main,"https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/img_cocktail_b%26b_main.png")
//        bannerAdapter.addFragment(R.drawable.img_cocktail_alaskaicedtea_main, uri)
//        bannerAdapter.addFragment(R.drawable.img_cocktail_alaskaicedtea_main,uri)
//        bannerAdapter.addFragment(R.drawable.img_cocktail_alaskaicedtea_main,uri)


        binding.mainRecVp.adapter = bannerAdapter
        binding.mainRecVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.mainRecIndicator.setViewPager2(binding.mainRecVp)

        var spf = activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab", 1)
        editor.commit()
    }

    override fun onMainrecLoading() {
    }

    override fun onMainrecSuccess(mainrecList : List<MainRec>) {
        val bannerAdapter = BannerViewpagerAdapter(this)
        for (i in 0..mainrecList.size-1){
            bannerAdapter.addFragment(mainrecList[i].cocktailImageURL)
        }
        binding.mainRecVp.adapter = bannerAdapter
        binding.mainRecVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.mainRecIndicator.setViewPager2(binding.mainRecVp)
    }

    override fun onSignUpFailure(code: Int, message: String) {
        Log.d("MainRecAPI",message.toString())
    }
}