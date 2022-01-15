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
        bannerAdapter.addFragment(R.drawable.main_cocktail)
        bannerAdapter.addFragment(R.drawable.main_cocktail2)
        bannerAdapter.addFragment(R.drawable.recommend_todays)
        bannerAdapter.addFragment(R.drawable.recommend_todays2)
        bannerAdapter.addFragment(R.drawable.detail_bg)
        binding.mainRecVp.adapter = bannerAdapter
        binding.mainRecVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        binding.mainRecIndicator.setViewPager(binding.mainRecVp)
        binding.mainRecIndicator.setViewPager2(binding.mainRecVp)
    }
}