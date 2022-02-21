package com.example.cocktail_dakk.ui.start.setting

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import com.example.cocktail_dakk.R
import com.example.cocktail_dakk.databinding.ActivityStartNameBinding
import com.example.cocktail_dakk.ui.BaseActivity
import android.view.animation.LinearInterpolator

import android.animation.ValueAnimator

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Insets.add
import androidx.core.graphics.Insets.add
import androidx.core.view.OneShotPreDrawListener.add
import io.reactivex.internal.util.BackpressureHelper.add
import com.race604.drawable.wave.WaveDrawable

import android.graphics.drawable.Drawable





class StartNameActivity : BaseActivity<ActivityStartNameBinding>(ActivityStartNameBinding::inflate) {
    @SuppressLint("ObjectAnimatorBinding")
    override fun initAfterBinding() {
        binding.startNameNickcheckTv.visibility = View.GONE
        SetEventListener()

    }

    private fun SetEventListener() {
        binding.startNameStartBtnTv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (binding.startNameNameEt.text.toString().replace(" ", "").equals("")) {
                    binding.startNameNickcheckTv.visibility = View.VISIBLE
                    var animjindong: Animation = AnimationUtils
                        .loadAnimation(this@StartNameActivity, R.anim.jindong)
                    binding.startNameNickcheckTv.startAnimation(animjindong)
                }
                else{
                    GotoSettingActivity()
                }
            }
        })

        binding.startNameNameEt.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.startNameNameEt.text.toString().replace(" ", "").equals("")) {
                    binding.startNameNickcheckTv.visibility = View.VISIBLE
                    var animjindong: Animation = AnimationUtils
                        .loadAnimation(this@StartNameActivity, R.anim.jindong)
                    binding.startNameNickcheckTv.startAnimation(animjindong)
                }
                else{
                    GotoSettingActivity()
                }
                handled = true
            }
            handled
        }
    }

    private fun GotoSettingActivity() {
        var intent = Intent(this@StartNameActivity, StartSettingActivity::class.java)
        intent.putExtra("nickname", binding.startNameNameEt.text.toString())
        startActivity(intent)
    }

}