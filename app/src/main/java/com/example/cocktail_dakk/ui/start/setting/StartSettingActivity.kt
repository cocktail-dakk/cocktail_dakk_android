package com.example.cocktail_dakk.ui.start.setting

import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.cocktail_dakk.databinding.ActivityStartSettingBinding
import com.example.cocktail_dakk.ui.BaseActivity

import com.example.cocktail_dakk.ui.main.adapter.StartSettingViewpagerAdapter
import com.example.cocktail_dakk.ui.start.setting.fragment.*

class StartSettingActivity : BaseActivity<ActivityStartSettingBinding>(ActivityStartSettingBinding::inflate) {

    private lateinit var viewPager: ViewPager2
    lateinit var nickname : String

    override fun initAfterBinding() {

        val binding = ActivityStartSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nickname = intent.getStringExtra("nickname").toString().trim()

        val adapter = StartSettingViewpagerAdapter(this)


        adapter.addFragmentInStartSetting(StartAStartFragment(nickname))
        adapter.addFragmentInStartSetting(StartBAgeFragment())
        adapter.addFragmentInStartSetting(StartCGenderFragment())
        adapter.addFragmentInStartSetting(StartDDrinkcapFragment())
        adapter.addFragmentInStartSetting(StartELikeFragment())
        adapter.addFragmentInStartSetting(StartFKeywordFragment())

        viewPager = binding.startSettingVp
        //손으로 움직이는거 막기
        viewPager.isUserInputEnabled = false
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val dotsIndicator = binding.startSettingDot
        dotsIndicator.setViewPager2(viewPager)

        //닷인디케이터 터치안되게 막아버림
        binding.startSettingDotback.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
            }
        })

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

    fun Nextpage(){
        viewPager.currentItem = viewPager.currentItem + 1
    }

    fun Undopage(){
        viewPager.currentItem = viewPager.currentItem - 1
    }


}