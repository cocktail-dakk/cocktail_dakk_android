package com.umcapplunching.cocktail_dakk.ui.mypage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.data.entities.User
import com.umcapplunching.cocktail_dakk.data.entities.UserInfo
import com.umcapplunching.cocktail_dakk.data.entities.getUser
import com.umcapplunching.cocktail_dakk.databinding.FragmentMypageBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.locker.SettingsActivity
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.main.adapter.MainViewpagerAdapter
import com.umcapplunching.cocktail_dakk.ui.main.adapter.MypageViewpagerAdapter
import com.umcapplunching.cocktail_dakk.ui.mypage.mypageService.MypageBody
import com.umcapplunching.cocktail_dakk.ui.mypage.mypageService.MypageRequest
import com.umcapplunching.cocktail_dakk.ui.mypage.mypageService.MypageService
import com.umcapplunching.cocktail_dakk.ui.mypage.mypageService.MypageView
import com.umcapplunching.cocktail_dakk.ui.start.Service.Autologinbody
import com.umcapplunching.cocktail_dakk.utils.getReposit
import com.umcapplunching.cocktail_dakk.utils.getaccesstoken
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.tbuonomo.viewpagerdotsindicator.setPaddingHorizontal

class MypageFragment : BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate), MypageView {

    //    private var user = User("셜록닉네임", "소주 20병", "",
//        "알록달록, 간단한,가벼운, 알록달록, 간단한,간단한,가벼운, 알록달록, 간단한,가벼운, 알록달록, 간단한," +
//                "알록달록, 간단한,가벼운, 알록달록, 간단한,간단한,가벼운, 알록달록, 간단한,가벼운, 알록달록, 간단한," +
//                "알록달록, 간단한,가벼운, 알록달록, 간단한,간단한,가벼운, 알록달록, 간단한,가벼운, 알록달록, 간단한")
    private lateinit var userInfo: UserInfo
    private val information = arrayListOf("    주량    ", "    기주    ", "  키워드  ")
    private lateinit var adapter: MypageViewpagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var animation2: Animation
    var mypageService = MypageService()

    private lateinit var callback: OnBackPressedCallback

    override fun initAfterBinding() {
        setCurrentPage()
        binding.mypageKeywordContextFa.removeAllViews()
        binding.mypageGijuContextFa.removeAllViews()

        //이게 어댑터 연결과 비슷한거
        mypageService.setmypageView(this)

        animation2 = AlphaAnimation(0f, 1f);
        animation2.duration = 300

        userInfo = getUser(requireContext())
        initUser(userInfo)

        adapter = MypageViewpagerAdapter(this)
        viewPager = binding.mypageResettingViewpagerVp
        viewPager.adapter = adapter

        TabLayoutMediator(
            binding.mypageResettingTablayoutTl,
            binding.mypageResettingViewpagerVp
        ) { tab, position ->
            tab.text = information[position]
        }.attach()

        initClicker()

        binding.mypageSettingIv.setOnClickListener {
            val intent = Intent(requireContext(),SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setCurrentPage() {
        var spf = activity?.getSharedPreferences("currenttab", AppCompatActivity.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putInt("currenttab", 3)
        editor.commit()
    }


    private fun initUser(user: UserInfo) {

        binding.mypageNicknameTv.text = when (user.nickname) {
            "" -> "이름 없음"
            else -> user.nickname
        }

        binding.mypageLevelContextTv.text = when (user.alcoholLevel) {
            0 -> "무알콜 선호자"
            else -> user.alcoholLevel.toString() + "도"
        }

//        if (userInfo.sex.equals("M")) {
//            binding.mypageProfileIv.setImageResource(R.drawable.mypage_profile)
//        } else {
//            binding.mypageProfileIv.setImageResource(R.drawable.img_mypage_girl)
//        }
        var spf = activity?.getSharedPreferences("profileimg", AppCompatActivity.MODE_PRIVATE)
        Glide.with(this)
            .load(spf!!.getString("profileimg"," "))
            .into(binding.mypageProfileIv)

        val gijulist = user.userDrinks.split(",") as ArrayList<String>
        for (i in 0 until gijulist.size) {
            gijulist[i] = gijulist[i].trim()
        }
        gijulist.remove("")
        val gijufa = binding.mypageGijuContextFa
        for (i in 0 until gijulist.size) {
            gijufa.addView(createKeyword(gijulist[i], 15.0f, "000000", 70))
            val vu = View(this.activity)
            var layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity, 10), 0)
            layoutparam.setMargins(0, 100, 0, 0)
            vu.layoutParams = layoutparam
            gijufa.addView(vu)
        }

        val keywords = user.userKeywords.split(",") as ArrayList<String>
        for (i in 0 until keywords.size) {
            keywords[i] = keywords[i].trim()
        }
        keywords.remove("")
        val l1 = binding.mypageKeywordContextFa
        for (i in 0 until keywords.size) {
            l1.addView(createKeyword(keywords[i], 15.0f, "000000", 70))
            val vu = View(this.activity)
            var layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity, 10), 0)
            layoutparam.setMargins(0, 100, 0, 0)
            vu.layoutParams = layoutparam
            l1.addView(vu)
        }

        // mainactivity 에도 mypage 변수들 변경
        (activity as MainActivity)!!.setMypageDosu(userInfo.alcoholLevel)
        (activity as MainActivity)!!.setMypageGijulist(gijulist)
        (activity as MainActivity)!!.setMypageKeywords(keywords)

    }

