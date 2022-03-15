package com.umcapplunching.cocktail_dakk.ui.search_tab

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.ActivitySearchTabBinding
import com.umcapplunching.cocktail_dakk.ui.BaseActivity

class SearchTabActivity : BaseActivity<ActivitySearchTabBinding>(ActivitySearchTabBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //들어올 때 애니메이션
        overridePendingTransition(R.anim.alpha_out, R.anim.none)
    }

    override fun initAfterBinding() {
        //검색어 초기화
        val spf =  getSharedPreferences("searchstr", MODE_PRIVATE)
        binding.searchTabEditTv.setText(spf.getString("searchstr","")!!.trim())
        binding.searchTabEditTv.setSelection(binding.searchTabEditTv.text.length)
        supportFragmentManager.beginTransaction()
            .replace(R.id.search_tab_frame_la, SearchTabBaseFragment())
            .commitAllowingStateLoss()
        EventListener()
    }

    private fun EventListener() {
        binding.searchTabEditTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //엘라스틱서치부분
    //                if (count == 0) {
    //                    supportFragmentManager.beginTransaction()
    //                        .replace(R.id.search_tab_frame_la, SearchTabBaseFragment())
    //                        .commitAllowingStateLoss()
    //                }
    //                else {
    //                    supportFragmentManager.beginTransaction()
    //                        .replace(R.id.search_tab_frame_la, SearchTabTempResultFragment())
    //                        .commitAllowingStateLoss()
    //                }

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //엘라스틱서치부분
    //                if (count == 0) {
    //                    supportFragmentManager.beginTransaction()
    //                        .replace(R.id.search_tab_frame_la, SearchTabBaseFragment())
    //                        .commitAllowingStateLoss()
    //                }
    //                else {
    //                    supportFragmentManager.beginTransaction()
    //                        .replace(R.id.search_tab_frame_la, SearchTabTempResultFragment())
    //                        .commitAllowingStateLoss()
    //                }
                val spf = getSharedPreferences("searchstr", MODE_PRIVATE)
                val editor: SharedPreferences.Editor = spf?.edit()!!
                editor.putString("searchstr",s.toString())
                editor.apply()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.searchTabEditTv.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                TomoveSearchTab()
                handled = true
            }
            handled
        }

        binding.searchTabBackIv.setOnClickListener {
            //나갈때 애니메이션 및 키보드 없애기
            Exit()
        }
    }

    fun TomoveSearchTab() {
        val spf = getSharedPreferences("currenttab", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab", 0)
        editor.apply()
        Exit()
    }

    //나갈때 코드 finish
    private fun Exit() {
        val animTransRight: Animation = AnimationUtils
            .loadAnimation(this, R.anim.horizon_in)
        animTransRight.duration = 700
        binding.searchTabSearchbarIv.startAnimation(animTransRight)

        //나가기전에 키보드 없애야지 오류 X
        val view: EditText = binding.searchTabEditTv
        val manager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
        finish()
        overridePendingTransition(R.anim.none, R.anim.alpha_in)
    }

    override fun onStart() {
        super.onStart()
        val view : EditText = binding.searchTabEditTv
        view.postDelayed({
            val manager : InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            view.requestFocus()
            manager.showSoftInput(view,0)
        },100)
    }

}


