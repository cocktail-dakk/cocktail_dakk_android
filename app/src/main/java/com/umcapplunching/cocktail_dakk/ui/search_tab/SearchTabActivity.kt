package com.umcapplunching.cocktail_dakk.ui.search_tab

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.data.entities.cocktaildata_db.Cocktail_recentSearch
import com.umcapplunching.cocktail_dakk.databinding.ActivitySearchTabBinding
import com.umcapplunching.cocktail_dakk.ui.BaseActivity
import com.umcapplunching.cocktail_dakk.ui.BaseActivityByDataBinding
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.menu_detail.DetailFragment
import com.umcapplunching.cocktail_dakk.ui.search.SearchCocktailViewModel
import com.umcapplunching.cocktail_dakk.ui.search_tab.adapter.RecentSearchKeywordRvAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchTabActivity : BaseActivityByDataBinding<ActivitySearchTabBinding>(R.layout.activity_search_tab) {
    var backflag = false
    private lateinit var searchText : String
    private lateinit var searchTabViewModel: SearchTabViewModel

    override fun initViewModel() {
        binding.lifecycleOwner=this
        searchTabViewModel = ViewModelProvider(this).get(SearchTabViewModel::class.java)
    }

    override fun initView() {
        overridePendingTransition(R.anim.alpha_out, R.anim.none)

        // 이미 검색한 검색어가 있다면 처리
        if(intent.hasExtra("searchStr")){
            binding.searchTabEditTv.setText(intent.getStringExtra("searchStr"))
            binding.searchTabEditTv.setSelection(intent.getStringExtra("searchStr")!!.length)
            searchText = intent.getStringExtra("searchStr")!!
        }else{
            searchText = ""
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.search_tab_frame_la, SearchTabBaseFragment())
            .commitAllowingStateLoss()
        EventListener()
    }

    //칵테일 디테일
    fun detailcocktailInSearchtab(id: Int) {
        backflag = true
        binding.navDetailFragmentContainer.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().replace(
            R.id.nav_detail_fragment_container,
            DetailFragment().apply {
                Bundle().apply {
                    putString("CocktailId",id.toString())
                    putString("DetailMethod","SearchTab")
                }.also { arguments = it }
            }
        ).commit()

        val view: EditText = binding.searchTabEditTv
        val manager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun DetailBackArrowInSearchtab(){
        backflag = false
        binding.navDetailFragmentContainer.visibility = View.GONE
    }

    private fun EventListener() {
        binding.searchTabEditTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.searchTabEditTv.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if(searchText.replace(" ","")!=""){
                    searchTabViewModel.recentSearchDuplicateCheck(Cocktail_recentSearch(searchText.trim()))
                    searchTabViewModel.recentSearchinsert(Cocktail_recentSearch(searchText.trim()))
                }
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("searchStr",searchText.replace(" ",""))
                startActivity(intent)
                handled = true
            }
            handled
        }

        //나갈때 애니메이션 및 키보드 없애기
        binding.searchTabBackIv.setOnClickListener {
            Exit()
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        if (backflag) {
            DetailBackArrowInSearchtab()
        }
        else{
            Exit()
        }
    }

    //나갈때 코드 finish
    private fun Exit() {
        val animTransRight: Animation = AnimationUtils
            .loadAnimation(this, R.anim.horizon_in)
        animTransRight.duration = 700
        binding.searchTabSearchbarIv.startAnimation(animTransRight)

        //나가기전에 키보드 없애기
        val view: EditText = binding.searchTabEditTv
        val manager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
        overridePendingTransition(R.anim.none, R.anim.alpha_in)
        finish()
    }

    @Override
    override fun onPause() {
        super.onPause()
        // 액티비티 끝날 때 애니메이션 없애기
        overridePendingTransition(0,0)
    }

    override fun onStart() {
        super.onStart()
        // 키보드 올리기
        val view : EditText = binding.searchTabEditTv
        view.postDelayed({
            val manager : InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            view.requestFocus()
            manager.showSoftInput(view,0)
        },100)
    }
}


