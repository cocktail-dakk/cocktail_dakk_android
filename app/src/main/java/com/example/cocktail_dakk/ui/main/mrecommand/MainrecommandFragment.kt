//package com.cock_tail.test_xml.ui.main.mrecommand
package com.example.cocktail_dakk.ui.main.mrecommand

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
        bannerAdapter.addFragment(R.drawable.img_cocktail_alaskaicedtea_main)
        bannerAdapter.addFragment(R.drawable.img_cocktail_bluesapphire_main)
        bannerAdapter.addFragment(R.drawable.img_cocktail_bluestar_main)
        bannerAdapter.addFragment(R.drawable.img_cocktail_brandysour_main)
        bannerAdapter.addFragment(R.drawable.img_cocktail_21century_main)
        bannerAdapter.addFragment(R.drawable.img_cocktail_b_b_main)
        bannerAdapter.addFragment(R.drawable.img_cocktail_b_52_main)
//        bannerAdapter.addFragment(R.drawable.test1)
//        bannerAdapter.addFragment(R.drawable.test2)
//        bannerAdapter.addFragment(R.drawable.test3)
//        bannerAdapter.addFragment(R.drawable.test4)
//        bannerAdapter.addFragment(R.drawable.test5)
        binding.mainRecVp.adapter = bannerAdapter
        binding.mainRecVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        binding.mainRecIndicator.setViewPager(binding.mainRecVp)
        binding.mainRecIndicator.setViewPager2(binding.mainRecVp)
    }
}