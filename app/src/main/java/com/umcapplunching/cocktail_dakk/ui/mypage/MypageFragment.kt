package com.umcapplunching.cocktail_dakk.ui.mypage

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.data.entities.UserInfo_forApp
import com.umcapplunching.cocktail_dakk.databinding.FragmentMypageBinding
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.mypage.mypageService.MypageBody
import com.umcapplunching.cocktail_dakk.ui.mypage.mypageService.MypageRequest
import com.umcapplunching.cocktail_dakk.ui.mypage.mypageService.MypageService
import com.umcapplunching.cocktail_dakk.ui.mypage.mypageService.MypageView
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication
import com.umcapplunching.cocktail_dakk.ui.BaseFragmentByDataBinding
import com.umcapplunching.cocktail_dakk.ui.settings.SettingActivity

class MypageFragment : BaseFragmentByDataBinding<FragmentMypageBinding>(R.layout.fragment_mypage), MypageView {
//    private lateinit var userInfo: UserInfo
    var mypageService = MypageService()
    private lateinit var callback: OnBackPressedCallback

    override fun initView() {
        initUser(CocktailDakkApplication.userInfoForApp)
        mypageService.setmypageView(this)
        initClicker()

        binding.mypageSettingIv.setOnClickListener {
//            (activity as MainActivity).changesettingtab()
            startActivity(Intent(requireContext(),SettingActivity::class.java))
        }
    }

    private fun initClicker() {
        // 닉네임 변경 다이어로그
        binding.mypageNicknameResetLa.setOnClickListener{
            val myDialog = DialogChangeNickname(requireContext(), setUser = {
                binding.mypageNicknameTv.text = it.nickname
                UserInfoChangeToServer()
            })
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(myDialog.window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            myDialog.show()
            myDialog.window!!.attributes = lp
        }

        val userInfochangeListener = (View.OnClickListener {
            val fragment = this
            val myDialog = DialogChangeUserInfo(requireContext(), setUser = {
                UserInfoChangeToServer()
                // 데이터 변경 된 것을 마이페이지에도 새로고침
                binding.mypageLevelContextTv.text =
                    "${it.alcoholLevel} 도"
                val gijufa = binding.mypageGijuContextFa
                val gijulist = arrayListOf<String>()
                gijulist.addAll(it.userDrinks.split(',') as ArrayList<String>)
                gijulist.remove("")
                gijufa.removeAllViews()
                for (i in 0 until gijulist.size) {
                    gijufa.addView(createKeyword(gijulist[i], 15.0f, "000000", 70))
                    val vu = View(this.activity)
                    val layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity, 10), 0)
                    layoutparam.setMargins(0, 100, 0, 0)
                    vu.layoutParams = layoutparam
                    gijufa.addView(vu)
                }

                val l1 = binding.mypageKeywordContextFa
                val keywords = arrayListOf<String>()
                keywords.addAll(it.userKeywords.split(',') as ArrayList<String>)
                keywords.remove("")
                l1.removeAllViews()
                for (i in 0 until keywords.size) {
                    l1.addView(createKeyword(keywords[i], 15.0f, "000000", 70))
                    val vu = View(this.activity)
                    val layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity, 10), 0)
                    layoutparam.setMargins(0, 100, 0, 0)
                    vu.layoutParams = layoutparam
                    l1.addView(vu)
                }
                (activity as MainActivity).reStartActivity()
//                val intent =Intent(activity,MainActivity::class.java)
//                intent.flags = FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
//                startActivity(intent)
//                val intent: Intent = requireActivity().packageManager.getLaunchIntentForPackage(requireActivity().packageName)!!
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                startActivity(intent)
            },fragment)
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(myDialog.window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            myDialog.show()
            myDialog.window!!.attributes = lp
        })

        binding.mypageLevelResetIv.setOnClickListener(userInfochangeListener)
        binding.mypageLevelResetTv.setOnClickListener(userInfochangeListener)
        binding.mypageBaseResetIv.setOnClickListener(userInfochangeListener)
        binding.mypageBaseResetTv.setOnClickListener(userInfochangeListener)
        binding.mypageKeywordResetIv.setOnClickListener(userInfochangeListener)
        binding.mypageKeywordResetTv.setOnClickListener(userInfochangeListener)

    }

    private fun initUser(userForApp: UserInfo_forApp) {
        binding.mypageNicknameTv.text = when (userForApp.nickname) {
            "" -> "이름 없음"
            else -> userForApp.nickname
        }

        binding.mypageLevelContextTv.text = when (userForApp.alcoholLevel) {
            0 -> "무알콜 선호자"
            else -> userForApp.alcoholLevel.toString() + "도"
        }

        // 프로필 이미지 설정
        Glide.with(this)
            .load(CocktailDakkApplication.userImgUrl)
            .into(binding.mypageProfileIv)

        val gijulist = userForApp.userDrinks.split(",") as ArrayList<String>
        for (i in 0 until gijulist.size) {
            gijulist[i] = gijulist[i].trim()
        }
        gijulist.remove("")

        val gijufa = binding.mypageGijuContextFa
        for (i in 0 until gijulist.size) {
            gijufa.addView(createKeyword(gijulist[i], 15.0f, "000000", 70))
            val vu = View(this.activity)
            val layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity, 10), 0)
            layoutparam.setMargins(0, 100, 0, 0)
            vu.layoutParams = layoutparam
            gijufa.addView(vu)
        }

        val keywords = userForApp.userKeywords.split(",") as ArrayList<String>
        for (i in 0 until keywords.size) {
            keywords[i] = keywords[i].trim()
        }

        keywords.remove("")
        val l1 = binding.mypageKeywordContextFa
        for (i in 0 until keywords.size) {
            l1.addView(createKeyword(keywords[i], 15.0f, "000000", 70))
            val vu = View(this.activity)
            val layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity, 10), 0)
            layoutparam.setMargins(0, 100, 0, 0)
            vu.layoutParams = layoutparam
            l1.addView(vu)
        }

    }



    private fun UserInfoChangeToServer() {
        mypageService.mypagemodify(
            MypageRequest(
                CocktailDakkApplication.userInfoForApp.nickname,
                CocktailDakkApplication.userInfoForApp.alcoholLevel,
                CocktailDakkApplication.userInfoForApp.userKeywords,
                CocktailDakkApplication.userInfoForApp.userDrinks
            )
        )
    }


    override fun onMypageSuccess(mypagebody: MypageBody) {
        Toast.makeText(context,"설정 변경이 완료되었습니다.",Toast.LENGTH_SHORT).show()
    }

    override fun onMypageFailure(code: Int, message: String) {
        if (code==5000){
            (activity as MainActivity).TokenrefreshInMain()
            Toast.makeText(requireContext(),"재시도 해주세요.",Toast.LENGTH_SHORT).show()
        }
    }

    private fun createKeyword(inputText: String, size: Float, color: String, width: Int): TextView {
        val textView = TextView(this.activity)
        textView.text = inputText
        textView.textSize = size
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setBackgroundResource(R.drawable.round_rect_white_in_sky)
        textView.setTextColor(Color.parseColor("#$color"))
        textView.setPadding(DPtoPX(this.activity, 10), DPtoPX(this.activity, 4), DPtoPX(this.activity, 10), DPtoPX(this.activity, 4))
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.layoutParams = lp
        return textView
    }

    private fun DPtoPX(context: FragmentActivity?, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context?.resources?.displayMetrics
        ).toInt()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }


}