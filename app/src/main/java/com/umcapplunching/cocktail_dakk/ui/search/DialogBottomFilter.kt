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
        binding.mainFilterDosunumTv.text = mindosu.toString() + "??? ~ " + maxdosu.toString() + "???"
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
        // ?????? ????????? ??????????????? ???????????? ???
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
        // ????????? favor ????????? ???????????? ??????????????? ??????
        val favorListner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.main_filter_keyword_ladykiller_bt -> {
                        favorkeyword.add("???????????????")
                    }
                    R.id.main_filter_keyword_shooter_bt -> {
                        favorkeyword.add("??????")
                    }
                    R.id.main_filter_keyword_clean_bt -> {
                        favorkeyword.add("?????????")
                    }
                    R.id.main_filter_keyword_tansan_bt -> {
                        favorkeyword.add("??????")
                    }
                    R.id.main_filter_keyword_layered_bt -> {
                        favorkeyword.add("????????????")
                    }
                    R.id.main_filter_keyword_martini_bt -> {
                        favorkeyword.add("?????????")
                    }
                    R.id.main_filter_keyword_pretty_bt -> {
                        favorkeyword.add("??????")
                    }
                    R.id.main_filter_keyword_highball_bt -> {
                        favorkeyword.add("?????????")
                    }
                    R.id.main_filter_keyword_sweet_bt -> {
                        favorkeyword.add("?????????")
                    }
                    R.id.main_filter_keyword_dockhan_bt -> {
                        favorkeyword.add("??????")
                    }
                    R.id.main_filter_keyword_sangque_bt -> {
                        favorkeyword.add("?????????")
                    }
                    R.id.main_filter_keyword_fluitfavor_bt -> {
                        favorkeyword.add("?????????")
                    }
                    R.id.main_filter_keyword_ssupssup_bt -> {
                        favorkeyword.add("?????????")
                    }
                    R.id.main_filter_keyword_ontherrock_bt -> {
                        favorkeyword.add("?????????")
                    }
                    R.id.main_filter_keyword_sangkum_bt -> {
                        favorkeyword.add("?????????")
                    }
                    R.id.main_filter_keyword_dansun_bt -> {
                        favorkeyword.add("?????????")
                    }
                    R.id.main_filter_keyword_milk_bt -> {
                        favorkeyword.add("??????")
                    }
                    R.id.main_filter_keyword_bockjap_bt -> {
                        favorkeyword.add("?????????")
                    }
                }
            } else {
                when (buttonView.id) {
                    R.id.main_filter_keyword_ladykiller_bt -> {
                        favorkeyword.remove("???????????????")
                    }
                    R.id.main_filter_keyword_shooter_bt -> {
                        favorkeyword.remove("??????")
                    }
                    R.id.main_filter_keyword_clean_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.main_filter_keyword_tansan_bt -> {
                        favorkeyword.remove("??????")
                    }
                    R.id.main_filter_keyword_layered_bt -> {
                        favorkeyword.remove("????????????")
                    }
                    R.id.main_filter_keyword_martini_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.main_filter_keyword_pretty_bt -> {
                        favorkeyword.remove("??????")
                    }
                    R.id.main_filter_keyword_highball_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.main_filter_keyword_sweet_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.main_filter_keyword_dockhan_bt -> {
                        favorkeyword.remove("??????")
                    }
                    R.id.main_filter_keyword_sangque_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.main_filter_keyword_fluitfavor_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.main_filter_keyword_ssupssup_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.main_filter_keyword_ontherrock_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.main_filter_keyword_sangkum_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.main_filter_keyword_dansun_bt -> {
                        favorkeyword.remove("?????????")
                    }
                    R.id.main_filter_keyword_milk_bt -> {
                        favorkeyword.remove("??????")
                    }
                    R.id.main_filter_keyword_bockjap_bt -> {
                        favorkeyword.remove("?????????")
                    }
                }
            }
        }

        for(element in favorTemp){
            element.setOnCheckedChangeListener(favorListner)
        }
    }

    private fun SetGijuKeyword() {
        // ????????? ?????? ????????? ???????????? ??????????????? ??????
        val gijulistner = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.main_filter_giju_vodca_bt -> {
                        gijulist.add("?????????")
                    }
                    R.id.main_filter_giju_wiski_bt -> {
                        gijulist.add("?????????")
                    }
                    R.id.main_filter_giju_rum_bt -> {
                        gijulist.add("???")
                    }
                    R.id.main_filter_giju_jin_bt -> {
                        gijulist.add("???")
                    }
                    R.id.main_filter_giju_tequila_bt -> {
                        gijulist.add("?????????")
                    }
                    R.id.main_filter_giju_liqueur_bt -> {
                        gijulist.add("?????????")
                    }
                    R.id.main_filter_giju_brandy_bt -> {
                        gijulist.add("?????????")
                    }
                }
            } else {
                when (buttonView.id) {
                    R.id.main_filter_giju_vodca_bt -> {
                        gijulist.remove("?????????")
                    }
                    R.id.main_filter_giju_wiski_bt -> {
                        gijulist.remove("?????????")
                    }
                    R.id.main_filter_giju_rum_bt -> {
                        gijulist.remove("???")
                    }
                    R.id.main_filter_giju_jin_bt -> {
                        gijulist.remove("???")
                    }
                    R.id.main_filter_giju_tequila_bt -> {
                        gijulist.remove("?????????")
                    }
                    R.id.main_filter_giju_liqueur_bt -> {
                        gijulist.remove("?????????")
                    }
                    R.id.main_filter_giju_brandy_bt -> {
                        gijulist.remove("?????????")
                    }
                }
            }
        }
        for(element in gijuTemp){
            element.setOnCheckedChangeListener(gijulistner)
        }
    }

    private fun KeywordReset() {
        // ??????
        binding.mainFilterDosuSeekbar.minProgress = 10
        binding.mainFilterDosuSeekbar.maxProgress = 30

        // ??????
        for(element in gijuTemp){
            element.isChecked = false
        }

        // ???????????????
        for(element in favorTemp){
            element.isChecked = false
        }
    }

}