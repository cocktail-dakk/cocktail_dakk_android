package com.umcapplunching.cocktail_dakk.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umcapplunching.cocktail_dakk.ui.start.setting.StartSettingActivity

class StartSettingViewpagerAdapter(fragment: StartSettingActivity) : FragmentStateAdapter(fragment) {

    private val fragmentList : ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun addFragmentInStartSetting(fragment: Fragment){
        fragmentList.add(fragment)
        notifyItemInserted(fragmentList.size - 1)
        // 0,1,2 있는 상태에서 1개가 새로들어오면 position==3 이 들어온 것이므로 size-1을 notify
    }

}