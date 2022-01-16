package com.example.cocktail_dakk.ui.search_tab

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.ActivitySearchTabBinding
import com.example.cocktail_dakk.ui.BaseActivity

class SearchTabActivity : BaseActivity<ActivitySearchTabBinding>(ActivitySearchTabBinding::inflate) {

    private var imm: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //들어올 떄 애니메이션
        overridePendingTransition(R.anim.alpha_out, R.anim.none)
    }


    override fun initAfterBinding() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.search_tab_frame_la, SearchTabBaseFragment())
            .commitAllowingStateLoss()

        binding.searchTabEditTv.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (count == 0) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.search_tab_frame_la, SearchTabBaseFragment())
                        .commitAllowingStateLoss()
                }
                else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.search_tab_frame_la, SearchTabTempResultFragment())
                        .commitAllowingStateLoss()
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.search_tab_frame_la, SearchTabBaseFragment())
                        .commitAllowingStateLoss()
                }
                else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.search_tab_frame_la, SearchTabTempResultFragment())
                        .commitAllowingStateLoss()
                }


            }

            override fun afterTextChanged(s: Editable?) {

            }

        })


        binding.searchTabBackIv.setOnClickListener {
            //나갈때 애니메이션
            var animTransRight: Animation = AnimationUtils
                .loadAnimation(this, R.anim.horizon_in);
            binding.searchTabSearchbarIv.startAnimation(animTransRight)
            finish()
            overridePendingTransition(R.anim.none, R.anim.alpha_in)

        }
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


