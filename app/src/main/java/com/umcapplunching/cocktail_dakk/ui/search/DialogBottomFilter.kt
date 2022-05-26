package com.umcapplunching.cocktail_dakk.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.DialogBottomFilterBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragmentByDataBinding
import hearsilent.discreteslider.DiscreteSlider

class DialogBottomFilter : BottomSheetDialogFragment(){
    lateinit var binding : DialogBottomFilterBinding
    private lateinit var searchCocktailViewModel : SearchCocktailViewModel
    private var gijulist = ArrayList<String>()
    private var favorkeyword = ArrayList<String>()

    private lateinit var gijuTemp : ArrayList<CheckBox>
    private lateinit var favorTemp : ArrayList<CheckBox>
    private var dosumin: Int = 10
    private var dosumax: Int = 30

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_bottom_filter,container, false)
        binding.lifecycleOwner=this
        searchCocktailViewModel = ViewModelProvider(requireActivity()).get(SearchCocktailViewModel::class.java)

        InitKeyword()
        SetFavorKeyword()
        SetGijuKeyword()
        SetDosuListener()

        return binding.root
    }

    private fun SetDosuListener() {
        binding.mainFilterDosuSeekbar.setValueChangedImmediately(true)
        binding.mainFilterDosuSeekbar.setOnValueChangedListener(object : DiscreteSlider.OnValueChangedListener() {
            override fun onValueChanged(minProgress: Int, maxProgress: Int, fromUser: Boolean) {
                super.onValueChanged(minProgress, maxProgress, fromUser)
                changedosutv(minProgress, maxProgress)
                dosumin = minProgress
                dosumax = maxProgress
            }
        })
    }

    private fun changedosutv(mindosu: Int, maxdosu: Int) {
        binding.mainFilterDosunumTv.text = mindosu.toString() + "도 ~ " + maxdosu.toString() + "도"
    }

    override fun onResume() {
        super.onResume()
        dosumin = 10
        dosumax = 30
        KeywordReset()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.mainFilterAdjustBt.setOnClickListener {
            searchCocktailViewModel.updateKeyword(Triple(gijulist,favorkeyword,Pair(dosumin,dosumax)))
            searchCocktailViewModel.updatecurrentPage(0)
            searchCocktailViewModel.updateSearchMode(SearchCocktailViewModel.SearchMode.FILTER)
            dismiss()
        }
    }

    private fun InitKeyword() {
        gijulist.clear()
        favorkeyword.clear()
        gijuTemp = arrayListOf(
            binding.mainFilterGijuVodcaBt,
            binding.mainFilterGijuRumBt,
            binding.mainFilterGijuTequilaBt,
            binding.mainFilterGijuWiskiBt,
            binding.mainFilterGijuBrandyBt,
            binding.mainFilterGijuLiqueurBt,
            binding.mainFilterGijuJinBt
        )
        // 취향 리스트 여기에서만 추가하면 됨
        favorTemp = arrayListOf(
            binding.mainFilterKeywordLadykillerBt,
            binding.mainFilterKeywordShooterBt,
            binding.mainFilterKeywordCleanBt,
            binding.mainFilterKeywordTansanBt,
            binding.mainFilterKeywordLayeredBt,
            binding.mainFilterKeywordMartiniBt,
            binding.mainFilterKeywordPrettyBt,
            binding.mainFilterKeywordHighballBt,
            binding.mainFilterKeywordSweetBt,
            binding.mainFilterKeywordDockhanBt,
            binding.mainFilterKeywordSangqueBt,
            binding.mainFilterKeywordFluitfavorBt,
            binding.mainFilterKeywordSsupssupBt,
            binding.mainFilterKeywordOntherrockBt,
            binding.mainFilterKeywordSangkumBt,
            binding.mainFilterKeywordDansunBt,
            binding.mainFilterKeywordMilkBt,
            binding.mainFilterKeywordBockjapBt
        )
    }

    private fun SetFavorKeyword() {
        // 새로운 favor 키워드 추가하면 여기에다가 추가
        val favorListner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
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
                        favorkeyword.add("상쾌한")
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
                        favorkeyword.remove("상쾌한")
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
        }

        for(element in favorTemp){
            element.setOnCheckedChangeListener(favorListner)
        }
    }

    private fun SetGijuKeyword() {
        // 새로운 기주 키워드 추가하면 여기에다가 추가
        val gijulistner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
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
                }
            }
        }
        for(element in gijuTemp){
            element.setOnCheckedChangeListener(gijulistner)
        }
    }

    private fun KeywordReset() {

        // 도수
        binding.mainFilterDosuSeekbar.minProgress = 10
        binding.mainFilterDosuSeekbar.maxProgress = 30

        // 기주
        for(element in gijuTemp){
            element.isChecked = false
        }

        // 취향키워드
        for(element in favorTemp){
            element.isChecked = false
        }
    }

}