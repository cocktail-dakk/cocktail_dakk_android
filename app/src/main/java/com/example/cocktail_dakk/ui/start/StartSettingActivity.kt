package com.example.cocktail_dakk.ui.start

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.cocktail_dakk.databinding.ActivityStartSettingBinding

import com.example.cocktail_dakk.ui.main.adapter.StartSettingViewpagerAdapter

class StartSettingActivity : FragmentActivity(){

    lateinit var binding: ActivityStartSettingBinding
    private lateinit var viewPager: ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityStartSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = StartSettingViewpagerAdapter(this)

        adapter.addFragmentInStartSetting(StartNameFragment())
        adapter.addFragmentInStartSetting(StartAStartFragment())
        adapter.addFragmentInStartSetting(StartBAgeFragment())
        adapter.addFragmentInStartSetting(StartCGenderFragment())
        adapter.addFragmentInStartSetting(StartDDrinkcapFragment())
        adapter.addFragmentInStartSetting(StartELikeFragment())
        adapter.addFragmentInStartSetting(StartFKeywordFragment())

        viewPager = binding.startSettingVp
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val dotsIndicator = binding.startSettingDot
        dotsIndicator.setViewPager2(viewPager)

    }


    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

}