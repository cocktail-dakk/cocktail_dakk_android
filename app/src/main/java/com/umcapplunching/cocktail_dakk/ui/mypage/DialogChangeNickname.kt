package com.umcapplunching.cocktail_dakk.ui.mypage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.DialogChangeNicknameBinding
import com.umcapplunching.cocktail_dakk.data.entities.UserInfo

class DialogChangeNickname(context: Context,val setUser : (UserInfo) -> Unit) : Dialog(context) {

    private lateinit var binding: DialogChangeNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogChangeNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initListener()
        showKeyboard()
    }

    private fun showKeyboard() {
        val view: EditText = binding.mypageRenameEditEt
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.requestFocus()
//        inputMethodManager.showSoftInput(view, 0)
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private fun initListener() {

        binding.mypageRenameWhiteboardLa.setOnClickListener{
            // 아무것도 안함. 배경 클릭과의 대비를 두기 위한 코드
        }

        binding.mypageRenameBackgroundLa.setOnClickListener{
            dismiss()
        }

        binding.mypageRenameExitIv.setOnClickListener{
            dismiss()
        }

        binding.mypageRenameOkOnTv.setOnClickListener {
            if (binding.mypageRenameEditEt.text.toString().replace(" ", "") == "") {
                binding.mypageRenameNickcheckTv.visibility = View.VISIBLE
                val animjindong: Animation = AnimationUtils
                    .loadAnimation(context, R.anim.jindong)
                binding.mypageRenameNickcheckTv.startAnimation(animjindong)
            } else {
                val userInfo = UserInfo(
                    CocktailDakkApplication.userInfo.age,
                    CocktailDakkApplication.userInfo.alcoholLevel,
                    binding.mypageRenameEditEt.text.toString(),
                    CocktailDakkApplication.userInfo.sex,
                    CocktailDakkApplication.userInfo.userDrinks,
                    CocktailDakkApplication.userInfo.userKeywords
                )
                CocktailDakkApplication.userInfo = userInfo
                setUser(userInfo)
                dismiss()
            }
        }

    }

    private fun initViews() = with(binding) {
        // 뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라지지 않도록
        // setCancelable(false)

        // background를 투명하게 만듦
        // (중요) Dialog는 내부적으로 뒤에 흰 사각형 배경이 존재하므로, 배경을 투명하게 만들지 않으면
        // corner radius의 적용이 보이지 않는다.
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

}