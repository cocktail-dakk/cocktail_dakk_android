package com.example.cocktail_dakk.ui.main

import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.ActivityMainBinding
import com.example.cocktail_dakk.ui.BaseActivity
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecService
import com.example.cocktail_dakk.ui.main.mainrecommand.MainrecService.MainrecView
import java.util.*
import kotlin.collections.ArrayList


class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate){
    private lateinit var navHostFragment: NavHostFragment

    var gijulist = ArrayList<String>()
    var favorkeyword = ArrayList<String>()
    var dosu :Int = 0
    override fun initAfterBinding() {

        setBottomNavigation()
        FilterClcikListener()

        //Log.d("uid테스트",UUID.randomUUID().toString()) //guid

    }

    override fun onResume() {
        super.onResume()
        changeSearchtab()
    }


    fun changeSearchtab(){
        var spf = getSharedPreferences("currenttab", MODE_PRIVATE)
        if (spf.getInt("currenttab",0) == 0){
            binding.mainBottomNavigation.selectedItemId = R.id.searchFragment
        }
        else if (spf.getInt("currenttab",0) == 1){
            binding.mainBottomNavigation.selectedItemId = R.id.homeFragment
        }


    }

    private fun setBottomNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()
        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null
        binding.navHostFragmentContainer.isSaveEnabled = false

    }

    private fun FilterClcikListener() {
        binding.mainFilterBackgroundcoverIv.setOnClickListener {
            //아무것도 안하기
            ShowFilter(false)
            binding.mainFilterBackgroundcoverIv.postDelayed(object : Runnable{
                override fun run() {
                    KeywordReset()
                }
            },300)
        }

        binding.mainFilterExitIv.setOnClickListener {
            ShowFilter(false)
            binding.mainFilterExitIv.postDelayed(object : Runnable{
                override fun run() {
                    KeywordReset()
                }
            },300)
        }

        binding.mainFilterAdjustBt.setOnClickListener {
            ShowFilter(false)
        }

        binding.mainFilterResetLayout.setOnClickListener {
            KeywordReset()
        }

        //기주리스트는 gijulist로 들어감
        SetGijuKeyword()
        //키워드리스트는 keywordlist로 들어감
        SetFavorKeyword()
    
        //Seekbar 리스너
        binding.mainFilterDosuSeekbar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                dosu = seekBar!!.progress
                binding.mainFilterDosunumTv.text = dosu.toString() + " 도"
                Log.d("Filtertest",dosu.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun KeywordReset() {
        //도수
        binding.mainFilterDosuSeekbar.progress = 0

        //기주
        binding.mainFilterGijuVodcaBt.isChecked = false
        binding.mainFilterGijuWiskiBt.isChecked = false
        binding.mainFilterGijuRumBt.isChecked = false
        binding.mainFilterGijuJinBt.isChecked = false
        binding.mainFilterGijuTequilaBt.isChecked = false
        binding.mainFilterGijuLiqueurBt.isChecked = false
        binding.mainFilterGijuBrandyBt.isChecked = false
        binding.mainFilterGijuAnythingBt.isChecked = false

        //취향키워드
        binding.mainFilterKeywordLadykillerBt.isChecked = false
        binding.mainFilterKeywordShooterBt.isChecked = false
        binding.mainFilterKeywordCleanBt.isChecked = false
        binding.mainFilterKeywordTansanBt.isChecked = false
        binding.mainFilterKeywordLayeredBt.isChecked = false
        binding.mainFilterKeywordMartiniBt.isChecked = false
        binding.mainFilterKeywordPrettyBt.isChecked = false
        binding.mainFilterKeywordHighballBt.isChecked = false
        binding.mainFilterKeywordSweetBt.isChecked = false
        binding.mainFilterKeywordDockhanBt.isChecked = false
        binding.mainFilterKeywordSangqueBt.isChecked = false
        binding.mainFilterKeywordFluitfavorBt.isChecked = false
        binding.mainFilterKeywordSsupssupBt.isChecked = false
        binding.mainFilterKeywordOntherrockBt.isChecked = false
        binding.mainFilterKeywordSangkumBt.isChecked = false
        binding.mainFilterKeywordDansunBt.isChecked = false
        binding.mainFilterKeywordMilkBt.isChecked = false
        binding.mainFilterKeywordBockjapBt.isChecked = false
    }

    private fun SetFavorKeyword() {
        var favorListner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.main_filter_keyword_ladykiller_bt -> {
                        favorkeyword.add("레이디킬러")
                    }
                    R.id.main_filter_keyword_shooter_bt -> {
                        favorkeyword.add("슈터")
                    }
                    R.id.main_filter_keyword_clean_bt -> {
                        favorkeyword.add("깔끔한")
                    }
                    R.id.main_filter_keyword_tansan_bt -> {
                        favorkeyword.add("탄산")
                    }
                    R.id.main_filter_keyword_layered_bt -> {
                        favorkeyword.add("레이어드")
                    }
                    R.id.main_filter_keyword_martini_bt -> {
                        favorkeyword.add("마티니")
                    }
                    R.id.main_filter_keyword_pretty_bt -> {
                        favorkeyword.add("예쁜")
                    }
                    R.id.main_filter_keyword_highball_bt -> {
                        favorkeyword.add("하이볼")
                    }
                    R.id.main_filter_keyword_sweet_bt -> {
                        favorkeyword.add("달달한")
                    }
                    R.id.main_filter_keyword_dockhan_bt -> {
                        favorkeyword.add("독한")
                    }
                    R.id.main_filter_keyword_sangque_bt -> {
                        favorkeyword.add("상퀘한")
                    }
                    R.id.main_filter_keyword_fluitfavor_bt -> {
                        favorkeyword.add("과일향")
                    }
                    R.id.main_filter_keyword_ssupssup_bt -> {
                        favorkeyword.add("씁쓸한")
                    }
                    R.id.main_filter_keyword_ontherrock_bt -> {
                        favorkeyword.add("온더락")
                    }
                    R.id.main_filter_keyword_sangkum_bt -> {
                        favorkeyword.add("상큼한")
                    }
                    R.id.main_filter_keyword_dansun_bt -> {
                        favorkeyword.add("단순한")
                    }
                    R.id.main_filter_keyword_milk_bt -> {
                        favorkeyword.add("우유")
                    }
                    R.id.main_filter_keyword_bockjap_bt -> {
                        favorkeyword.add("복잡한")
                    }
                }
            } else {
                when (buttonView.id) {
                    R.id.main_filter_keyword_ladykiller_bt -> {
                        favorkeyword.remove("레이디킬러")
                    }
                    R.id.main_filter_keyword_shooter_bt -> {
                        favorkeyword.remove("슈터")
                    }
                    R.id.main_filter_keyword_clean_bt -> {
                        favorkeyword.remove("깔끔한")
                    }
                    R.id.main_filter_keyword_tansan_bt -> {
                        favorkeyword.remove("탄산")
                    }
                    R.id.main_filter_keyword_layered_bt -> {
                        favorkeyword.remove("레이어드")
                    }
                    R.id.main_filter_keyword_martini_bt -> {
                        favorkeyword.remove("마티니")
                    }
                    R.id.main_filter_keyword_pretty_bt -> {
                        favorkeyword.remove("예쁜")
                    }
                    R.id.main_filter_keyword_highball_bt -> {
                        favorkeyword.remove("하이볼")
                    }
                    R.id.main_filter_keyword_sweet_bt -> {
                        favorkeyword.remove("달달한")
                    }
                    R.id.main_filter_keyword_dockhan_bt -> {
                        favorkeyword.remove("독한")
                    }
                    R.id.main_filter_keyword_sangque_bt -> {
                        favorkeyword.remove("상퀘한")
                    }
                    R.id.main_filter_keyword_fluitfavor_bt -> {
                        favorkeyword.remove("과일향")
                    }
                    R.id.main_filter_keyword_ssupssup_bt -> {
                        favorkeyword.remove("씁쓸한")
                    }
                    R.id.main_filter_keyword_ontherrock_bt -> {
                        favorkeyword.remove("온더락")
                    }
                    R.id.main_filter_keyword_sangkum_bt -> {
                        favorkeyword.remove("상큼한")
                    }
                    R.id.main_filter_keyword_dansun_bt -> {
                        favorkeyword.remove("단순한")
                    }
                    R.id.main_filter_keyword_milk_bt -> {
                        favorkeyword.remove("우유")
                    }
                    R.id.main_filter_keyword_bockjap_bt -> {
                        favorkeyword.remove("복잡한")
                    }
                }
            }
            Log.d("Filtertest", favorkeyword.toString())
        }
        binding.mainFilterKeywordLadykillerBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordShooterBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordCleanBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordTansanBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordLayeredBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordMartiniBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordPrettyBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordHighballBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordSweetBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordDockhanBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordSangqueBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordFluitfavorBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordSsupssupBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordOntherrockBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordSangkumBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordDansunBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordMilkBt.setOnCheckedChangeListener(favorListner)
        binding.mainFilterKeywordBockjapBt.setOnCheckedChangeListener(favorListner)

    }

    private fun SetGijuKeyword() {
        var gijulistner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.main_filter_giju_vodca_bt -> {
                        gijulist.add("보드카")
                    }
                    R.id.main_filter_giju_wiski_bt -> {
                        gijulist.add("위스키")
                    }
                    R.id.main_filter_giju_rum_bt -> {
                        gijulist.add("럼")
                    }
                    R.id.main_filter_giju_jin_bt -> {
                        gijulist.add("진")
                    }
                    R.id.main_filter_giju_tequila_bt -> {
                        gijulist.add("데킬라")
                    }
                    R.id.main_filter_giju_liqueur_bt -> {
                        gijulist.add("리큐르")
                    }
                    R.id.main_filter_giju_brandy_bt -> {
                        gijulist.add("브랜디")
                    }
                    R.id.main_filter_giju_anything_bt -> {
                        gijulist.add("상관없음")
                    }
                }
            } else {
                when (buttonView.id) {
                    R.id.main_filter_giju_vodca_bt -> {
                        gijulist.remove("보드카")
                    }
                    R.id.main_filter_giju_wiski_bt -> {
                        gijulist.remove("위스키")
                    }
                    R.id.main_filter_giju_rum_bt -> {
                        gijulist.remove("럼")
                    }
                    R.id.main_filter_giju_jin_bt -> {
                        gijulist.remove("진")
                    }
                    R.id.main_filter_giju_tequila_bt -> {
                        gijulist.remove("데킬라")
                    }
                    R.id.main_filter_giju_liqueur_bt -> {
                        gijulist.remove("리큐르")
                    }
                    R.id.main_filter_giju_brandy_bt -> {
                        gijulist.remove("브랜디")
                    }
                    R.id.main_filter_giju_anything_bt -> {
                        gijulist.remove("상관없음")
                    }
                }
            }
            Log.d("Filtertest", gijulist.toString())
        }
        binding.mainFilterGijuVodcaBt.setOnCheckedChangeListener(gijulistner)
        binding.mainFilterGijuWiskiBt.setOnCheckedChangeListener(gijulistner)
        binding.mainFilterGijuRumBt.setOnCheckedChangeListener(gijulistner)
        binding.mainFilterGijuJinBt.setOnCheckedChangeListener(gijulistner)
        binding.mainFilterGijuTequilaBt.setOnCheckedChangeListener(gijulistner)
        binding.mainFilterGijuLiqueurBt.setOnCheckedChangeListener(gijulistner)
        binding.mainFilterGijuBrandyBt.setOnCheckedChangeListener(gijulistner)
        binding.mainFilterGijuAnythingBt.setOnCheckedChangeListener(gijulistner)
    }

    fun ShowFilter(isshow : Boolean){
        if(isshow){
            var animation2 : Animation = AlphaAnimation(0f,1f);
            animation2.setDuration(500)
            binding.mainFilterBackgroundcoverIv.animation = animation2

            binding.mainBottomNavigation.visibility = View.INVISIBLE
            var animation : Animation = TranslateAnimation(0f,0f,500f, 0f);
            animation.setDuration(300)
            binding.mainFilterBackLayout.animation = animation
            binding.mainFilterBackLayout.visibility = View.VISIBLE
            binding.mainFilterBackgroundcoverIv.visibility = View.VISIBLE
        }
        else{
            var animation2 : Animation = AlphaAnimation(1f,0.2f);
            animation2.setDuration(300)
            binding.mainFilterBackgroundcoverIv.animation = animation2

            var animation : Animation = AlphaAnimation(0f,1f);
            animation.setDuration(500)
            binding.mainBottomNavigation.animation = animation
            binding.mainFilterBackLayout.animation = animation
            binding.mainBottomNavigation.visibility = View.VISIBLE
            animation  = TranslateAnimation(0f,0f,0f, 1500f);
            animation.setDuration(500)
            binding.mainFilterBackLayout.animation = animation
            binding.mainFilterBackLayout.visibility = View.GONE
            binding.mainFilterBackgroundcoverIv.visibility = View.GONE

        }
    }



    override fun onDestroy() {
        super.onDestroy()
        SetCurrentpageMain()
    }

    private fun SetCurrentpageMain() {
        var spf = getSharedPreferences("currenttab", MODE_PRIVATE)
        var editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab", 1)
        editor.commit()
    }
}


