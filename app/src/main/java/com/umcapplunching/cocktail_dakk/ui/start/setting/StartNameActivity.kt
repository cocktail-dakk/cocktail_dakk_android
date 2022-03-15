package com.umcapplunching.cocktail_dakk.ui.start.setting

import android.content.Intent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.ActivityStartNameBinding
import com.umcapplunching.cocktail_dakk.ui.BaseActivity

class StartNameActivity : BaseActivity<ActivityStartNameBinding>(ActivityStartNameBinding::inflate) {

    override fun initAfterBinding() {
        binding.startNameNickcheckTv.visibility = View.GONE
        setEventListener()
    }

    private fun setEventListener() {
        binding.startNameStartBtnTv.setOnClickListener {
            if (binding.startNameNameEt.text.toString().replace(" ", "").equals("")) {
                binding.startNameNickcheckTv.visibility = View.VISIBLE
                val animjindong: Animation = AnimationUtils
                    .loadAnimation(this@StartNameActivity, R.anim.jindong)
                binding.startNameNickcheckTv.startAnimation(animjindong)
            } else {
                gotoSettingActivity()
            }
        }

        binding.startNameNameEt.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.startNameNameEt.text.toString().replace(" ", "") == "") {
                    binding.startNameNickcheckTv.visibility = View.VISIBLE
                    val animjindong: Animation = AnimationUtils
                        .loadAnimation(this@StartNameActivity, R.anim.jindong)
                    binding.startNameNickcheckTv.startAnimation(animjindong)
                }
                else{
                    gotoSettingActivity()
                }
                handled = true
            }
            handled
        }
    }

    private fun gotoSettingActivity() {
        val intent = Intent(this@StartNameActivity, StartSettingActivity::class.java)
        intent.putExtra("nickname", binding.startNameNameEt.text.toString())
        startActivity(intent)
    }

}