package com.example.cocktail_dakk.ui.search_tab

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.IBinder
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.ActivitySearchTabBinding
import com.example.cocktail_dakk.ui.BaseActivity
import com.example.cocktail_dakk.ui.main.MainActivity
import com.example.cocktail_dakk.ui.start.StartNameActivity
import kotlinx.android.synthetic.main.fragment_mypage.*

class SearchTabActivity : BaseActivity<ActivitySearchTabBinding>(ActivitySearchTabBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //들어올 떄 애니메이션
        overridePendingTransition(R.anim.alpha_out, R.anim.none)
    }

    override fun initAfterBinding() {
        //검색어 초기화
        var spf =  getSharedPreferences("searchstr", AppCompatActivity.MODE_PRIVATE)
        binding.searchTabEditTv.setText(spf.getString("searchstr",""))
        binding.searchTabEditTv.setSelection(binding.searchTabEditTv.getText().length);

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
                var spf = getSharedPreferences("searchstr", MODE_PRIVATE)
                var editor: SharedPreferences.Editor = spf?.edit()!!
                editor.putString("searchstr", s.toString())
                editor.apply()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.searchTabEditTv.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                var spf = getSharedPreferences("currenttab", MODE_PRIVATE)
                var editor: SharedPreferences.Editor = spf?.edit()!!
                editor.putInt("currenttab", 0)
                editor.commit()
                Exit()
                handled = true
            }
            handled
        }

        binding.searchTabBackIv.setOnClickListener {
            //나갈때 애니메이션 및 키보드 없애기
            Exit()
        }
    }

    //나갈때 코드 finish
    private fun Exit() {
        var animTransRight: Animation = AnimationUtils
            .loadAnimation(this, R.anim.horizon_in)
        animTransRight.duration = 700
        binding.searchTabSearchbarIv.startAnimation(animTransRight)

        //나가기전에 키보드 없애야지 오류 X
        val view: EditText = binding.searchTabEditTv
        var manager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
        finish()
        overridePendingTransition(R.anim.none, R.anim.alpha_in)
    }

    override fun onStart() {
        super.onStart()
        val view : EditText = binding.searchTabEditTv
        view.postDelayed(object : Runnable{
            override fun run() {
                var manager : InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                view.requestFocus()
                manager.showSoftInput(view,0)
            }
        },100)
    }

}