    private fun UserInspffochange(
        nickname: String,
        userDrinks: ArrayList<String>,
        userKeywords: ArrayList<String>,
        dosu: Int
    ) {
        var gijulist = ""
        for (i in userDrinks) {
            gijulist += i + ","
        }
        var keywrodlist = ""
        for (i in userKeywords) {
            keywrodlist += i + ","
        }

        var userinfotemp = getUser(requireContext())
        var userinfo = UserInfo(
            userinfotemp.age, dosu,
            nickname, userinfotemp.sex, gijulist, keywrodlist
        )
        val gson = Gson()
        var spf = requireActivity().getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putString("UserInfo", gson.toJson(userinfo))
        editor.apply()
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
//        DPtoPX(this.activity, 890)
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
                if (binding.mypageRenameBackgroundLa.visibility == View.VISIBLE){
                    binding.mypageRenameBackgroundLa.visibility = View.GONE
                } else if (binding.mypageResettingBackgroundLa.visibility == View.VISIBLE){
                    binding.mypageResettingBackgroundLa.visibility = View.GONE
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }




    private fun initClicker() {

        // ***** 닉네임 변경 시작

        binding.mypageNicknameResetIv.setOnClickListener() {
            (activity as MainActivity).setMypageReStatus(true)
            (activity as MainActivity).hidebottomnavation()
            val view: EditText = binding.mypageRenameEditEt
            (activity as MainActivity).showKeyboard(view)

            binding.mypageRenameEditEt.text = null
            binding.mypageRenameBackgroundLa.visibility = View.VISIBLE
            binding.mypageRenameBackgroundLa.animation = animation2

        }
        binding.mypageNicknameResetTv.setOnClickListener() {
            (activity as MainActivity).setMypageReStatus(true)
            (activity as MainActivity).hidebottomnavation()
            val view: EditText = binding.mypageRenameEditEt
            (activity as MainActivity).showKeyboard(view)

            binding.mypageRenameEditEt.text = null
            binding.mypageRenameBackgroundLa.visibility = View.VISIBLE
            binding.mypageRenameBackgroundLa.animation = animation2
        }

        binding.mypageRenameWhiteboardLa.setOnClickListener() {
            // 아무것도 안함. 배경 클릭과의 대비를 두기 위한 코드. 지우지 말것!
        }

        binding.mypageRenameBackgroundLa.setOnClickListener() {

//            var manager : InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//            view.requestFocus()
//            manager.showSoftInput(view,0)

            binding.mypageRenameBackgroundLa.visibility = View.GONE
            hideKeyboard2()
            (activity as MainActivity).showbottomnavation()

        }
        binding.mypageRenameExitIv.setOnClickListener() {
            binding.mypageRenameBackgroundLa.visibility = View.GONE
            hideKeyboard2()
            (activity as MainActivity).showbottomnavation()
        }


        binding.mypageRenameOkOnTv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (binding.mypageRenameEditEt.text.toString().replace(" ", "").equals("")) {
                    binding.mypageRenameNickcheckTv.visibility = View.VISIBLE
                    var animjindong: Animation = AnimationUtils
                        .loadAnimation(activity, R.anim.jindong)
                    binding.mypageRenameNickcheckTv.startAnimation(animjindong)
                } else {
                    val view: EditText = binding.mypageRenameEditEt
                    hideKeyboard2()
                    (activity as MainActivity).showbottomnavation()

                    binding.mypageRenameBackgroundLa.visibility = View.GONE
                    val reNickName = binding.mypageRenameEditEt.text
                    binding.mypageNicknameTv.text = reNickName
                    makeTextInput("닉네임을 변경했습니다.")

                    binding.mypageRenameEditEt.text = null
                    binding.mypageRenameNickcheckTv.visibility = View.INVISIBLE

                    //spf에 저장
                    UserInspffochange(
                        reNickName.toString(), (activity as MainActivity)!!.getMypageGijulist(),
                        (activity as MainActivity)!!.getMypageKeywords(),
                        (activity as MainActivity)!!.getMypageDosu()
                    )

                    UserInfoChangeToServer()
                    (activity as MainActivity).showbottomnavation()
                }

            }

        })

        binding.mypageRenameEditEt.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.mypageRenameEditEt.text.toString().replace(" ", "").equals("")) {

                    binding.mypageRenameNickcheckTv.visibility = View.VISIBLE
                    var animjindong: Animation = AnimationUtils
                        .loadAnimation(activity, R.anim.jindong)
                    binding.mypageRenameNickcheckTv.startAnimation(animjindong)
                } else {
                    val view: EditText = binding.mypageRenameEditEt
                    hideKeyboard2()
                    (activity as MainActivity).showbottomnavation()

                    binding.mypageRenameBackgroundLa.visibility = View.GONE
                    val reNickName = binding.mypageRenameEditEt.text
                    binding.mypageNicknameTv.text = reNickName
                    makeTextInput("닉네임을 변경했습니다.")
                    UserInspffochange(
                        reNickName.toString(), (activity as MainActivity)!!.getMypageGijulist(),
                        (activity as MainActivity)!!.getMypageKeywords(),
                        (activity as MainActivity)!!.getMypageDosu()
                    )

                    //spf에 저장
                    UserInspffochange(
                        reNickName.toString(), (activity as MainActivity)!!.getMypageGijulist(),
                        (activity as MainActivity)!!.getMypageKeywords(),
                        (activity as MainActivity)!!.getMypageDosu()
                    )

                    UserInfoChangeToServer()

                    // 서버로도 데이터 보낼것!!!!!
                    binding.mypageRenameEditEt.text = null
                    binding.mypageRenameNickcheckTv.visibility = View.INVISIBLE
                }
                handled = true
            }
            handled
        }

        // ***** 닉네임 변경 끝

        // ***** resetting

        binding.mypageLevelResetIv.setOnClickListener() {
            (activity as MainActivity).setMypageReStatus(true)
            //바텀 네비게이션 뷰 가리기기
            (activity as MainActivity).hidebottomnavation()
            changeResettingFragmentByPosition(0)
        }
        binding.mypageLevelResetTv.setOnClickListener() {
            (activity as MainActivity).setMypageReStatus(true)
            (activity as MainActivity).hidebottomnavation()
            changeResettingFragmentByPosition(0)
        }

        binding.mypageBaseResetIv.setOnClickListener() {
            (activity as MainActivity).setMypageReStatus(true)
            (activity as MainActivity).hidebottomnavation()
            changeResettingFragmentByPosition(1)
        }
        binding.mypageBaseResetTv.setOnClickListener() {
            (activity as MainActivity).setMypageReStatus(true)
            (activity as MainActivity).hidebottomnavation()
            changeResettingFragmentByPosition(1)
        }

        binding.mypageKeywordResetIv.setOnClickListener() {
            (activity as MainActivity).setMypageReStatus(true)
            (activity as MainActivity).hidebottomnavation()
            changeResettingFragmentByPosition(2)
        }
        binding.mypageKeywordResetTv.setOnClickListener() {
            (activity as MainActivity).setMypageReStatus(true)
            (activity as MainActivity).hidebottomnavation()
            changeResettingFragmentByPosition(2)
        }

        binding.mypageResettingWhiteboardLa.setOnClickListener() {
            // 아무것도 안함. 배경 클릭과의 대비를 두기 위한 코드. 지우지 말것!
        }

        binding.mypageResettingBackgroundLa.setOnClickListener() {
            (activity as MainActivity).showbottomnavation()
            binding.mypageResettingBackgroundLa.visibility = View.GONE
            (activity as MainActivity).clearThree()
            binding.mypageResettingViewpagerVp.adapter = null
        }
        binding.mypageResettingExitIv.setOnClickListener() {
            (activity as MainActivity).showbottomnavation()
            binding.mypageResettingBackgroundLa.visibility = View.GONE
            (activity as MainActivity).clearThree()
            binding.mypageResettingViewpagerVp.adapter = null
        }
        binding.mypageResettingOkOnTv.setOnClickListener() {

            var tempGijuSize : Int = (activity as MainActivity).getMypageTempGijulist().size
            var tempKeywordSize : Int = (activity as MainActivity).getMypageTempKeywords().size

            if ((tempGijuSize >= 1) and (tempGijuSize <= 5) and (tempKeywordSize >= 1) and (tempKeywordSize <= 5 ))
            {
                (activity as MainActivity).showbottomnavation()

                // 기존 fragment들의 정보를 main에 저장
                (activity as MainActivity).setMypageDosu((activity as MainActivity).getMypageTempDosu())
                (activity as MainActivity).setMypageGijulist((activity as MainActivity).getMypageTempGijulist())
                (activity as MainActivity).setMypageKeywords((activity as MainActivity).getMypageTempKeywords())

                // 데이터들 변경, 서버에 데이터 전송!!
                UserInspffochange(
                    getUser(requireContext()).nickname, (activity as MainActivity)!!.getMypageGijulist(),
                    (activity as MainActivity)!!.getMypageKeywords(),
                    (activity as MainActivity)!!.getMypageDosu()
                )
                UserInfoChangeToServer()

                // 데이터 변경 된 것을 마이페이지에도 새로고침
                binding.mypageLevelContextTv.text =
                    (activity as MainActivity)!!.getMypageDosu().toString() + "도"

                val gijufa = binding.mypageGijuContextFa
                var gijulist = arrayListOf<String>()
                gijulist.addAll((activity as MainActivity).getMypageGijulist())

                gijufa.removeAllViews()
                for (i in 0 until gijulist.size) {
                    gijufa.addView(createKeyword(gijulist[i], 15.0f, "000000", 70))
                    val vu = View(this.activity)
                    var layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity, 10), 0)
                    layoutparam.setMargins(0, 100, 0, 0)
                    vu.layoutParams = layoutparam
                    gijufa.addView(vu)
                }


                val l1 = binding.mypageKeywordContextFa
                var keywords = arrayListOf<String>()
                keywords.addAll((activity as MainActivity).getMypageKeywords())

                l1.removeAllViews()
                for (i in 0 until keywords.size) {
                    l1.addView(createKeyword(keywords[i], 15.0f, "000000", 70))
                    val vu = View(this.activity)
                    var layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity, 10), 0)
                    layoutparam.setMargins(0, 100, 0, 0)
                    vu.layoutParams = layoutparam
                    l1.addView(vu)
                }


                // 메시지
                makeTextInput("변경사항을 저장했습니다.")

                // 리소스 파괴
                binding.mypageResettingBackgroundLa.visibility = View.GONE
                (activity as MainActivity).clearThree()
                binding.mypageResettingViewpagerVp.adapter = null
            }
            else {
                makeTextInput("기주, 키워드는 1개~5개를 선택해야 합니다.")
                Log.d("GGGG",(activity as MainActivity).getMypageTempDosu().toString())
                Log.d("GGGG",(activity as MainActivity).getMypageTempGijulist().toString())
                Log.d("GGGG",(activity as MainActivity).getMypageTempKeywords().toString())

            }
        }


        binding.mypageResettingOkOnTv.setOnClickListener() {

            var tempGijuSize : Int = (activity as MainActivity).getMypageTempGijulist().size
            var tempKeywordSize : Int = (activity as MainActivity).getMypageTempKeywords().size

            if ((tempGijuSize >= 1) and (tempGijuSize <= 5) and (tempKeywordSize >= 1) and (tempKeywordSize <= 5 ))
            {
                (activity as MainActivity).showbottomnavation()

                // 기존 fragment들의 정보를 main에 저장
                (activity as MainActivity).setMypageDosu((activity as MainActivity).getMypageTempDosu())
                (activity as MainActivity).setMypageGijulist((activity as MainActivity).getMypageTempGijulist())
                (activity as MainActivity).setMypageKeywords((activity as MainActivity).getMypageTempKeywords())

                // 데이터들 변경, 서버에 데이터 전송!!
                UserInspffochange(
                    getUser(requireContext()).nickname, (activity as MainActivity)!!.getMypageGijulist(),
                    (activity as MainActivity)!!.getMypageKeywords(),
                    (activity as MainActivity)!!.getMypageDosu()
                )
                UserInfoChangeToServer()

                // 데이터 변경 된 것을 마이페이지에도 새로고침
                binding.mypageLevelContextTv.text =
                    (activity as MainActivity)!!.getMypageDosu().toString() + "도"

                val gijufa = binding.mypageGijuContextFa
                var gijulist = arrayListOf<String>()
                gijulist.addAll((activity as MainActivity).getMypageGijulist())

                gijufa.removeAllViews()
                for (i in 0 until gijulist.size) {
                    gijufa.addView(createKeyword(gijulist[i], 15.0f, "000000", 70))
                    val vu = View(this.activity)
                    var layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity, 10), 0)
                    layoutparam.setMargins(0, 100, 0, 0)
                    vu.layoutParams = layoutparam
                    gijufa.addView(vu)
                }


                val l1 = binding.mypageKeywordContextFa
                var keywords = arrayListOf<String>()
                keywords.addAll((activity as MainActivity).getMypageKeywords())

                l1.removeAllViews()
                for (i in 0 until keywords.size) {
                    l1.addView(createKeyword(keywords[i], 15.0f, "000000", 70))
                    val vu = View(this.activity)
                    var layoutparam = LinearLayout.LayoutParams(DPtoPX(this.activity, 10), 0)
                    layoutparam.setMargins(0, 100, 0, 0)
                    vu.layoutParams = layoutparam
                    l1.addView(vu)
                }


                // 메시지
                makeTextInput("변경사항을 저장했습니다.")

                // 리소스 파괴
                binding.mypageResettingBackgroundLa.visibility = View.GONE
                (activity as MainActivity).clearThree()
                binding.mypageResettingViewpagerVp.adapter = null


                Log.d("GGGG",(activity as MainActivity).getMypageTempDosu().toString())
                Log.d("GGGG",(activity as MainActivity).getMypageTempGijulist().toString())
                Log.d("GGGG",(activity as MainActivity).getMypageTempGijulist().size.toString())
                Log.d("GGGG",(activity as MainActivity).getMypageTempKeywords().toString())
            }
            else {
                makeTextInput("기주, 키워드는 1개~5개를 선택해야 합니다.")

                Log.d("GGGG",(activity as MainActivity).getMypageTempDosu().toString())
                Log.d("GGGG",(activity as MainActivity).getMypageTempGijulist().toString())
                Log.d("GGGG",(activity as MainActivity).getMypageTempGijulist().size.toString())
                Log.d("GGGG",(activity as MainActivity).getMypageTempKeywords().toString())

            }
        }

    }

    private fun UserInfoChangeToServer() {
        var gijulist = ""
        for (i in (activity as MainActivity)!!.getMypageGijulist()) {
            gijulist += i + ","
        }
        var keywrodlist = ""
        for (i in (activity as MainActivity)!!.getMypageKeywords()) {
            keywrodlist += i + ","
        }

        mypageService.mypagemodify(
            getaccesstoken(requireContext()),MypageRequest(
                getUser(requireContext()).nickname,
                (activity as MainActivity)!!.getMypageDosu(),
                keywrodlist,
                gijulist
            )
        )
    }

    private fun makeTextInput(inputText: String) {
        Toast.makeText(this.activity, inputText, Toast.LENGTH_SHORT).show()
    }

    private fun changeResettingFragmentByPosition(position: Int) {
        adapter = MypageViewpagerAdapter(this)
        viewPager.adapter = adapter

        binding.mypageResettingBackgroundLa.visibility = View.VISIBLE
        binding.mypageResettingBackgroundLa.animation = animation2
        viewPager.postDelayed({ viewPager.currentItem = position }, 10)
        adapter.notifyDataSetChanged()
    }

    override fun onMypageLoading() {
    }

    override fun onMypageSuccess(mypagebody: MypageBody) {

    }

    override fun onMypageFailure(code: Int, message: String) {
        if (code==5000){
            (activity as MainActivity).TokenrefreshInMain()
            Toast.makeText(requireContext(),"재시도 해주세요.",Toast.LENGTH_SHORT).show()
        }
    }


    fun Context.hideKeyboard2(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun Fragment.hideKeyboard2() {
        view?.let { activity?.hideKeyboard2(it) }
    }

    fun Activity.hideKeyboard2() {
        hideKeyboard2(currentFocus ?: View(this))
    }


}