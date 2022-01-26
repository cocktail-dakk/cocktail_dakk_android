//package com.cock_tail.test_xml.ui.main.mrecommand
package com.example.cocktail_dakk.ui.main.mainrecommand

import android.net.Uri
import androidx.viewpager2.widget.ViewPager2
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.FragmentMainrecommandBinding
import com.example.cocktail_dakk.ui.BaseFragment

//import com.cock_tail.test_xml.R
//import com.cock_tail.test_xml.databinding.FragmentMainrecommandBinding
//import com.cock_tail.test_xml.ui.BaseFragment
//import com.cock_tail.test_xml.ui.main.adapter.BannerViewpagerAdapter

class MainrecommandFragment : BaseFragment<FragmentMainrecommandBinding>(FragmentMainrecommandBinding::inflate) {
    override fun initAfterBinding() {
        val bannerAdapter = BannerViewpagerAdapter(this)
        var uri : Uri? = Uri.parse("https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/img_cocktail_b%26b_main.png")
        bannerAdapter.addFragment(R.drawable.img_cocktail_alaskaicedtea_main,uri)
        uri  = Uri.parse("https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/img_cocktail_b%26b_main.png")
        bannerAdapter.addFragment(R.drawable.img_cocktail_alaskaicedtea_main, uri)
        uri  = Uri.parse("https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_b-52.webp")
        bannerAdapter.addFragment(R.drawable.img_cocktail_alaskaicedtea_main,uri)
        uri  = Uri.parse("https://cocktail-dakk.s3.ap-northeast-2.amazonaws.com/nukki/img_cocktail_alaskaicedtea.webp")
        bannerAdapter.addFragment(R.drawable.img_cocktail_alaskaicedtea_main,uri)

//        bannerAdapter.addFragment(R.drawable.img_cocktail_alaskaicedtea_main)
//        bannerAdapter.addFragment(R.drawable.img_cocktail_bluesapphire_main)
//        bannerAdapter.addFragment(R.drawable.img_cocktail_bluestar_main)
//        bannerAdapter.addFragment(R.drawable.img_cocktail_brandysour_main)
//        bannerAdapter.addFragment(R.drawable.img_cocktail_21century_main)
//        bannerAdapter.addFragment(R.drawable.img_cocktail_b_b_main)
//        bannerAdapter.addFragment(R.drawable.img_cocktail_b_52_main)
//

        binding.mainRecVp.adapter = bannerAdapter
        binding.mainRecVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        binding.mainRecIndicator.setViewPager(binding.mainRecVp)
        binding.mainRecIndicator.setViewPager2(binding.mainRecVp)

    }
}